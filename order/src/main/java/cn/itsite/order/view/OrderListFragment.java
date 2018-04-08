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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import cn.itsite.acommon.GoodsParams;
import cn.itsite.acommon.OperateBean;
import cn.itsite.acommon.event.RefreshOrderEvent;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.apayment.payment.Payment;
import cn.itsite.apayment.payment.PaymentListener;
import cn.itsite.apayment.payment.network.NetworkClient;
import cn.itsite.apayment.payment.network.PayService;
import cn.itsite.apayment.payment.pay.IPayable;
import cn.itsite.apayment.payment.pay.Pay;
import cn.itsite.order.R;
import cn.itsite.order.contract.OrderListContract;
import cn.itsite.order.model.OrderBean;
import cn.itsite.order.model.PayParams;
import cn.itsite.order.presenter.OrderListPresenter;
import cn.itsite.statemanager.BaseViewHolder;
import cn.itsite.statemanager.StateLayout;
import cn.itsite.statemanager.StateListener;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */
@Route(path = "/order/orderlistfragment")
public class OrderListFragment extends BaseFragment<OrderListContract.Presenter> implements OrderListContract.View {

    public static final String TAG = OrderListFragment.class.getSimpleName();

    public static final String TYPE_PAY = "pay";//去支付
    public static final String TYPE_CANCEL = "cancel";//取消订单
    public static final String TYPE_RECEIPT = "receipt";//确认收货
    public static final String TYPE_LOGISTICS = "logistics";//查看物流
    public static final String TYPE_DELETE = "delete";//删除订单


    RecyclerView mRecyclerView;
    private OrderListRVAdapter mAdapter;
    private GoodsParams mGoodsParams = new GoodsParams();
    private PtrFrameLayout mPtrFrameLayout;
    private StateManager mStateManager;
    private Payment payment;

    public static OrderListFragment newInstance(String category) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoodsParams.category = getArguments().getString("category");
    }

    @NonNull
    @Override
    protected OrderListContract.Presenter createPresenter() {
        return new OrderListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mPtrFrameLayout = view.findViewById(R.id.ptrFrameLayout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateManager();
        initData();
        initListener();
        initPtrFrameLayout(mPtrFrameLayout, mRecyclerView);
        EventBus.getDefault().register(this);
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(mRecyclerView)
                .setEmptyView(R.layout.state_empty_layout)
                .setEmptyImage(R.drawable.ic_prompt_order_01)
                .setErrorOnClickListener(v -> mPtrFrameLayout.autoRefresh())
                .setConvertListener(new StateListener.ConvertListener() {
                    @Override
                    public void convert(BaseViewHolder holder, StateLayout stateLayout) {
                        holder.setVisible(R.id.bt_empty_state, false);
                    }
                })
                .setEmptyText("当前页面暂无订单~")
                .build();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mGoodsParams.page = 1;
        mPresenter.getOrders(mGoodsParams);
    }

    private void initData() {
        mAdapter = new OrderListRVAdapter();
        mAdapter.setActivity((SupportActivity) _mActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(() -> {
            mGoodsParams.page++;
            mPresenter.getOrders(mGoodsParams);
        }, mRecyclerView);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshOrderEvent event) {
        onRefresh();
    }

    private void initListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OrderBean orderBean = mAdapter.getData().get(position);
                if (view.getId() == R.id.btn_2) {
                    OrderBean.ActionsBean actions = orderBean.getActions().get(0);
                    clickAction(orderBean, actions);
                } else if (view.getId() == R.id.btn_1) {
                    OrderBean.ActionsBean actions = orderBean.getActions().get(1);
                    clickAction(orderBean, actions);
                } else if (view.getId() == R.id.layout_order) {
                    ((SupportActivity) _mActivity).start(OrderDetailFragment.newInstance(orderBean.getUid()), 100);
                }
            }
        });
    }

    private void clickAction(OrderBean orderBean, OrderBean.ActionsBean action) {
        String orderUid = orderBean.getUid();
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
                showHintDialog(orderUid);
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
                Observable.just(orderBean).map(OrderBean::getUid).toList()
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

    private void showHintDialog(String orderUid) {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_hint)
                .setConvertListener((holder, dialog) -> {
                    holder.setText(R.id.tv_content, "您确定要删除订单吗？")
                            .setText(R.id.btn_cancel, "取消")
                            .setText(R.id.btn_comfirm, "确定")
                            .setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            })
                            .setOnClickListener(R.id.btn_comfirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteOrder(orderUid);
                                    dialog.dismiss();
                                }
                            });
                })
                .setMargin(40)
                .setDimAmount(0.3f)
                .setGravity(Gravity.CENTER)
                .show(getChildFragmentManager());
    }

    private void deleteOrder(String orderUid) {
        List<OperateBean> deleteOrders = new ArrayList<>();
        OperateBean dOrder = new OperateBean();
        dOrder.uid = orderUid;
        deleteOrders.add(dOrder);
        mPresenter.deleteOrders(deleteOrders);
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
    public void start(Object response) {

    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void responseOrders(List<OrderBean> datas) {
        mPtrFrameLayout.refreshComplete();
        if (datas == null || datas.isEmpty()) {
            if (mGoodsParams.page == 1) {
                mStateManager.showEmpty();
            }
            mAdapter.loadMoreEnd();
            mAdapter.loadMoreEnd();
            return;
        }

        if (mGoodsParams.page == 1) {
            mStateManager.showContent();
            mAdapter.setNewData(datas);
            mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        } else {
            mAdapter.addData(datas);
            mAdapter.setEnableLoadMore(true);
            mAdapter.loadMoreComplete();
        }

    }

    @Override
    public void responseDeleteSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        onRefresh();
    }

    @Override
    public void responsePutSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (payment != null) {
            payment.clear();
        }
        EventBus.getDefault().unregister(this);
    }
}
