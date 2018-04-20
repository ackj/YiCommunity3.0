package cn.itsite.amain.s1.main.smarthome.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.s1.camera.CameraPlayActivity;
import cn.itsite.amain.s1.camera.CameraSettingFragment;
import cn.itsite.amain.s1.camera.CameraWifiInputFragment;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.SmartHomeBean;
import cn.itsite.amain.s1.main.home.MainActivity;
import cn.itsite.amain.s1.main.smarthome.contract.SmartHomeContract;
import cn.itsite.amain.s1.main.smarthome.presenter.SmartHomePresenter;
import cn.itsite.amain.s1.qrcode.ScanQRCodeFragment;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.amain.yicommunity.entity.bean.MainDeviceListBean;
import cn.itsite.amain.yicommunity.widget.PtrHTFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/9/25 0025 09:35.
 * Email: liujia95me@126.com
 */

public class SmartHomeFragment extends BaseFragment<SmartHomeContract.Presenter> implements SmartHomeContract.View {
    private static final String TAG = SmartHomeFragment.class.getSimpleName();
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private RecyclerView recyclerView;
    private PtrHTFrameLayout ptrFrameLayout;
    private SmartHomeListAdapter adapter;
    private MainDeviceListBean.DataBean addEquipmentBean;//添加的item
    private MainDeviceListBean.DataBean addCameraBean;
    private String[] addSelectedArr = {"新设备配置网络", "添加已联网设备"};
    private String[] cameraSelectedArr = {"设置", "删除"};
    private Params params = Params.getInstance();
    public List<MainDeviceListBean.DataBean> equipmentList = new ArrayList<>();
    public List<MainDeviceListBean.DataBean> cameraList = new ArrayList<>();

    public static SmartHomeFragment newInstance(String roomDir) {
        ALog.e(TAG, "roomDir:" + roomDir);
        SmartHomeFragment fragment = new SmartHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomDir", roomDir);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SmartHomeFragment newInstance() {
        return new SmartHomeFragment();
    }

    @NonNull
    @Override
    protected SmartHomeContract.Presenter createPresenter() {
        return new SmartHomePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            params.roomDir = bundle.getString("roomDir");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
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
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestEquipmentInfoList(params);
        mPresenter.requestCameraList(params);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("智能家居");
        toolbarTitle.setOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new SmartHomeListAdapter();
        recyclerView.setAdapter(adapter);
        ptrFrameLayout.setBackgroundColor(ContextCompat.getColor(App.mContext, R.color.material_grey_300));

        addEquipmentBean = new MainDeviceListBean.DataBean();
        addEquipmentBean.setName("添加中控");
        addCameraBean = new MainDeviceListBean.DataBean();
        addCameraBean.setName("添加监控");

        equipmentList.add(addEquipmentBean);
        cameraList.add(addCameraBean);

        adapter.addData(new SmartHomeBean(SmartHomeBean.TYPE_EQUIPMENT, equipmentList, null));
        adapter.addData(new SmartHomeBean(SmartHomeBean.TYPE_CAMERA, null, cameraList));
    }

    private void initListener() {
        adapter.setOnItemEquipmentClickListener(new SmartHomeListAdapter.OnItemEquipmentClickListener() {
            @Override
            public void click(BaseRecyclerViewAdapter adapter, MainDeviceListBean.DataBean item, int position) {
                if (adapter.getData().size() - 1 == position) {
                    //最后一个
                    ((SupportActivity) _mActivity).start(ScanQRCodeFragment.newInstance(params.roomDir));
                } else {
                    //todo(高亮) 从这里获取deviceSn
                    UserHelper.deviceSn = item.getNo();
                    UserHelper.deviceName = item.getName();
                    UserHelper.deviceIsManager = item.getIsManager();
                    Intent intent = new Intent(App.mContext, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    App.mContext.startActivity(intent);
                }
            }

            @Override
            public void longClick(BaseRecyclerViewAdapter adapter, MainDeviceListBean.DataBean item, int position) {
                if (adapter.getData().size() - 1 == position) {
                    //最后一个不做操作
                } else {
                    new AlertDialog.Builder(_mActivity)
                            .setTitle("温馨提醒")
                            .setMessage("确定要解绑该网关吗？")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", (dialog, which) -> {
                                params.deviceSn = item.getNo();
                                mPresenter.requestDelGateway(params);
                            }).show();
                }
            }
        });

        adapter.setOnItemCameraClickListener(new SmartHomeListAdapter.OnItemCameraClickListener() {
            @Override
            public void click(BaseRecyclerViewAdapter adapter, MainDeviceListBean.DataBean item, int position) {
                if (adapter.getData().size() - 1 == position) {
                    showSelectedDialog();
                } else {
                    Intent intent = new Intent(_mActivity, CameraPlayActivity.class);
                    intent.putExtra("OperateBean", item);
                    _mActivity.startActivity(intent);
                }
            }

            @Override
            public void longClick(BaseRecyclerViewAdapter adapter, MainDeviceListBean.DataBean item, int position) {
                if (position != adapter.getData().size() - 1) {
                    showCameraSelectedDialog(item);
                }
            }
        });
    }

    private void showCameraSelectedDialog(MainDeviceListBean.DataBean item) {
        new AlertDialog.Builder(_mActivity)
                .setItems(cameraSelectedArr, (dialog, which) -> {
                    if (which == 0) {
                        showSelectorDialog();
                    } else {
                        showDelCameraDialog(item);
                    }
                })
                .show();
    }

    private void showSelectorDialog() {
        List<MainDeviceListBean.DataBean> data = adapter.getData().get(1).cameraList;
        new SelectorDialogFragment()
                .setTitle("请选择监控")
                .setItemLayoutId(android.R.layout.simple_list_item_1)
                .setData(data.subList(0, data.size() - 1))
                .setOnItemConvertListener((holder, position, dialog) -> {
                    holder.setText(android.R.id.text1, data.get(position).getName() + "(" + data.get(position).getNo() + ")");
                })
                .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                    dialog.dismiss();
                    ((SupportActivity) _mActivity).start(CameraSettingFragment.newInstance(data.get(position)));
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    private void showDelCameraDialog(MainDeviceListBean.DataBean item) {
        new AlertDialog.Builder(_mActivity)
                .setMessage("确认删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        params.fid = item.getFid();
                        params.deviceId = item.getNo();
                        mPresenter.requestDelCamera(params);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
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

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
        DialogHelper.errorSnackbar(getView(), errorMessage);
    }

    @Override
    public void responseEquipmentInfoList(MainDeviceListBean bean) {
        ptrFrameLayout.refreshComplete();
        adapter.getData().set(0, new SmartHomeBean(SmartHomeBean.TYPE_EQUIPMENT, bean.getData(), null));
        bean.getData().add(addEquipmentBean);
        adapter.notifyItemChanged(0);
    }

    @Override
    public void responseDelGatewaySuccess(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        mPresenter.requestEquipmentInfoList(params);
    }

    @Override
    public void responseCameraList(MainDeviceListBean bean) {
        ptrFrameLayout.refreshComplete();
        adapter.getData().set(1, new SmartHomeBean(SmartHomeBean.TYPE_CAMERA, null, bean.getData()));
        bean.getData().add(addCameraBean);
        adapter.notifyItemChanged(1);
    }

    @Override
    public void responseAddAndDelCameraSuccess(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        mPresenter.requestCameraList(params);
    }
}
