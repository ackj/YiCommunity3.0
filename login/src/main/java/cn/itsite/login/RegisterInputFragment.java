package cn.itsite.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.utils.RegexUtils;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.contract.RegisterContract;
import cn.itsite.login.model.VerifyCodeBean;
import cn.itsite.login.presenter.RegisterPresenter;

/**
 * Author： Administrator on 2018/3/7 0007.
 * Email： liujia95me@126.com
 */

public class RegisterInputFragment extends BaseFragment<RegisterContract.Presenter> implements RegisterContract.View, View.OnClickListener {

    private static final String TAG = RegisterInputFragment.class.getSimpleName();
    private TextView mTvVerifyCode;
    private EditText mEtVerifyCode;
    private EditText mEtPwd;
    private EditText mEtPhone;
    private View mFlSubmit;

    private UserParams params = new UserParams();
    private Thread mVerifyThread;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                mTvVerifyCode.setText("获取验证码");
                mTvVerifyCode.setEnabled(true);
            } else {
                if (mTvVerifyCode == null) {
                    return;
                }
                mTvVerifyCode.setText(msg.what + "秒后重试");
                mTvVerifyCode.setEnabled(false);
            }
        }
    };

    public static RegisterInputFragment newInstance() {
        return new RegisterInputFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected RegisterContract.Presenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_register, container, false);
        mTvVerifyCode = view.findViewById(R.id.tv_verify_code);
        mEtPhone = view.findViewById(R.id.et_phone);
        mEtPwd = view.findViewById(R.id.et_pwd);
        mEtVerifyCode = view.findViewById(R.id.et_verify_code);
        mFlSubmit = view.findViewById(R.id.fl_submit);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
    }

    private void initData() {

    }

    private void initListener() {
        mTvVerifyCode.setOnClickListener(this);
        mFlSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fl_submit) {
            String phone = mEtPhone.getText().toString().trim();
            String verifyCode = mEtVerifyCode.getText().toString().trim();
            String pwd = mEtPwd.getText().toString().trim();
            if (!RegexUtils.isMobileExact(phone)) {
                DialogHelper.warningSnackbar(getView(), "请输入正确的手机号");
            } else if (TextUtils.isEmpty(verifyCode)) {
                DialogHelper.warningSnackbar(getView(), "请输入验证码");
            } else if (TextUtils.isEmpty(pwd)) {
                DialogHelper.warningSnackbar(getView(), "请输入密码");
            } else {
                params.username = phone;
                params.code = verifyCode;
                params.pwd = pwd;
                mPresenter.requestRegister(params);
            }
        } else if (v.getId() == R.id.tv_verify_code) {
            String phone = mEtPhone.getText().toString().trim();
            if (RegexUtils.isMobileExact(phone)) {
                params.phone = phone;
                mPresenter.requestVerifyCode(params);
                mVerifyThread = new Thread(() -> {
                    for (int i = 60; i >= 0; i--) {
                        if (mVerifyThread == null) {
                            return;
                        }
                        mHandler.sendEmptyMessage(i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                mVerifyThread.start();
            } else {
                DialogHelper.warningSnackbar(getView(), "请输入正确的手机号码");
            }
        }
    }

    @Override
    public void responseRegister(BaseOldResponse response) {
        DialogHelper.successSnackbar(getView(),response.getOther().getMessage());
        mEtPhone.setText("");
        mEtVerifyCode.setText("");
        mEtPwd.setText("");
    }

    @Override
    public void responseVerifyCode(VerifyCodeBean bean) {
        DialogHelper.successSnackbar(getView(),"获取验证码成功");
    }
}
