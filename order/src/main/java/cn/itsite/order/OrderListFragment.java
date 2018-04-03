package cn.itsite.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.acommon.OperatorBean;
import cn.itsite.order.contract.OrderListContract;
import cn.itsite.order.presenter.OrderListPresenter;
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


    RecyclerView mRecyclerView;
    private OrderListRVAdapter mAdapter;
    private GoodsParams mGoodsParams = new GoodsParams();
    private PtrFrameLayout mPtrFrameLayout;

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
        initData();
        initListener();
        initPtrFrameLayout(mPtrFrameLayout, mRecyclerView);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getOrders(mGoodsParams);
    }

    private void initData() {
        mAdapter = new OrderListRVAdapter();
        mAdapter.setActivity((SupportActivity) _mActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);


    }

    private void initListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OrderBean orderBean = mAdapter.getData().get(position);
                if (view.getId() == R.id.btn_2) {
                    OrderBean.ActionsBean actions = orderBean.getActions().get(0);
                    clickAction(orderBean.getUid(), actions);
                } else if (view.getId() == R.id.btn_1) {
                    OrderBean.ActionsBean actions = orderBean.getActions().get(1);
                    clickAction(orderBean.getUid(), actions);
                } else {
                    ((SupportActivity) _mActivity).start(OrderDetailFragment.newInstance());
                }
            }
        });
    }

    private void clickAction(String orderUid, OrderBean.ActionsBean action) {
        switch (action.getType().toLowerCase()) {
            case TYPE_CANCEL://取消订单
            case TYPE_RECEIPT://确认订单
                //这两种操作只需要无脑传category给后台，以更改该订单的状态即可
                List<OperatorBean> orders = new ArrayList<>();
                OperatorBean order = new OperatorBean();
                order.uid = orderUid;
                order.category = action.getCategory();
                orders.add(order);
                mPresenter.putOrders(orders);
                break;
            case TYPE_DELETE://删除订单
                List<OperatorBean> deleteOrders = new ArrayList<>();
                OperatorBean dOrder = new OperatorBean();
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
                break;
            default:
        }
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
    public void responseOrders(List<OrderBean> data) {
        mPtrFrameLayout.refreshComplete();
        mAdapter.setNewData(data);
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
}
