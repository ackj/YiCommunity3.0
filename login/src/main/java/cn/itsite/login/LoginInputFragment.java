package cn.itsite.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.event.EventLoginSuccess;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.RegexUtils;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.contract.LoginContract;
import cn.itsite.login.model.UserInfoBean;
import cn.itsite.login.presenter.LoginPresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by liujia on 03/05/2018.
 */

public class LoginInputFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener {

    private static final String TAG = LoginInputFragment.class.getSimpleName();

    private UserParams params = new UserParams();
    private EditText mEtPwd;
    private EditText mEtPhone;
    private ImageView mIvCanseePwd;

    public static LoginInputFragment newInstance() {
        return new LoginInputFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_login, container, false);
        view.findViewById(R.id.fl_login).setOnClickListener(this);
        view.findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        mIvCanseePwd = view.findViewById(R.id.iv_cansee_pwd);
        mEtPhone = view.findViewById(R.id.et_phone);
        mEtPwd = view.findViewById(R.id.et_pwd);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
    }

    private void initData() {
        mEtPhone.setText(UserHelper.getAccount());
        mEtPhone.setSelection(mEtPhone.getText().toString().length());
        mEtPwd.setText(UserHelper.getPassword());
    }

    private void initListener() {
        mIvCanseePwd.setOnClickListener(this);
    }

    @Override
    public void responseLogin(UserInfoBean bean) {
        DialogHelper.successSnackbar(_mActivity.getCurrentFocus(),"登录成功");
        EventBus.getDefault().post(new EventLoginSuccess());
    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.fl_login){
            if(checkInput()){
                mPresenter.requestLogin(params);
            }
        }else if(view.getId() == R.id.iv_cansee_pwd){
            if(mIvCanseePwd.isSelected()){
                mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }else{
                mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            mEtPwd.setSelection(mEtPwd.getText().toString().length());
            mIvCanseePwd.setSelected(!mIvCanseePwd.isSelected());
        }else if(view.getId()==R.id.tv_forget_pwd){
            ((SupportActivity)_mActivity).start(ForgetPwdFragment.newInstance());
        }
    }

    private boolean checkInput() {
        String username = mEtPhone.getText().toString();
        String pwd = mEtPwd.getText().toString();
        if(!RegexUtils.isMobileExact(username)){
            DialogHelper.warningSnackbar(getView(),"请输入正确的手机号");
            return false;
        }
        if(TextUtils.isEmpty(pwd)){
            DialogHelper.warningSnackbar(getView(),"请输入密码");
            return false;
        }
        params.username = username;
        params.pwd = pwd;
        return true;
    }
}
