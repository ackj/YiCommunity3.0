package cn.itsite.amain.yicommunity.main.propery.view;

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

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.amain.yicommunity.entity.bean.RepairApplyBean;
import cn.itsite.amain.yicommunity.event.EventCommunity;
import cn.itsite.amain.yicommunity.main.message.view.RepairDetailFragment;
import cn.itsite.amain.yicommunity.main.propery.contract.RepairApplyContract;
import cn.itsite.amain.yicommunity.main.propery.presenter.RepairApplyPresenter;
import cn.itsite.amain.yicommunity.main.publish.view.RepairFragment;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2017/4/19 14:27.
 * <p>
 * 物业保修列表
 */
public class RepairRecordFragment extends BaseFragment<RepairApplyContract.Presenter> implements RepairApplyContract.View {
    private final String TAG = RepairRecordFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PtrFrameLayout ptrFrameLayout;
    private RepairRecordRVAdapter adapter;
    private Params params = Params.getInstance();
    public static final int REQUEST_CODE = 100;
    private StateManager mStateManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        EventBus.getDefault().register(this);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
        initStateManager();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public static RepairRecordFragment newInstance() {
        return new RepairRecordFragment();
    }

    @NonNull
    @Override
    protected RepairApplyContract.Presenter createPresenter() {
        return new RepairApplyPresenter(this);
    }

    @Override
    public void onRefresh() {
        params.page = 1;
        params.pageSize = Constants.PAGE_SIZE;
        params.cmnt_c = UserHelper.communityCode;
        mPresenter.requestRepairApplyList(params);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("物业报修");
        toolbar.inflateMenu(R.menu.menu_repair_record);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
        toolbarTitle.setOnClickListener(v -> recyclerView.scrollToPosition(0));
    }

    public void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new RepairRecordRVAdapter();
        //设置Item动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(true);
        //设置允许加载更多
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> {
            params.page++;
            mPresenter.requestRepairApplyList(params);
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.btn_public_repairs) {
                startForResult(RepairFragment.newInstance(false), REQUEST_CODE);
            } else if (item.getItemId() == R.id.btn_private_repairs) {
                startForResult(RepairFragment.newInstance(true), REQUEST_CODE);
            }
            return true;
        });

        adapter.setOnItemClickListener((adapter, view, position) -> {
            RepairApplyBean.DataBean.RepairApplysBean bean = (RepairApplyBean.DataBean.RepairApplysBean) adapter.getData().get(position);
            start(RepairDetailFragment.newInstance(bean.getFid()));
        });
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.state_empty)
                .setEmptyImage(R.drawable.ic_property_repair_empty_state_gray_200px)
                .setEmptyText("有问题，您找物业保修！")
                .setErrorOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setEmptyOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .build();
    }

    @Override
    public void start(Object response) {

    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
        if (params.page == 1) {
            //为后面的pageState做准备
            mStateManager.showError();
        } else if (params.page > 1) {
            adapter.loadMoreFail();
            params.page--;
        }

        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    @Override
    public void responseRepairApplyList(List<RepairApplyBean.DataBean.RepairApplysBean> datas) {

        ptrFrameLayout.refreshComplete();
        if (datas == null || datas.isEmpty()) {
            if (params.page == 1) {
                mStateManager.showEmpty();
            }
            adapter.loadMoreEnd();
            return;
        }

        if (params.page == 1) {
            mStateManager.showContent();
            adapter.setNewData(datas);
            adapter.disableLoadMoreIfNotFullPage(recyclerView);
        } else {
            adapter.addData(datas);
            adapter.setEnableLoadMore(true);
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            ptrFrameLayout.autoRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCommunity event) {
        ptrFrameLayout.autoRefresh();
    }
}
