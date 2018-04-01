package cn.itsite.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.acommon.GoodsParams;
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

    RecyclerView mRecyclerView;
    private OrderListRVAdapter mAdapter;
    private GoodsParams mGoodsParams = new GoodsParams();
    private PtrFrameLayout mPtrFrameLayout;

    public static OrderListFragment newInstance(String category) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category",category);
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
        initPtrFrameLayout(mPtrFrameLayout,mRecyclerView);
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
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((SupportActivity) _mActivity).start(OrderDetailFragment.newInstance());
            }
        });
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
}
