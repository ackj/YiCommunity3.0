package cn.itsite.goodsdetail.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.goodsdetail.R;
import cn.itsite.goodsdetail.contract.GoodsCommentContract;
import cn.itsite.goodsdetail.model.EvaluatesBean;
import cn.itsite.goodsdetail.presenter.GoodsCommentPresenter;
import cn.itsite.statemanager.BaseViewHolder;
import cn.itsite.statemanager.StateLayout;
import cn.itsite.statemanager.StateListener;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/12 0012 17:33
 */

public class GoodsCommentFragment extends BaseFragment<GoodsCommentContract.Presenter> implements GoodsCommentContract.View {

    public static final String TAG = GoodsCommentFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ImageView mIvBack;
    private GoodsCommentRVAdapter mAdapter;
    private TextView mToolbarTitle;
    private PtrFrameLayout mPtrFrameLayout;

    private GoodsParams mParams = new GoodsParams();
    private StateManager mStateManager;

    public static GoodsCommentFragment newInstance(String uid) {
        GoodsCommentFragment fragment = new GoodsCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParams.uid = getArguments().getString("uid");
    }

    @NonNull
    @Override
    protected GoodsCommentContract.Presenter createPresenter() {
        return new GoodsCommentPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbarTitle = view.findViewById(R.id.toolbar_title);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mIvBack = view.findViewById(R.id.iv_back);
        mPtrFrameLayout = view.findViewById(R.id.ptrFrameLayout);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
        initStateManager();
        initPtrFrameLayout(mPtrFrameLayout, mRecyclerView);
    }

    private void initToolbar() {
        initStateBar(mToolbar);
        mToolbarTitle.setText("商品评价");
        mToolbarTitle.setTextColor(_mActivity.getResources().getColor(R.color.base_black));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_gray_24dp);
        mToolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mParams.page = 1;
        mPresenter.getComments(mParams);
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(mRecyclerView)
                .setEmptyView(R.layout.state_empty_layout)
                .setEmptyImage(R.drawable.ic_prompt_shangpin_01)
                .setConvertListener(new StateListener.ConvertListener() {
                    @Override
                    public void convert(BaseViewHolder holder, StateLayout stateLayout) {
                        holder.setVisible(R.id.bt_empty_state, false);
                    }
                })
                .setEmptyText("抱歉，该分类暂无评论~")
                .build();
    }

    private void initData() {
        mToolbar.setBackgroundColor(_mActivity.getResources().getColor(R.color.white));
        mAdapter = new GoodsCommentRVAdapter();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(() -> {
            mParams.page++;
            mPresenter.getComments(mParams);
        }, mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getComments(mParams);
    }

    private void initListener() {

    }

    @Override
    public void responseGetComments(List<EvaluatesBean> datas) {
        mPtrFrameLayout.refreshComplete();
        if (datas == null || datas.isEmpty()) {
            if (mParams.page == 1) {
                mStateManager.showEmpty();
            }
            mAdapter.loadMoreEnd();
            return;
        }
        if (mParams.page == 1) {
            mStateManager.showContent();
            mAdapter.setNewData(datas);
            mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView);
        } else {
            mAdapter.addData(datas);
            mAdapter.setEnableLoadMore(true);
            mAdapter.loadMoreComplete();
        }
    }
}
