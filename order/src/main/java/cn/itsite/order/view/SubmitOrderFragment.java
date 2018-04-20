package cn.itsite.order.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.acommon.DeliveryBean;
import cn.itsite.acommon.OperateBean;
import cn.itsite.acommon.StorePojo;
import cn.itsite.acommon.event.RefreshCartEvent;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.apayment.payment.Payment;
import cn.itsite.apayment.payment.PaymentListener;
import cn.itsite.apayment.payment.network.NetworkClient;
import cn.itsite.apayment.payment.network.PayService;
import cn.itsite.apayment.payment.pay.IPayable;
import cn.itsite.apayment.payment.pay.Pay;
import cn.itsite.order.R;
import cn.itsite.order.contract.SubmitOrderContract;
import cn.itsite.order.model.PayParams;
import cn.itsite.order.model.SubmitOrderBean;
import cn.itsite.order.presenter.SubmitOrderPresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */
@Route(path = "/order/submitorderfragment")
public class SubmitOrderFragment extends BaseFragment<SubmitOrderContract.Presenter> implements SubmitOrderContract.View {
    public static final String TAG = SubmitOrderFragment.class.getSimpleName();
    private RelativeLayout mLlToolbar;
    private RecyclerView mRecyclerView;
    private SubmitOrderRVAdapter mAdapter;
    private ArrayList<StorePojo> mOrders;
    private TextView mTvTotalPrice;
    private TextView mTvAmount;
    private SubmitOrderBean mCurrentEditInfo;
    private DeliveryBean mDefaultDelivery;
    private TextView mTvSubmit;
    private Payment payment;
    private String mFrom;
    private String mOutTradeNo;

    public static SubmitOrderFragment newInstance() {
        return new SubmitOrderFragment();
    }

    @NonNull
    @Override
    protected SubmitOrderContract.Presenter createPresenter() {
        return new SubmitOrderPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrders = getArguments().getParcelableArrayList("orders");
        mFrom = getArguments().getString("from");
        ALog.e(TAG, "orders:" + mOrders);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_order, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLlToolbar = view.findViewById(R.id.rl_toolbar);
        mTvAmount = view.findViewById(R.id.tv_amount);
        mTvTotalPrice = view.findViewById(R.id.tv_total_price);
        mTvSubmit = view.findViewById(R.id.tv_submit);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStatusBar();
        initData();
        initListener();
    }

    private void initStatusBar() {
        mLlToolbar.setPadding(mLlToolbar.getPaddingLeft(),
                mLlToolbar.getPaddingTop() +
                        ScreenUtils.getStatusBarHeight(_mActivity),
                mLlToolbar.getPaddingRight(),
                mLlToolbar.getPaddingBottom());
    }

    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new SubmitOrderRVAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getAddress();
    }

    private void loadData() {
        List<SubmitOrderBean> data = new ArrayList<>();
        float allTotalPrice = 0;
        int allAmount = 0;
        String currency = "";
        for (int i = 0; i < mOrders.size(); i++) {
            SubmitOrderBean store = new SubmitOrderBean();
            store.setItemType(SubmitOrderBean.TYPE_STORE_TITLE);
            store.setShopBean(mOrders.get(i).getShop());
            List<StorePojo.ProductsBean> products = mOrders.get(i).getProducts();
            data.add(store);
            int amount = 0;
            float totalPrice = 0;
            for (int i1 = 0; i1 < products.size(); i1++) {
                StorePojo.ProductsBean productsBean = products.get(i1);
                SubmitOrderBean product = new SubmitOrderBean();
                product.setItemType(SubmitOrderBean.TYPE_STORE_GOODS);
                product.setProductsBean(productsBean);
                data.add(product);
                allAmount += productsBean.getCount();
                allTotalPrice += Float.valueOf(productsBean.getPay().getPrice()) * productsBean.getCount();
                amount += productsBean.getCount();
                totalPrice += Float.valueOf(productsBean.getPay().getPrice()) * productsBean.getCount();
                if (TextUtils.isEmpty(currency)) {
                    currency = productsBean.getPay().getCurrency();
                }
            }
            SubmitOrderBean info = new SubmitOrderBean();
            info.setItemType(SubmitOrderBean.TYPE_ORDER_INFO);
            info.setTotalPrice(totalPrice);
            info.setAmount(amount);
            info.setCurrency(currency);
            info.setDeliveryBean(mDefaultDelivery);
            data.add(info);
        }
        mTvAmount.setText(allAmount + "件");
        mTvTotalPrice.setText(currency + allTotalPrice);
        mAdapter.setNewData(data);
    }

    private void initListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_delivery) {
                    Fragment addressFragment = (Fragment) ARouter.getInstance().build("/delivery/selectshoppingaddressfragment").navigation();
                    startForResult((BaseFragment) addressFragment, 100);
                    mCurrentEditInfo = mAdapter.getItem(position);
                } else if (view.getId() == R.id.tv_leave_message) {
                    mCurrentEditInfo = mAdapter.getItem(position);
                    showInputDialog();
                }
            }
        });
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });
    }

    private void submitOrder() {
        List<OperateBean> data = new ArrayList<>();
        OperateBean operatorOperateBean = new OperateBean();
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            SubmitOrderBean submitOrderBean = mAdapter.getData().get(i);
            if (submitOrderBean.getItemType() == SubmitOrderBean.TYPE_STORE_TITLE) {
                operatorOperateBean = new OperateBean();
                operatorOperateBean.products = new ArrayList<>();
                operatorOperateBean.from = mFrom;
            } else if (submitOrderBean.getItemType() == SubmitOrderBean.TYPE_STORE_GOODS) {
                OperateBean.Product product = new OperateBean.Product();
                product.amount = submitOrderBean.getProductsBean().getCount() + "";
                product.sku = submitOrderBean.getProductsBean().getSkuID();
                product.uid = submitOrderBean.getProductsBean().getUid();
                operatorOperateBean.products.add(product);
            } else if (submitOrderBean.getItemType() == SubmitOrderBean.TYPE_ORDER_INFO) {
                operatorOperateBean.note = submitOrderBean.getLeaveMessage();
                operatorOperateBean.uid = submitOrderBean.getDeliveryBean().getUid();
                data.add(operatorOperateBean);
            }
        }
        if (data.size() > 0) {
            mPresenter.postOrders(data);
        }
    }

    private void showInputDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_input)
                .setConvertListener((holder, dialog) -> {
                    EditText etInput = holder.getView(R.id.et_input);
                    etInput.setText(mCurrentEditInfo.getLeaveMessage());
                    etInput.setSelection(etInput.getText().toString().length());
                    holder
                            .setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            })
                            .setOnClickListener(R.id.btn_comfirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String input = etInput.getText().toString().trim();
                                    if (TextUtils.isEmpty(input)) {
                                        ToastUtils.showToast(_mActivity, "请输入留言内容");
                                    } else {
                                        mCurrentEditInfo.setLeaveMessage(input);
                                        mAdapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                }
                            });
                })
                .setDimAmount(0.3f)
                .setMargin(40)
                .setGravity(Gravity.CENTER)
                .show(getChildFragmentManager());
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            DeliveryBean addressBean = (DeliveryBean) data.getSerializable("delivery");
            mCurrentEditInfo.setDeliveryBean(addressBean);
            mAdapter.notifyDataSetChanged();
        } else if (resultCode == RESULT_OK && requestCode == 101) {
            mPresenter.getAddress();
        }
    }

    @Override
    public void responseGetAddress(List<DeliveryBean> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isIsDefault()) {
                mDefaultDelivery = data.get(i);
                break;
            }
        }
        if (data.size() >= 1 && mDefaultDelivery == null) {
            mDefaultDelivery = data.get(0);
        }

        if (mDefaultDelivery == null) {
            showHintDialog();
        } else {
            loadData();
        }
    }

    @Override
    public void responsePostOrdersSuccess(BaseResponse<List<OperateBean>> response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        EventBus.getDefault().post(new RefreshCartEvent());
        List<OperateBean> data = response.getData();
        BaseRequest<PayParams> request = new BaseRequest<>();

        request.message = "请求统一订单";

        Observable.from(data).map(OperateBean::getUid).toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> {
                    PayParams payParams = new PayParams();
                    payParams.setOrders(strings);
                    request.data = payParams;
                    showPaySelector(request);
                });
    }

    @Override
    public void responseCheckOrderStatus(int status) {
        if (status == 0) {
            //支付失败
            DialogHelper.errorSnackbar(getView(), "支付失败");
        } else if (status == 1) {
            //支付成功
            DialogHelper.successSnackbar(getView(), "支付成功");
        }
        ALog.e(TAG, "status:" + status);
        //跳到订单详情页
//        start(OrderDetailFragment.newInstance(mOutTradeNo));
        pop();
    }

    private void showPaySelector(BaseRequest<PayParams> request) {
        List<String> strings = Arrays.asList("支付宝", "微信");
        new SelectorDialogFragment()
                .setTitle("请选择支付方式")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(strings)
                .setOnItemConvertListener((holder, position, dialog) -> {
                    holder.setText(R.id.tv_item_rv_simple_selector, strings.get(position));
                })
                .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                    dialog.dismiss();
                    switch (position) {
                        case 0:
                            request.data.setPayment("zfb");
                            pay(request, Pay.aliAppPay());
                            break;
                        case 1:
                            request.data.setPayment("weixin_h5");
                            pay(request, Pay.weChatH5xPay());
                            break;
                        default:
                            break;
                    }
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    private void showHintDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_hint)
                .setConvertListener((holder, dialog) -> {
                    holder.setText(R.id.tv_content, "您还没有地址，请先新增一个默认地址")
                            .setVisible(R.id.btn_cancel, false)
                            .setOnClickListener(R.id.btn_comfirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Fragment addressFragment = (Fragment) ARouter.getInstance().build("/delivery/selectshoppingaddressfragment").navigation();
                                    startForResult((BaseFragment) addressFragment, 101);
                                    dialog.dismiss();
                                }
                            });
                })
                .setDimAmount(0.3f)
                .setMargin(40)
                .setGravity(Gravity.CENTER)
                .show(getChildFragmentManager());
    }

    @Override
    public void start(Object response) {
    }

    private void pay(BaseRequest<PayParams> request, IPayable iPayable) {
        //拼参数。
        Map<String, String> params = new HashMap<>();
        params.put("params", request.toString());
        ALog.e(TAG, request.toString());
        //构建支付入口对象。
        payment = Payment.builder()
                .setParams(params)
                .setHttpType(Payment.HTTP_POST)
                .setUrl(PayService.requestGoodsPayResult)
                .setActivity(_mActivity)
                .setClient(NetworkClient.okhttp())
                .setPay(iPayable)
                .setOnRequestListener(new PaymentListener.OnRequestListener() {
                    @Override
                    public void onStart() {
                        ALog.e("1.请求 开始-------->");
                        showLoading("请求订单中");
                    }

                    @Override
                    public void onSuccess(String result) {
                        ALog.e("1.请求 成功-------->" + result);
                        showLoading("等待解析");
                    }

                    @Override
                    public void onError(int errorCode) {
                        ALog.e("1.请求 失败-------->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "订单请求失败");

                    }
                })
                .setOnParseListener(new PaymentListener.OnParseListener() {
                    @Override
                    public void onStart(String result) {
                        ALog.e("2.解析 开始-------->" + result);
                        showLoading("正在解析");
                    }

                    @Override
                    public void onSuccess(cn.itsite.apayment.payment.PayParams params) {
                        ALog.e("2.解析 成功-------->");
                        showLoading("解析成功");
                        mOutTradeNo = params.getOutTradeNo();
                    }

                    @Override
                    public void onError(int errorCode) {
                        ALog.e("2.解析 失败------->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "解析异常");
                    }
                })
                .setOnPayListener(new PaymentListener.OnPayListener() {
                    @Override
                    public void onStart(@Payment.PayType int payType) {
                        ALog.e("3.支付 开始-------->" + payType);
                        showLoading("正在支付");
                    }

                    @Override
                    public void onSuccess(@Payment.PayType int payType) {
                        ALog.e("3.支付 成功-------->" + payType);
                        dismissLoading();
                        mPresenter.checkOrderStatus(mOutTradeNo);
                    }

                    @Override
                    public void onFailure(@Payment.PayType int payType, int errorCode) {
                        ALog.e("3.支付 失败-------->" + payType + "----------errorCode-->" + errorCode);
                        dismissLoading();
                        mPresenter.checkOrderStatus(mOutTradeNo);
                    }
                })
                .setOnVerifyListener(new PaymentListener.OnVerifyListener() {

                    @Override
                    public void onStart() {
                        ALog.e("4.检验 开始--------");
                        showLoading("正在确认");
                    }

                    @Override
                    public void onSuccess() {
                        ALog.e("4.检验 成功--------");
                        dismissLoading();
                    }

                    @Override
                    public void onFailure(int errorCode) {
                        ALog.e("4.检验 失败--------" + "errorCode-->" + errorCode);
                        dismissLoading();
//                        DialogHelper.errorSnackbar(getView(), "确认失败，请稍后再查看");
//                        ptrFrameLayout.autoRefresh();
                    }
                })
                .start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (payment != null) {
            payment.clear();
        }
    }
}
