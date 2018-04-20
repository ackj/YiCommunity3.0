package cn.itsite.amain.s1.camera;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.p2p.core.P2PHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.camera.contract.CameraSettingContract;
import cn.itsite.amain.s1.camera.presenter.CameraSettingPresenter;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.event.EventCameraPwdChanged;
import cn.itsite.amain.yicommunity.entity.bean.MainDeviceListBean;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/9/14 0014 17:07.
 * Email: liujia95me@126.com
 */

public class CameraSettingFragment extends BaseFragment<CameraSettingContract.Presenter>
        implements CameraSettingContract.View, View.OnClickListener {
    public static final String TAG = CameraSettingFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvNickname;
    private TextView tvPassword;
    private MainDeviceListBean.DataBean bean;
    private boolean isNickname;
    private Params params = Params.getInstance();
    private LinearLayout llPassword;
    private LinearLayout llVideo;
    private LinearLayout llNickname;

    public static CameraSettingFragment newInstance(MainDeviceListBean.DataBean bean) {
        CameraSettingFragment fragment = new CameraSettingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("OperateBean", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected CameraSettingContract.Presenter createPresenter() {
        return new CameraSettingPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("OperateBean") instanceof MainDeviceListBean.DataBean) {
                bean = (MainDeviceListBean.DataBean) bundle.getSerializable("OperateBean");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_setting, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvNickname = ((TextView) view.findViewById(R.id.tv_nickname));
        tvPassword = ((TextView) view.findViewById(R.id.tv_password));
        llPassword = ((LinearLayout) view.findViewById(R.id.ll_pwd));
        llVideo = ((LinearLayout) view.findViewById(R.id.ll_video));
        llNickname = ((LinearLayout) view.findViewById(R.id.ll_nickname));
        EventBus.getDefault().register(this);
        return view;
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
        toolbarTitle.setText("设置");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        if (bean != null) {
            tvNickname.setText(bean.getName());
            tvPassword.setText(bean.getPassword());
            params.deviceId = bean.getNo();
            params.devicePassword = bean.getPassword();
            params.deviceName = bean.getName();
//            params.deviceType = OperateBean.getDeviceType();
//            params.index = OperateBean.getIndex();
        }
    }

    private void initListener() {
        llPassword.setOnClickListener(this);
        llVideo.setOnClickListener(this);
        llNickname.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ll_nickname) {
            isNickname = true;
            showInputDialog();
        } else if (i == R.id.ll_pwd) {
            isNickname = false;
            showInputDialog();
        } else if (i == R.id.ll_video) {
            ((SupportActivity) _mActivity).start(CameraFileRecordFragment.newInstance(bean.getNo(), bean.getPassword()));
        }
    }

    private void showInputDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_add_authorization)
                .setConvertListener((holder, dialog) -> {
                    EditText etInput = holder.getView(R.id.et_input_phone);
                    if (isNickname) {
                        etInput.setText(bean.getName());
                        etInput.setSelection(bean.getName().length());
                    } else {
                        etInput.setText(bean.getPassword());
                        etInput.setSelection(bean.getPassword().length());
                    }

                    holder.setText(R.id.tv_title, isNickname ? "请输入昵称" : "请输入密码")
                            .setOnClickListener(R.id.tv_cancel, v -> dialog.dismiss())
                            .setOnClickListener(R.id.tv_comfirm, v -> {
                                String result = etInput.getText().toString().trim();
                                if (TextUtils.isEmpty(result)) {
                                    DialogHelper.warningSnackbar(getView(), "请输入内容");
                                } else {
                                    showLoading();
//                                    params.fid = OperateBean.getFid();
                                    if (isNickname) {
                                        params.deviceName = result;
                                        mPresenter.requestModCamera(params);
                                    } else {
                                        params.devicePassword = result;
                                        mPresenter.requestModCamera(params);
//                                        updatePassword(result);
                                    }
                                    dialog.dismiss();
                                }
                            });
                })
                .setMargin(40)
                .setDimAmount(0.3f)
                .setGravity(Gravity.CENTER)
                .show(getFragmentManager());

    }

    private void updatePassword(String pwd) {
        P2PHandler.getInstance().setDevicePassword(params.deviceId,
                P2PHandler.getInstance().EntryPassword(bean.getPassword()),
                P2PHandler.getInstance().EntryPassword(pwd),
                pwd, pwd);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCameraPwdChanged event) {
        if (event.result == 0) {
//            mPresenter.requestModCamera(params);
            bean.setPassword(params.devicePassword);
        } else {
            dismissLoading();
            DialogHelper.errorSnackbar(getView(), "修改密码失败");
        }
    }

    @Override
    public void responseSuccess(BaseBean baseBean) {
        dismissLoading();
        if (isNickname) {
            bean.setName(params.deviceName);
            tvNickname.setText(params.deviceName);
        } else {
            bean.setPassword(params.devicePassword);
            tvPassword.setText(params.devicePassword);
            updatePassword(params.devicePassword);
        }
        DialogHelper.successSnackbar(getView(), "修改成功");
    }
}
