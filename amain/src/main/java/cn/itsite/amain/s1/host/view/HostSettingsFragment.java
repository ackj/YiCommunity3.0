package cn.itsite.amain.s1.host.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.RxManager;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.HostSettingsBean;
import cn.itsite.amain.s1.host.contract.HostSettingsContract;
import cn.itsite.amain.s1.host.presenter.HostSettingsPresenter;
import cn.itsite.acommon.UserHelper;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */

public class HostSettingsFragment extends BaseFragment<HostSettingsContract.Presenter> implements HostSettingsContract.View, View.OnClickListener {
    public static final String TAG = HostSettingsFragment.class.getSimpleName();
    public static final int RESULT_HOST_SETTINGS = 1234;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvHostName;
    private TextView tvAlertMessage;
    private TextView tvPush;
    private TextView tvVolume;
    private LinearLayout llHostName;
    private TextView tvAccredit;
    private RxManager mRxManager = new RxManager();
    private Params params = Params.getInstance();
    private TextView tvTestSchema;

    public static HostSettingsFragment newInstance() {
        return new HostSettingsFragment();
    }

    @NonNull
    @Override
    protected HostSettingsContract.Presenter createPresenter() {
        return new HostSettingsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvHostName = ((TextView) view.findViewById(R.id.tv_host_name_host_setting_fragment));
        tvAlertMessage = ((TextView) view.findViewById(R.id.tv_alert_sms_host_setting_fragment));
        tvPush = ((TextView) view.findViewById(R.id.tv_push_host_setting_fragment));
        tvVolume = ((TextView) view.findViewById(R.id.tv_volume_host_setting_fragment));
        llHostName = ((LinearLayout) view.findViewById(R.id.ll_host_name_host_setting_fragment));
        tvAccredit = ((TextView) view.findViewById(R.id.tv_accredit_host_setting_fragment));
        tvTestSchema = ((TextView) view.findViewById(R.id.tv_goto_test_schema));
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
        llHostName.setOnClickListener(this);
        tvAlertMessage.setOnClickListener(this);
        tvPush.setOnClickListener(this);
        tvVolume.setOnClickListener(this);
        tvAccredit.setOnClickListener(this);
        tvTestSchema.setOnClickListener(this);
    }

    private void initData() {
        tvHostName.setText(UserHelper.deviceName);
        tvAccredit.setVisibility(UserHelper.deviceIsManager == 0 ? View.GONE : View.VISIBLE);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("主机设置");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRxManager.clear();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ll_host_name_host_setting_fragment) {
            startForResult(EditHostFragment.newInstance(), RESULT_HOST_SETTINGS);
        } else if (i == R.id.tv_alert_sms_host_setting_fragment) {
            start(AlertSmsFragment.newInstance());
        } else if (i == R.id.tv_push_host_setting_fragment) {
            start(PushSettingsFragment.newInstance());
        } else if (i == R.id.tv_volume_host_setting_fragment) {
            start(VolumeSettingsFragment.newInstance());
        } else if (i == R.id.tv_goto_test_schema) {
            new AlertDialog.Builder(_mActivity)
                    .setTitle("提示")
                    .setMessage("检测模式下设备报警不会推送至安防中心，您可对设备进行报警检测，5分钟后自动退出检测模式！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            params.status = 1;
                            mPresenter.requestGatewayTest(params);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();

        } else if (i == R.id.tv_accredit_host_setting_fragment) {
            start(AuthorizationFragment.newInstance());
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        ALog.e("requestCode-->" + requestCode);
        ALog.e("resultCode-->" + resultCode);
        if (data != null) {
            String name = data.getString("name");
            tvHostName.setText(name);
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(Constants.KEY_HOST, hostBean);
//            setFragmentResult(HostSettingsFragment.RESULT_HOST_SETTINGS, bundle);
        }
    }

    @Override
    public void responseSetHost(BaseBean baseBean) {

    }

    @Override
    public void responseHostSettings(HostSettingsBean baseBean) {

    }

    @Override
    public void responseGatewayTest(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
    }
}
