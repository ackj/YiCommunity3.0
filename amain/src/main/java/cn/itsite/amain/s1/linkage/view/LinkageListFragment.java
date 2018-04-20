package cn.itsite.amain.s1.linkage.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.LinkageBean;
import cn.itsite.amain.s1.event.EventLinkageChanged;
import cn.itsite.amain.s1.event.EventSwitchHost;
import cn.itsite.amain.s1.linkage.contract.LinkageListContract;
import cn.itsite.amain.s1.linkage.presenter.LinkageListPresenter;
import cn.itsite.amain.yicommunity.widget.PtrHTFrameLayout;
import cn.itsite.statemanager.StateManager;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 联动模块。
 */

public class LinkageListFragment extends BaseFragment<LinkageListContract.Presenter> implements LinkageListContract.View {
    private static final String TAG = LinkageListFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private PtrHTFrameLayout ptrFrameLayout;
    private LinkageRVAdapter adapter;
    private Params params = Params.getInstance();
    private StateManager mStateManager;
    private int delPosition;
    private SwitchButton sbSelected;

    public static LinkageListFragment newInstance() {
        return new LinkageListFragment();
    }

    @NonNull
    @Override
    protected LinkageListContract.Presenter createPresenter() {
        return new LinkageListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrHTFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
        initStateManager();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    private void initData() {
        adapter = new LinkageRVAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> {
            params.page++;
            mPresenter.requestLinkageList(params);//请求开门记录列表
        }, recyclerView);
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.layout_state_empty)
                .setEmptyText("暂无联动，请点击添加！")
                .setErrorOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setEmptyOnClickListener(v -> ((SupportActivity) _mActivity).start(LinkageEditFragment.newInstance()))
                .setConvertListener((holder, stateLayout) ->
                        holder.setOnClickListener(R.id.bt_empty_state,
                                v -> ((SupportActivity) _mActivity).start(LinkageEditFragment.newInstance()))
                                .setText(R.id.bt_empty_state, "点击添加"))
                .build();

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            LinkageBean.DataBean bean = adapter.getItem(position);
            ((SupportActivity) _mActivity).start(LinkageEditFragment.newInstance(bean));
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLinkageChanged(EventLinkageChanged event) {
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchHost(EventSwitchHost event) {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        params.page = 1;
        params.pageSize = Constants.PAGE_SIZE;
        mPresenter.requestLinkageList(params);
    }

    private void initListener() {
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            LinkageBean.DataBean bean = adapter.getItem(position);
            int i = view.getId();
            if (i == R.id.tv_delete) {
                params.index = adapter.getItem(position).getIndex();
                delPosition = position;
                mPresenter.requestDeleteLinkage(params);

            } else if (i == R.id.ll_item_intelligence_linkage) {
                // _mActivity.start(LinkageEditFragment.newInstance());

            } else if (i == R.id.switch_button) {
                sbSelected = (SwitchButton) view;
                ALog.e(TAG, "click switch button:" + sbSelected.isChecked());

                params.cdt_day = bean.getDay();
                params.index = bean.getIndex();
                params.name = bean.getName();
                params.cdt_sensorAct = bean.getSensorCmd() + "";
                params.cdt_sensorId = bean.getSensorIndex();

                params.act1 = bean.getTargetCmd() + "";
                params.targetId = bean.getTargetIndex() + "";
                params.nodeId = bean.getTargetNodeId() + "";
                params.delay = bean.getTargetTimeout() + "";
                params.act2 = bean.getTargetTimeoutCmd() + "";
                params.targetType = bean.getTargetType();
                params.cdt_time = bean.getTime();
                params.triggerType = bean.getTriggerType();
                params.status = sbSelected.isChecked() ? 1 : 0;
                mPresenter.requestModLinkage(params);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void responseLinkageList(List<LinkageBean.DataBean> data) {
        ptrFrameLayout.refreshComplete();
        if (data.size() < Constants.PAGE_SIZE) {
            //如果加载数量小于个数，直接完成
            adapter.loadMoreEnd();
        } else {
            //否则，可继续加载
            adapter.loadMoreComplete();
        }
        if (params.page == 1) {
            adapter.setNewData(data);
        } else {
            adapter.addData(data);
        }
        //如果个数为0，显示空
        if (adapter.getData().size() == 0) {
            mStateManager.showEmpty();
            adapter.loadMoreEnd();
        } else {
            mStateManager.showContent();
        }
    }

    @Override
    public void responseModLinkage(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
    }

    @Override
    public void responseDeleteLinkage(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        adapter.remove(delPosition);
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
}
