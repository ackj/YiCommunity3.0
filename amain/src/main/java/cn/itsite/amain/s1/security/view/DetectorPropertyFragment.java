package cn.itsite.amain.s1.security.view;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.dd.CircularProgressButton;
import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.common.DefenseLineLevel;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.common.clip.ClipActivity;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.SecurityBean;
import cn.itsite.amain.s1.entity.bean.SubDeviceDetBean;
import cn.itsite.amain.s1.security.contract.DetectorPropertyContract;
import cn.itsite.amain.s1.security.presenter.DetectorPropertyPresenter;
import cn.itsite.apush.event.EventRefreshSecurity;
import me.yokeyword.fragmentation.SupportActivity;


/**
 * Author: 2017/5/2 0002.
 * Email:liujia95me@126.com
 */
public class DetectorPropertyFragment extends BaseFragment<DetectorPropertyContract.Presenter> implements DetectorPropertyContract.View, View.OnClickListener {
    public static final String TAG = DetectorPropertyFragment.class.getSimpleName();
    private final static int RESULT_LOAD_IMAGE = 0x100;
    private final static int RESULT_IMAGE_COMPLETE = 0x101;
    private TextView toolbarTitle;
    private TextView toolbarMenu;
    private Toolbar toolbar;
    private CircularProgressButton cpbDelete;
    private EditText etName;
    private TextView tvLineOfDefense;
    private ImageView ivIcon;
    private SwitchButton sbDetectionDoorWindow;
    private SwitchButton sbAlarmDelay;
    private LinearLayout llDefenseLevel;
    private String[] lineOfDefenseArr = {"在家开启", "在家关闭"};
    private String defenseLevel = DefenseLineLevel.DLL_FIRST;
    private Params params = Params.getInstance();
    private SecurityBean.DataBean.SubDevicesBean deviceBean;
    private LinearLayout llChangeIcon;

    public static DetectorPropertyFragment newInstance(SecurityBean.DataBean.SubDevicesBean bean) {
        DetectorPropertyFragment fragment = new DetectorPropertyFragment();
        Bundle args = new Bundle();
        args.putSerializable("OperateBean", bean);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected DetectorPropertyContract.Presenter createPresenter() {
        return new DetectorPropertyPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            deviceBean = (SecurityBean.DataBean.SubDevicesBean) bundle.getSerializable("OperateBean");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detector_property, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        cpbDelete = ((CircularProgressButton) view.findViewById(R.id.cpb_delete_fragment_detector_property));
        etName = ((EditText) view.findViewById(R.id.et_name));
        tvLineOfDefense = ((TextView) view.findViewById(R.id.tv_line_of_defense));
        ivIcon = ((ImageView) view.findViewById(R.id.iv_icon));
        sbDetectionDoorWindow = ((SwitchButton) view.findViewById(R.id.sb_detection_door_window));
        sbAlarmDelay = ((SwitchButton) view.findViewById(R.id.sb_alarm_delay));
        llDefenseLevel = ((LinearLayout) view.findViewById(R.id.ll_defenseLevel));
        llChangeIcon = ((LinearLayout) view.findViewById(R.id.ll_change_icon));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initListener() {
        sbAlarmDelay.setOnCheckedChangeListener((buttonView, isChecked) ->
                params.alarmDelay = isChecked ? 1 : 0);

        cpbDelete.setOnClickListener(this);
        toolbarMenu.setOnClickListener(this);
        llDefenseLevel.setOnClickListener(this);
        llChangeIcon.setOnClickListener(this);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("探测器属性");
        toolbarMenu.setText("删除");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        if (deviceBean != null) {
            tvLineOfDefense.setText(getLineOfDefenseStr(deviceBean.getDefenseLevel()));
            defenseLevel = deviceBean.getDefenseLevel();
            if (!TextUtils.isEmpty(deviceBean.getName())) {
                etName.setText(deviceBean.getName());
            }
            Glide.with(_mActivity)
                    .load(deviceBean.getIcon())
                    .into(ivIcon);
            sbAlarmDelay.setChecked(deviceBean.getAlarmDelay() == 1);
            params.alarmDelay = deviceBean.getAlarmDelay();
            llDefenseLevel.setEnabled(!deviceBean.getDefenseLevel().equals(DefenseLineLevel.DLL_24HOUR));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ALog.d(TAG, "onActivityResult:" + requestCode + " --- :" + resultCode);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {
            ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (medias.size() > 0) {
                File file = new File(medias.get(0).getPath());
                Intent intent = new Intent(_mActivity, ClipActivity.class);
                intent.putExtra("path", file.getPath());
                startActivityForResult(intent, RESULT_IMAGE_COMPLETE);
            }
        } else if (resultCode == RESULT_OK && requestCode == RESULT_IMAGE_COMPLETE) {
            String path = data.getStringExtra("path");
            ALog.e(TAG, "path------>" + path);
            params.file = new File(path);
            Glide.with(_mActivity)
                    .load(params.file)
                    .into(ivIcon);
        }
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        cpbDelete.setProgress(0);
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    @Override
    public void responseNodifSuccess(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), "修改成功");
    }

    @Override
    public void responseDelSuccess(BaseBean baseBean) {
        cpbDelete.setProgress(100);
        EventBus.getDefault().post(new EventRefreshSecurity());
        DialogHelper.successSnackbar(getView(), "删除成功");
        pop();
    }

    @Override
    public void responseSubDeviceDet(SubDeviceDetBean bean) {
        tvLineOfDefense.setText(getLineOfDefenseStr(bean.getData().getDefenseLevel()));
        defenseLevel = bean.getData().getDefenseLevel();
        if (!TextUtils.isEmpty(bean.getData().getName())) {
            etName.setText(bean.getData().getName());
        }
        Glide.with(_mActivity)
                .load(bean.getData().getIcon())
                .into(ivIcon);
        sbAlarmDelay.setChecked(bean.getData().getAlarmDelay() == 1);
        params.alarmDelay = bean.getData().getAlarmDelay();
    }

    @Override
    public void responseModsensor(BaseBean bean) {
        params.file = null;
        DialogHelper.successSnackbar(getView(), "修改成功");
        pop();
    }

    private String getLineOfDefenseStr(String english) {
        switch (english) {
            case DefenseLineLevel.DLL_FIRST:
                return "在家开启";
            case DefenseLineLevel.DLL_SECOND:
                return "在家关闭";
            case DefenseLineLevel.DLL_24HOUR:
                return "24小时开启";
            default:
                return "";
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_change_icon) {
            BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
            config.needCamera(R.drawable.ic_boxing_camera_white) // 支持gif，相机，设置最大选图数
                    .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image); // 设置默认图片占位图，默认无
            Boxing.of(config).withIntent(_mActivity, BoxingActivity.class).start(this, RESULT_LOAD_IMAGE);

        } else if (i == R.id.cpb_delete_fragment_detector_property) {
            params.index = deviceBean.getIndex();
            params.name = etName.getText().toString().trim();
            if (TextUtils.isEmpty(params.name)) {
                DialogHelper.warningSnackbar(getView(), "名称不能为空");
                return;
            }
            params.defenseLevel = defenseLevel;
            mPresenter.requestModsensor(params);

        } else if (i == R.id.toolbar_menu) {
            cpbDelete.setIndeterminateProgressMode(true);
            if (deviceBean == null) {
                DialogHelper.warningSnackbar(getView(), "删除失败");
                return;
            }
            new AlertDialog.Builder(_mActivity)
                    .setTitle("提示")
                    .setMessage("你确定要删除吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        params.index = deviceBean.getIndex();
                        cpbDelete.setProgress(50);
                        mPresenter.requestDelsensor(params);
                    })
                    .setNegativeButton("取消", null)
                    .show();


        } else if (i == R.id.ll_defenseLevel) {
            new AlertDialog.Builder(_mActivity)
                    .setItems(lineOfDefenseArr, (dialog, which) -> {
                        switch (which) {
                            case 0:
                                defenseLevel = DefenseLineLevel.DLL_FIRST;
                                break;
                            case 1:
                                defenseLevel = DefenseLineLevel.DLL_SECOND;
                                break;
                            default:
                        }
                        tvLineOfDefense.setText(getLineOfDefenseStr(defenseLevel));
                    }).show();

        } else {
        }

    }
}
