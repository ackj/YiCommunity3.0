package cn.itsite.amain.s1.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Unbinder;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.camera.contract.CameraListContract;
import cn.itsite.amain.s1.camera.presenter.CameraListPresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.CameraBean;
import cn.itsite.amain.s1.event.EventCameraListRefresh;
import cn.itsite.amain.yicommunity.widget.PtrHTFrameLayout;
import cn.itsite.statemanager.StateManager;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/9/12 0012 09:09.
 * Email: liujia95me@126.com
 */

public class CameraListFragment extends BaseFragment<CameraListContract.Presenter> implements CameraListContract.View {
    public static final String TAG = CameraListFragment.class.getSimpleName();
    TextView toolbarTitle;
    Toolbar toolbar;
    RecyclerView recyclerView;
    PtrHTFrameLayout ptrFrameLayout;
    Unbinder unbinder;
    TextView toolbarMenu;
    private StateManager mStateManager;
    private String[] addSelectedArr = {"新设备配置网络", "添加已联网设备"};
    private Params params = Params.getInstance();
    private CameraListRVAdapter adapter;
    private CameraBean.DataBean addCameraBean;
    private List<CameraBean.DataBean> cameraList;

    public static CameraListFragment newInstance() {
        CameraListFragment fragment = new CameraListFragment();
        return fragment;
    }

    @NonNull
    @Override
    protected CameraListContract.Presenter createPresenter() {
        return new CameraListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrHTFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        EventBus.getDefault().register(this);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initStateManager();
        initListener();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("智能监控");
        toolbarMenu.setText("设置");
        toolbarMenu.setOnClickListener(v -> {
            List<CameraBean.DataBean> data = adapter.getData();
            new SelectorDialogFragment()
                    .setTitle("请选择监控")
                    .setItemLayoutId(android.R.layout.simple_list_item_1)
                    .setData(data.subList(0, data.size() - 1))
                    .setOnItemConvertListener((holder, position, dialog) -> {
                        holder.setText(android.R.id.text1, data.get(position).getName() + "(" + data.get(position).getNo() + ")");
                    })
                    .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                        dialog.dismiss();
//                    _mActivity.start(CameraSettingFragment.newInstance(data.get(position)));
                    })
                    .setAnimStyle(R.style.SlideAnimation)
                    .setGravity(Gravity.BOTTOM)
                    .show(getChildFragmentManager());
        });
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        recyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 3));
        adapter = new CameraListRVAdapter();
        recyclerView.setAdapter(adapter);
        addCameraBean = new CameraBean.DataBean();
        addCameraBean.setName("添加监控");
        mPresenter.requestCameraList(params);
    }

    @Override
    public void onRefresh() {
        params.page = 1;
        params.pageSize = Constants.PAGE_SIZE;
        //要清空所选中的那些设备。
        mPresenter.requestCameraList(params);
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.layout_state_empty)
                .setEmptyImage(R.drawable.bg_vidicon_360px)
                .setEmptyText("还没有添加摄像头哦,赶紧去添加吧~")
                .setErrorOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setEmptyOnClickListener(v -> showSelectedDialog())
                .setConvertListener((holder, stateLayout) ->
                        holder.setOnClickListener(R.id.bt_empty_state,
                                v -> showSelectedDialog())
                                .setText(R.id.bt_empty_state, "马上添加"))
                .build();
    }

    private void showSelectedDialog() {
        new AlertDialog.Builder(_mActivity)
                .setItems(addSelectedArr, (dialog, which) -> {
                    if (which == 0) {
                        ((SupportActivity) _mActivity).start(CameraWifiInputFragment.newInstance());
                    } else {
                        showAddCameraDialog();
                    }
                })
                .show();
    }

    private void showAddCameraDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.fragment_input_video)
                .setConvertListener((holder, dialog) -> {
                    EditText etDeviceId = holder.getView(R.id.et_input_1);
                    EditText etNickname = holder.getView(R.id.et_input_2);
                    EditText etPassword = holder.getView(R.id.et_input_3);
                    holder.setText(R.id.tv_title, "添加设备")
                            .setOnClickListener(R.id.tv_cancel, v -> {
                                dialog.dismiss();
                            })
                            .setOnClickListener(R.id.tv_comfirm, v -> {
                                params.deviceId = etDeviceId.getText().toString().trim();
                                params.deviceName = etNickname.getText().toString().trim();
                                params.devicePassword = etPassword.getText().toString().trim();
                                if (TextUtils.isEmpty(params.deviceId)) {
                                    DialogHelper.warningSnackbar(getView(), "请输入摄像头ID");
                                    return;
                                }
                                if (TextUtils.isEmpty(params.deviceName)) {
                                    DialogHelper.warningSnackbar(getView(), "请输入摄像头昵称");
                                    return;
                                }
                                if (TextUtils.isEmpty(params.devicePassword)) {
                                    DialogHelper.warningSnackbar(getView(), "请输入摄像头密码");
                                    return;
                                }
                                mPresenter.requestNewCamera(params);
                                dialog.dismiss();
                            });
                })
                .setMargin(40)
                .setDimAmount(0.3f)
                .setGravity(Gravity.CENTER)
                .show(getFragmentManager());
    }

    private void initListener() {
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            CameraBean.DataBean bean = adapter.getItem(position);
            if (position == adapter.getData().size() - 1) {
                showSelectedDialog();
            } else {
                if (cameraList == null || cameraList.size() == 0) {
                    return;
                }
                Intent intent = new Intent(_mActivity, CameraPlayActivity.class);
                intent.putExtra("OperateBean", bean);
                _mActivity.startActivity(intent);
            }
        });


        adapter.setOnItemLongClickListener((adapter1, view, position) -> {
            CameraBean.DataBean bean = adapter.getItem(position);
            if (position != adapter.getData().size() - 1) {
                new AlertDialog.Builder(_mActivity)
                        .setMessage("确认删除？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            params.fid = bean.getFid();
                            params.deviceType = "password";
                            params.deviceName = "";
                            params.devicePassword = "123";
                            mPresenter.requestModCamera(params);
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
            return false;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCameraListRefresh event) {
        onRefresh();
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

    @Override
    public void responseCameraList(List<CameraBean.DataBean> data) {
        ptrFrameLayout.refreshComplete();
        data.add(addCameraBean);
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
            cameraList = data;
            mStateManager.showContent();
        }
    }

    @Override
    public void responseSuccess(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        onRefresh();
    }

    @Override
    public void responseModSuccess(BaseBean baseBean) {
        mPresenter.requestDelCamera(params);
    }

}
