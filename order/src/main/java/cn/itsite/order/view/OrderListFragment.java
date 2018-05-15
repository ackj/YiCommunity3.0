package cn.itsite.order.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.acommon.data.bean.OperateBean;
import cn.itsite.acommon.event.EventRefreshOrdersPoint;
import cn.itsite.acommon.event.RefreshOrderEvent;
import cn.itsite.apayment.payment.Payment;
import cn.itsite.order.R;
import cn.itsite.order.contract.OrderListContract;
import cn.itsite.order.model.OrderBean;
import cn.itsite.order.presenter.OrderListPresenter;
import cn.itsite.statemanager.BaseViewHolder;
import cn.itsite.statemanager.StateLayout;
import cn.itsite.statemanager.StateListener;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

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
    public static final String TYPE_EVALUATE = "evaluate";//去评价
    public static final String TYPE_REFUND = "refund";//去评价

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
        if (mPresenter != null)
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
                } else if (view.getId() == R.id.layout_order || view.getId()==R.id.recyclerView) {
                    go2OrderDetailView(orderBean.getUid());
                }
            }
        });
    }

    private void go2OrderDetailView(String orderUid) {
        ((SupportActivity) _mActivity).start(OrderDetailFragment.newInstance(orderUid), 100);
    }

    private void clickAction(OrderBean orderBean, OrderBean.ActionsBean action) {
        String orderUid = orderBean.getUid();
        switch (action.getType().toLowerCase()) {
            case TYPE_CANCEL://取消订单
            case TYPE_RECEIPT://确认订单
                //这两种操作只需要无脑传category给后台，以更改该订单的状态即可
                new AlertDialog.Builder(_mActivity)
                        .setTitle("提示")
                        .setMessage("此操作无法撤销，是否继续？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                List<OperateBean> orders = new ArrayList<>();
                                OperateBean order = new OperateBean();
                                order.uid = orderUid;
                                order.category = action.getCategory();
                                orders.add(order);
                                mPresenter.putOrders(orders);
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                break;
            case TYPE_DELETE://删除订单
                new AlertDialog.Builder(_mActivity)
                        .setTitle("提示")
                        .setMessage("此操作无法撤销，是否继续？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteOrder(orderUid);
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                break;
            case TYPE_LOGISTICS://查看物流
                Fragment fragment = (Fragment) ARouter
                        .getInstance()
                        .build("/web/webfragment")
                        .withString(BaseConstants.KEY_LINK, "http://www.aglhz.com/mall/m/html/wuliuSearch.html?token=" + UserHelper.token)
                        .withString(BaseConstants.KEY_TITLE, "查看物流")
                        .navigation();
                ((BaseFragment) getParentFragment()).start((BaseFragment) fragment);
                break;
            case TYPE_PAY://支付——跳详情去支付
                go2OrderDetailView(orderUid);
                break;
            case TYPE_EVALUATE://去评价
                go2OrderDetailView(orderUid);
//                该页面无评价按钮（后台决定）
//                start(InputCommentFragment.newInstance());
                break;
            case TYPE_REFUND://退款

                break;
            default:
        }
    }

    private void deleteOrder(String orderUid) {
        List<OperateBean> deleteOrders = new ArrayList<>();
        OperateBean dOrder = new OperateBean();
        dOrder.uid = orderUid;
        deleteOrders.add(dOrder);
        mPresenter.deleteOrders(deleteOrders);
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
        EventBus.getDefault().post(new EventRefreshOrdersPoint());
        onRefresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (payment != null) {
            payment.clear();
        }
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
}
