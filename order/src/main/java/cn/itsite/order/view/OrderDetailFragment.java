package cn.itsite.order.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.acommon.OperateBean;
import cn.itsite.acommon.event.RefreshOrderEvent;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.apayment.payment.Payment;
import cn.itsite.apayment.payment.PaymentListener;
import cn.itsite.apayment.payment.network.NetworkClient;
import cn.itsite.apayment.payment.network.PayService;
import cn.itsite.apayment.payment.pay.IPayable;
import cn.itsite.apayment.payment.pay.Pay;
import cn.itsite.order.R;
import cn.itsite.order.contract.OrderDetailContract;
import cn.itsite.order.model.OrderDetailBean;
import cn.itsite.order.model.PayParams;
import cn.itsite.order.presenter.OrderDetailPresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.itsite.order.view.OrderListFragment.TYPE_CANCEL;
import static cn.itsite.order.view.OrderListFragment.TYPE_DELETE;
import static cn.itsite.order.view.OrderListFragment.TYPE_LOGISTICS;
import static cn.itsite.order.view.OrderListFragment.TYPE_PAY;
import static cn.itsite.order.view.OrderListFragment.TYPE_RECEIPT;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */
@Route(path = "/order/orderdetailfragment")
public class OrderDetailFragment extends BaseFragment<OrderDetailContract.Presenter> implements OrderDetailContract.View {

    public static final String TAG = OrderDetailFragment.class.getSimpleName();
    private OrderDetailRVAdapter mAdapter;

    private RelativeLayout mRlToolbar;
    private RecyclerView mRecyclerView;
    private TextView mTvDeliveryType;
    private TextView mTvShopName;
    private TextView mTvCategory;
    private TextView mTvAmount;
    private TextView mTvContactWay;
    private TextView mTvLocation;
    private TextView mTvLeaveWords;
    private TextView mTvOrderNum;
    private TextView mTvOrderTime;

    private String mUid;
    private View mLayoutExpress;
    private TextView mTvExpressPhone;
    private TextView mTvExpressName;
    private ImageView mIvAvator;
    private TextView mTvIdentity;
    private Button mBtn1;
    private Button mBtn2;
    private Payment payment;

    public static OrderDetailFragment newInstance(String uid) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUid = getArguments().getString("uid");
    }

    @NonNull
    @Override
    protected OrderDetailContract.Presenter createPresenter() {
        return new OrderDetailPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mBtn1 = view.findViewById(R.id.btn_1);
        mBtn2 = view.findViewById(R.id.btn_2);
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
        mRlToolbar.setPadding(mRlToolbar.getPaddingLeft(), mRlToolbar.getPaddingTop() + ScreenUtils.getStatusBarHeight(_mActivity), mRlToolbar.getPaddingRight(), mRlToolbar.getPaddingBottom());
    }

    private void initData() {
        mAdapter = new OrderDetailRVAdapter();
        //加header和footer
        View viewHeader = LayoutInflater.from(_mActivity).inflate(R.layout.item_order_detail_header, null);
        mTvDeliveryType = viewHeader.findViewById(R.id.tv_delivery_type);
        mTvShopName = viewHeader.findViewById(R.id.tv_name);
        mTvCategory = viewHeader.findViewById(R.id.tv_category);
        mIvAvator = viewHeader.findViewById(R.id.iv_avator);
        mTvExpressName = viewHeader.findViewById(R.id.tv_express_name);
        mTvExpressPhone = viewHeader.findViewById(R.id.tv_express_phone);
        mLayoutExpress = viewHeader.findViewById(R.id.layout_express);
        mTvIdentity = viewHeader.findViewById(R.id.tv_identity);
        mAdapter.addHeaderView(viewHeader);


        View viewFooter = LayoutInflater.from(_mActivity).inflate(R.layout.item_order_detail_footer, null);
        mTvAmount = viewFooter.findViewById(R.id.tv_amount);
        mTvContactWay = viewFooter.findViewById(R.id.tv_contactway);
        mTvLocation = viewFooter.findViewById(R.id.tv_location);
        mTvLeaveWords = viewFooter.findViewById(R.id.tv_leave_words);
        mTvOrderNum = viewFooter.findViewById(R.id.tv_order_num);
        mTvOrderTime = viewFooter.findViewById(R.id.tv_order_time);
        mAdapter.addFooterView(viewFooter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getOrderDetail(mUid);
    }

    private void initListener() {
    }


    @Override
    public void responseOrderDetail(OrderDetailBean orderDetailBean) {
        mTvDeliveryType.setText(orderDetailBean.getDeliveryType());
        mTvShopName.setText(orderDetailBean.getShop().getName());
        mTvCategory.setText(orderDetailBean.getCategory());
        mTvAmount.setText(_mActivity.getString(R.string.amount, orderDetailBean.getAmount(), "￥ " + orderDetailBean.getCost()));
        mTvContactWay.setText(_mActivity.getString(R.string.consignee, orderDetailBean.getDelivery().getName(), orderDetailBean.getDelivery().getPhoneNumber()));
        mTvLocation.setText(orderDetailBean.getDelivery().getAddress() + orderDetailBean.getDelivery().getLocation());
        mTvLeaveWords.setText(orderDetailBean.getNote());
        mTvOrderNum.setText(orderDetailBean.getOrderNumber());
        mTvOrderTime.setText(orderDetailBean.getTime());
        mAdapter.setNewData(orderDetailBean.getProducts());

        List<OrderDetailBean.ActionsBean> actions = orderDetailBean.getActions();
        mBtn1.setVisibility(View.INVISIBLE);
        mBtn2.setVisibility(View.INVISIBLE);
        for (int i = 0; i < actions.size(); i++) {
            OrderDetailBean.ActionsBean action = actions.get(i);
            if (i == 0) {
                mBtn2.setText(action.getAction());
                mBtn2.setVisibility(View.VISIBLE);
            } else if (i == 1) {
                mBtn1.setText(action.getAction());
                mBtn1.setVisibility(View.VISIBLE);
            }
        }

        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAction(orderDetailBean, actions.get(1));
            }
        });

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAction(orderDetailBean, actions.get(0));
            }
        });

        if (orderDetailBean.getDeliveryType().contains("上门")) {
            mLayoutExpress.setVisibility(View.VISIBLE);
            OrderDetailBean.ExpressBean express = orderDetailBean.getExpress();
            Glide.with(_mActivity)
                    .load(express.getImageUrl())
                    .apply(new RequestOptions().error(R.drawable.ic_img_error))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading))
                    .into(mIvAvator);
            mTvExpressName.setText(express.getName());
            mTvExpressPhone.setText(express.getPhoneNumber());
            mTvIdentity.setText(express.getIdentity());
        } else {
            mLayoutExpress.setVisibility(View.GONE);
        }
    }


    private void clickAction(OrderDetailBean orderDetailBean, OrderDetailBean.ActionsBean action) {
        String orderUid = orderDetailBean.getUid();
        switch (action.getType().toLowerCase()) {
            case TYPE_CANCEL://取消订单
            case TYPE_RECEIPT://确认订单
                //这两种操作只需要无脑传category给后台，以更改该订单的状态即可
                List<OperateBean> orders = new ArrayList<>();
                OperateBean order = new OperateBean();
                order.uid = orderUid;
                order.category = action.getCategory();
                orders.add(order);
                mPresenter.putOrders(orders);
                break;
            case TYPE_DELETE://删除订单
                List<OperateBean> deleteOrders = new ArrayList<>();
                OperateBean dOrder = new OperateBean();
                dOrder.uid = orderUid;
                deleteOrders.add(dOrder);
                mPresenter.deleteOrders(deleteOrders);
                break;
            case TYPE_LOGISTICS://查看物流
                Fragment fragment = (Fragment) ARouter.getInstance().build("/web/webfragment").navigation();
                Bundle bundle = new Bundle();
                bundle.putString(BaseConstants.KEY_LINK, action.getLink());
                bundle.putString(BaseConstants.KEY_TITLE, "查看物流");
                fragment.setArguments(bundle);
                ((BaseFragment) getParentFragment()).start((BaseFragment) fragment);
                break;
            case TYPE_PAY://跳支付
                BaseRequest<PayParams> request = new BaseRequest<>();
                request.message = "请求统一订单";
                Observable.just(orderDetailBean).map(OrderDetailBean::getUid).toList()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(strings -> {
                            PayParams payParams = new PayParams();
                            payParams.setOrders(strings);
                            request.data = payParams;
                            showPaySelector(request);
                        });
                break;
            default:
        }
    }

    private void showPaySelector(BaseRequest<PayParams> request) {
        List<String> strings = Arrays.asList("支付宝", "微信");
        new SelectorDialogFragment()
                .setTitle("请选择支付方式")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(strings)
                .setOnItemConvertListener((holder, position, dialog) ->
                        holder.setText(R.id.tv_item_rv_simple_selector, strings.get(position)))
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

    private void pay(BaseRequest<PayParams> request, IPayable iPayable) {
        //拼参数。
        Map<String, String> params = new HashMap<>();
        params.put("params", request.toString());
        ALog.e(TAG, "request:" + request.toString());
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
                        DialogHelper.successSnackbar(getView(), "支付成功");
//                        ptrFrameLayout.autoRefresh();
                    }

                    @Override
                    public void onFailure(@Payment.PayType int payType, int errorCode) {
                        ALog.e("3.支付 失败-------->" + payType + "----------errorCode-->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "支付失败，请重试");
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
                        DialogHelper.errorSnackbar(getView(), "确认失败，请稍后再查看");
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

    @Override
    public void responseDeleteSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        EventBus.getDefault().register(new RefreshOrderEvent());
        pop();
    }

    @Override
    public void responsePutSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        EventBus.getDefault().register(new RefreshOrderEvent());
        pop();
    }
}
