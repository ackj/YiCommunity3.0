package cn.itsite.amain.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.MonthCardBillListBean;
import cn.itsite.amain.yicommunity.main.parking.contract.RechargeRecordContract;
import cn.itsite.amain.yicommunity.main.parking.presenter.RechargeRecordPresenter;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车卡管理充值记录模块。
 * [充值记录]的View层。
 * 打开方式：StartApp-->管家-->我的车卡-->充值记录
 */
public class RechargeRecordFragment extends BaseFragment<RechargeRecordContract.Presenter> implements RechargeRecordContract.View {
    public static final String TAG = RechargeRecordFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private PtrFrameLayout ptrFrameLayout;
    private Params params = Params.getInstance();
    private RechargeReocrdAdapter adapter;
    private StateManager mStateManager;

    public static RechargeRecordFragment newInstance() {
        return new RechargeRecordFragment();
    }

    @NonNull
    @Override
    protected RechargeRecordContract.Presenter createPresenter() {
        return new RechargeRecordPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        ptrFrameLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initStateManager();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    @Override
    public void onRefresh() {
        params.page = 1;
        params.pageSize = 20;
        mPresenter.requestMonthCardBillList(params);//请求月卡的充值记录列表
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("充值记录");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new RechargeReocrdAdapter();
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> {
            params.page++;
            mPresenter.requestMonthCardBillList(params);//请求月卡的充值记录列表
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.state_empty)
                .setEmptyImage(R.drawable.ic_apply_refuse_200px)
                .setEmptyText("暂无充值记录！")
                .setErrorOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setEmptyOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .build();
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
        if (params.page == 1) {
            mStateManager.showError();
        } else if (params.page > 1) {
            adapter.loadMoreFail();
            params.page--;
        }
    }

    /**
     * 响应请求充值记录的列表
     *
     * @param datas
     */
    @Override
    public void responseRechargeRecord(List<MonthCardBillListBean.DataBean.MonthCardBillBean> datas) {
        ptrFrameLayout.refreshComplete();
        if (datas == null || datas.isEmpty()) {
            if (params.page == 1) {
                mStateManager.showEmpty();
            }
            adapter.loadMoreEnd();
            return;
        }
        if (params.page == 1) {
            adapter.setNewData(datas);
            adapter.disableLoadMoreIfNotFullPage(recyclerView);
        } else {
            adapter.addData(datas);
            adapter.setEnableLoadMore(true);
            adapter.loadMoreComplete();
        }
    }
}
