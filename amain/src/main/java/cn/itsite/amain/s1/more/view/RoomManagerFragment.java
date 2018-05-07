package cn.itsite.amain.s1.more.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.RoomTypesBean;
import cn.itsite.amain.s1.entity.bean.RoomsBean;
import cn.itsite.amain.s1.more.contract.RoomManagerContract;
import cn.itsite.amain.s1.more.presenter.RoomManagerPresenter;
import cn.itsite.acommon.view.PtrHTFrameLayout;
import cn.itsite.statemanager.StateManager;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/5/2 0002 20:17.
 * Email: liujia95me@126.com
 */
public class RoomManagerFragment extends BaseFragment<RoomManagerContract.Presenter> implements RoomManagerContract.View {
    public static final String TAG = RoomManagerFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PtrHTFrameLayout ptrFrameLayout;
    private RoomManagerRVAdapter adapter;
    private Params params = Params.getInstance();
    private RoomsBean.DataBean.RoomListBean addIconBean = new RoomsBean.DataBean.RoomListBean();
    private StateManager mStateManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrHTFrameLayout) view.findViewById(R.id.ptrFrameLayout));
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

    public static RoomManagerFragment newInstance() {
        return new RoomManagerFragment();
    }

    @NonNull
    @Override
    protected RoomManagerContract.Presenter createPresenter() {
        return new RoomManagerPresenter(this);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("房间管理");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        addIconBean.setName("添加房间");
        recyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 3));
        adapter = new RoomManagerRVAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        params.page = 1;
        params.pageSize = 100;
        mPresenter.requestHouseList(params);
    }

    private void initListener() {
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            RoomsBean.DataBean.RoomListBean bean = adapter.getItem(position);
            if (position == adapter.getData().size() - 1) {
                showLoading();
                mPresenter.requestRoomTypeList(params);
            } else {
                new AlertDialog.Builder(_mActivity)
                        .setTitle("温馨提醒")
                        .setMessage("确定要删除该房间吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", (dialog, which) -> {
                            params.fid = bean.getFid();
                            mPresenter.requestDelroom(params);
                        }).show();
            }
        });
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.layout_state_empty)
                .setEmptyText("还没有房间哦，赶紧添加吧！")
                .setErrorOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setEmptyOnClickListener(v -> mPresenter.requestRoomTypeList(params))
                .setConvertListener((holder, stateLayout) ->
                        holder.setOnClickListener(R.id.bt_empty_state, v -> mPresenter.requestRoomTypeList(params))
                                .setText(R.id.bt_empty_state, "点击添加"))
                .build();
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
        dismissLoading();
    }

    @Override
    public void responseHouseList(List<RoomsBean.DataBean.RoomListBean> data) {
        //由于没有分页，所以只需要管理显示空和显示内容。
        ptrFrameLayout.refreshComplete();
        if (data == null || data.isEmpty()) {
            mStateManager.showEmpty();
        } else {
            mStateManager.showContent();
        }
        data.add(addIconBean);
        adapter.setNewData(data);
    }

    @Override
    public void responseAddHouse(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        //添加后要刷新。
        onRefresh();
    }

    @Override
    public void responseRoomTypeList(List<RoomTypesBean.DataBean> data) {
        dismissLoading();
        //拿到房间类型后要弹框提示。
        new SelectorDialogFragment()
                .setTitle("请选房间类型")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(data)
                .setOnItemConvertListener((holder, position, dialog) ->
                        holder.setText(R.id.tv_item_rv_simple_selector, data.get(position).getName()))
                .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                    dialog.dismiss();
                    RoomTypesBean.DataBean bean = data.get(position);
                    params.roomName = bean.getName();
                    params.roomTypeFid = bean.getFid();
                    mPresenter.requestAddHouse(params);
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    @Override
    public void responseDelroom(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        //添加后要刷新。
        onRefresh();
    }
}
