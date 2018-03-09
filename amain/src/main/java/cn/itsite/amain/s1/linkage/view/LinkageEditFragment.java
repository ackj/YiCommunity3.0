package cn.itsite.amain.s1.linkage.view;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.DeviceListBean;
import cn.itsite.amain.s1.entity.bean.LinkageBean;
import cn.itsite.amain.s1.entity.bean.SceneBean;
import cn.itsite.amain.s1.event.EventLinkageChanged;
import cn.itsite.amain.s1.linkage.contract.AddLinkageContract;
import cn.itsite.amain.s1.linkage.presenter.AddLinkagePresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 联动模块。
 */
public class LinkageEditFragment extends BaseFragment<AddLinkageContract.Presenter> implements AddLinkageContract.View, View.OnClickListener {
    public static final String TAG = LinkageEditFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView toolbarMenu;
    private EditText etName;
    private TextView tvTriggerType;
    private TextView tvLinkageType;
    private LinearLayout llSceneGoneContainer;
    private LinearLayout llDateContainer;
    private LinearLayout llSensorContainer;
    private TextView tvWeek;
    private TextView tvTime;
    private TextView tvSensor;
    private TextView tvDeviceSence;
    private TextView tvDeviceNode;
    private TextView tvSensorAction;
    private TextView tvDeviceAction;
    private TextView tvDeviceAction2;
    private EditText etMinute;
    private Params params = Params.getInstance();
    private String[] triggerTypeArr = {"传感器", "时间"};
    private String[] linkageTypeArr = {"设备", "场景"};
    private String[] weekArr = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private boolean[] weekBoolArr = {false, false, false, false, false, false, false};
    private boolean isSensor = false;
    private DeviceListBean.DataBean.SubDevicesBean selectedDevice;//选中的设备
    private DeviceListBean.DataBean.SubDevicesBean selectedSensor;//选中的探测器
    private DeviceListBean.DataBean.SubDevicesBean.ActionsBean selectedDeviceAct;
    private DeviceListBean.DataBean.SubDevicesBean.ActionsBean selectedDeviceAct2;
    private DeviceListBean.DataBean.SubDevicesBean.ActionsBean selectedSensorAct;
    private SceneBean.DataBean selectedScene;//选中的场景
    private LinkageBean.DataBean bean;
    private boolean isMod = false;
    private LinearLayout llWeek;
    private LinearLayout llTime;

    public static LinkageEditFragment newInstance(LinkageBean.DataBean bean) {
        LinkageEditFragment fragment = new LinkageEditFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static LinkageEditFragment newInstance() {
        return new LinkageEditFragment();
    }

    @NonNull
    @Override
    protected AddLinkageContract.Presenter createPresenter() {
        return new AddLinkagePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("bean") instanceof LinkageBean.DataBean) {
                bean = (LinkageBean.DataBean) bundle.getSerializable("bean");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linkage_edit, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        etName = ((EditText) view.findViewById(R.id.et_name));
        tvTriggerType = ((TextView) view.findViewById(R.id.tv_trigger_type));
        tvLinkageType = ((TextView) view.findViewById(R.id.tv_linkage_type));
        llSceneGoneContainer = ((LinearLayout) view.findViewById(R.id.ll_scene_gone_container));
        llDateContainer = ((LinearLayout) view.findViewById(R.id.ll_date_container));
        llSensorContainer = ((LinearLayout) view.findViewById(R.id.ll_sensor_container));
        tvWeek = ((TextView) view.findViewById(R.id.tv_week));
        tvTime = ((TextView) view.findViewById(R.id.tv_time));
        tvSensor = ((TextView) view.findViewById(R.id.tv_sensor));
        tvDeviceSence = ((TextView) view.findViewById(R.id.tv_device_sence));
        tvDeviceNode = ((TextView) view.findViewById(R.id.tv_device_node));
        tvSensorAction = ((TextView) view.findViewById(R.id.tv_sensor_action));
        tvDeviceAction = ((TextView) view.findViewById(R.id.tv_device_action));
        tvDeviceAction2 = ((TextView) view.findViewById(R.id.tv_device_action_2));
        etMinute = ((EditText) view.findViewById(R.id.et_minute));
        llWeek = ((LinearLayout) view.findViewById(R.id.ll_week));
        llTime = ((LinearLayout) view.findViewById(R.id.ll_time));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("联动编辑");
        toolbarMenu.setText("确定");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        if (bean != null) {
            isMod = true;
            etName.setText(bean.getName());
            if (bean.getTriggerType().equals("sensor")) {
                tvTriggerType.setText(triggerTypeArr[0]);
//                tvSensor.setText();
            } else {
                tvTriggerType.setText(triggerTypeArr[0]);
            }
        }
        //todo:触发类型为时间有问题，所以先设置死(2017/9/1 16:21
        tvTriggerType.setText(triggerTypeArr[0]);
    }

    private void initListener() {
        tvTriggerType.setOnClickListener(this);
        tvLinkageType.setOnClickListener(this);
        llWeek.setOnClickListener(this);
        llTime.setOnClickListener(this);
        tvDeviceSence.setOnClickListener(this);
        tvSensor.setOnClickListener(this);
        tvSensorAction.setOnClickListener(this);
        tvDeviceAction.setOnClickListener(this);
        tvDeviceAction2.setOnClickListener(this);
        tvDeviceNode.setOnClickListener(this);
        toolbarMenu.setOnClickListener(this);
    }

    @Override
    public void responseAddSuccess(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        EventBus.getDefault().post(new EventLinkageChanged());
        pop();
    }

    @Override
    public void responseSceneList(SceneBean bean) {
        List<SceneBean.DataBean> data = bean.getData();
        String[] arr = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = data.get(i).getName();
        }
        ALog.e(TAG, "responseDeviceList:" + data.size());
        new AlertDialog.Builder(_mActivity)
                .setItems(arr, (dialog, which) -> {
                    selectedScene = data.get(which);
                    tvDeviceSence.setText(arr[which]);
                }).show();
    }

    @Override
    public void responseDeviceList(List<DeviceListBean.DataBean.SubDevicesBean> data) {
        String[] arr = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = data.get(i).getName();
        }
        ALog.e(TAG, "responseDeviceList:" + data.size());
        new AlertDialog.Builder(_mActivity)
                .setItems(arr, (dialog, which) -> {
                    if (isSensor) {
                        selectedSensor = data.get(which);
                        tvSensor.setText(arr[which]);
                        tvSensorAction.setText("请选择");
                    } else {
                        selectedDevice = data.get(which);
                        tvDeviceSence.setText(arr[which]);
                        tvDeviceAction.setText("请选择");
                        tvDeviceAction2.setText("请选择");
                        tvDeviceNode.setText("请选择");
                    }
                }).show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_trigger_type) {//                new AlertDialog.Builder(_mActivity)
//                        .setItems(triggerTypeArr, (dialog, which) -> {
//                            llDateContainer.setVisibility(which == 0 ? View.GONE : View.VISIBLE);
//                            llSensorContainer.setVisibility(which == 0 ? View.VISIBLE : View.GONE);
//                            tvTriggerType.setText(triggerTypeArr[which]);
//                        }).show();

        } else if (id == R.id.tv_linkage_type) {
            new AlertDialog.Builder(_mActivity)
                    .setItems(linkageTypeArr, (dialog, which) -> {
                        llSceneGoneContainer.setVisibility(which == 0 ? View.VISIBLE : View.GONE);
                        tvLinkageType.setText(linkageTypeArr[which]);
                        tvDeviceSence.setText("请选择");
                    }).show();

        } else if (id == R.id.ll_week) {
            new AlertDialog.Builder(_mActivity)
                    .setMultiChoiceItems(weekArr, weekBoolArr,
                            (dialog, which, isChecked) -> weekBoolArr[which] = isChecked)
                    .setCancelable(false)
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", (dialog, which) -> {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i <= weekBoolArr.length; i++) {
                            if (weekBoolArr[i - 1]) {
                                if (TextUtils.isEmpty(sb.toString())) {
                                    sb.append(i);
                                } else {
                                    sb.append(" , " + i);
                                }
                            }
                        }
                        tvWeek.setText("周：" + sb.toString());
                    })
                    .show();

        } else if (id == R.id.ll_time) {
            new TimePickerDialog(_mActivity, (view1, hourOfDay, minute) -> {
                StringBuilder sb = new StringBuilder();
                if (hourOfDay < 10) {
                    sb.append("0" + hourOfDay);
                } else {
                    sb.append(hourOfDay);
                }
                sb.append(":");
                if (minute < 10) {
                    sb.append("0" + minute);
                } else {
                    sb.append(minute);
                }
                tvTime.setText(sb.toString());
            }, 0, 0, true)
                    .show();

        } else if (id == R.id.tv_sensor) {
            params.category = Constants.SENSOR;
            isSensor = true;
            mPresenter.requestDeviceList(params);

        } else if (id == R.id.tv_device_sence) {//
            if (linkageTypeArr[0].equals(tvLinkageType.getText().toString())) {
                ALog.e(TAG, "请求设备");
                params.category = Constants.DEVICE_CTRL;
                isSensor = false;
                mPresenter.requestDeviceList(params);
            } else {
                ALog.e(TAG, "请求场景");
                mPresenter.requestSceneList(params);
            }

        } else if (id == R.id.tv_sensor_action) {
            if (selectedSensor == null) {
                DialogHelper.warningSnackbar(getView(), "请选择探测器");
                return;
            }
            String[] sensorActionArr = new String[selectedSensor.getActions().size()];
            for (int i = 0; i < selectedSensor.getActions().size(); i++) {
                sensorActionArr[i] = selectedSensor.getActions().get(i).getName();
            }
            new AlertDialog.Builder(_mActivity)
                    .setItems(sensorActionArr, (dialog, which) -> {
                        tvSensorAction.setText(sensorActionArr[which]);
                        selectedSensorAct = selectedSensor.getActions().get(which);
                    }).show();

        } else if (id == R.id.tv_device_action || id == R.id.tv_device_action_2) {
            if (selectedDevice == null) {
                DialogHelper.warningSnackbar(getView(), "请选择设备");
                return;
            }
            String[] deviceActionArr = new String[selectedDevice.getActions().size()];
            for (int i = 0; i < selectedDevice.getActions().size(); i++) {
                deviceActionArr[i] = selectedDevice.getActions().get(i).getName();
            }
            new AlertDialog.Builder(_mActivity)
                    .setItems(deviceActionArr, (dialog, which) -> {
                        if (view.getId() == R.id.tv_device_action) {
                            tvDeviceAction.setText(deviceActionArr[which]);
                            selectedDeviceAct = selectedDevice.getActions().get(which);
                        } else {
                            tvDeviceAction2.setText(deviceActionArr[which]);
                            selectedDeviceAct2 = selectedDevice.getActions().get(which);
                        }
                    }).show();

        } else if (id == R.id.toolbar_menu) {//探测器类型
            if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                DialogHelper.warningSnackbar(getView(), "请输入联动名称");
                return;
            }
            params.name = etName.getText().toString();
            if (triggerTypeArr[0].equals(tvTriggerType.getText().toString())) {
                if (selectedSensor == null) {
                    DialogHelper.warningSnackbar(getView(), "请选择探测器");
                    return;
                }
                if ("请选择".equals(tvSensorAction.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "请选择探测器触发动作");
                    return;
                }
                params.triggerType = Constants.SENSOR;
                params.cdt_sensorId = selectedSensor.getIndex();
                params.cdt_sensorAct = selectedSensorAct.getCmd() + "";
            } else { //时间类型
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < weekBoolArr.length; i++) {
                    if (weekBoolArr[i]) {
                        if (TextUtils.isEmpty(sb.toString())) {
                            sb.append(i);
                        } else {
                            sb.append("," + i);
                        }
                    }
                }
                if (TextUtils.isEmpty(sb.toString())) {
                    DialogHelper.warningSnackbar(getView(), "请选择触发的星期");
                    return;
                }
                params.triggerType = Constants.TIME;
                params.cdt_day = sb.toString();
                params.cdt_time = tvTime.getText().toString();
            }

            //设备联动
            if (linkageTypeArr[0].equals(tvLinkageType.getText().toString())) {
                if ("请选择".equals(tvDeviceSence.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "请选择联动的设备");
                    return;
                }
                if ("请选择".equals(tvDeviceNode.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "请选择要联动到的节点");
                    return;
                }
                if ("请选择".equals(tvDeviceAction.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "请选择联动动作");
                    return;
                }
                if (TextUtils.isEmpty(etMinute.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "请输入延时时长");
                    return;
                }
                if ("请选择".equals(tvDeviceAction2.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "请选择延时联动动作");
                    return;
                }
                params.targetType = Constants.DEVICE;
                params.targetId = selectedDevice.getIndex() + "";
                params.nodeId = tvDeviceNode.getText().toString();
                params.act1 = selectedDeviceAct.getCmd() + "";
                params.act2 = selectedDeviceAct2.getCmd() + "";
                params.delay = etMinute.getText().toString();
            } else {//场景联动
                if ("请选择".equals(tvDeviceSence.getText().toString())) {
                    DialogHelper.warningSnackbar(getView(), "请选择联动的场景");
                    return;
                }
                params.targetType = Constants.SCENE;
                params.targetId = selectedScene.getIndex() + "";
            }
            mPresenter.requestNewLinkage(params);

        } else if (id == R.id.tv_device_node) {
            if ("请选择".equals(tvDeviceSence.getText().toString())) {
                DialogHelper.warningSnackbar(getView(), "请选择联动的设备");
                return;
            }
            String[] nodeArr = new String[selectedDevice.getExtInfo().getNode()];
            for (int i = 0; i < selectedDevice.getExtInfo().getNode(); i++) {
                nodeArr[i] = i + "";
            }
            new AlertDialog.Builder(_mActivity)
                    .setItems(nodeArr, (dialog, which) -> {
                        tvDeviceNode.setText(nodeArr[which]);
                    }).show();

        } else {
        }
    }
}