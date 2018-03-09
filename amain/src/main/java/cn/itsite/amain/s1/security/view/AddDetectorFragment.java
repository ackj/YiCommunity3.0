package cn.itsite.amain.s1.security.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.DevicesBean;
import cn.itsite.amain.s1.security.contract.AddDetectorContract;
import cn.itsite.amain.s1.security.presenter.AddDetectorPresenter;
import cn.itsite.amain.yicommunity.widget.PtrHTFrameLayout;
import cn.itsite.apush.event.EventLearnSensor;
import cn.itsite.statemanager.StateLayout;
import cn.itsite.statemanager.StateManager;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author： Administrator on 2017/5/2 0002.
 * Email： liujia95me@126.com
 */
public class AddDetectorFragment extends BaseFragment<AddDetectorContract.Presenter> implements AddDetectorContract.View {
    public static final String TAG = AddDetectorFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private StateLayout stateLayout;
    private PtrHTFrameLayout ptrFrameLayout;
    private AddDetectorRVAdapter adapter;
    private Params params = Params.getInstance();
    private StateManager mStateManager;
    private AlertDialog dialog;

    public static AddDetectorFragment newInstance() {
        return new AddDetectorFragment();
    }

    @NonNull
    @Override
    protected AddDetectorContract.Presenter createPresenter() {
        return new AddDetectorPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        stateLayout = ((StateLayout) view.findViewById(R.id.stateLayout));
        ptrFrameLayout = ((PtrHTFrameLayout) view.findViewById(R.id.ptrFrameLayout));
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
    public void onRefresh() {
        super.onRefresh();
        params.page = 1;
        mPresenter.requestDetectorList(params);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("选择添加探测器");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        adapter = new AddDetectorRVAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            DevicesBean.DataBean.DeviceTypeListBean bean = adapter.getData().get(position);
            params.sensorType = bean.getCode();
            params.name = bean.getName();
            mPresenter.requestAddDetector(params);
        });
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.layout_state_empty)
                .setEmptyOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .build();
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void responseDetectorList(List<DevicesBean.DataBean.DeviceTypeListBean> data) {
        ptrFrameLayout.refreshComplete();
        if (data == null && data.isEmpty()) {
            mStateManager.showEmpty();
        }
        adapter.setNewData(data);
    }

    @Override
    public void responseAddDetector(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        dialog = new AlertDialog.Builder(_mActivity)
                .setTitle("温馨提醒")
                .setMessage("正在学习中，请稍后…")
                .setNegativeButton("取消", (dialog, which) ->
                        mPresenter.reqeuestCancellationOfSensorLearning(params))
                .setCancelable(false)
                .show();
    }

    @Override
    public void responseCancellationOfSensorLearning(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLearnSensor(EventLearnSensor event) {
        dialog.dismiss();
        pop();
    }
}
