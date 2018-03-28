package cn.itsite.amain.yicommunity.login.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.amain.yicommunity.event.EventData;
import cn.itsite.amain.yicommunity.login.contract.LoginContract;
import cn.itsite.amain.yicommunity.login.presenter.LoginPresenter;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View, TextWatcher, View.OnClickListener {
    public static final String TAG = LoginFragment.class.getSimpleName();
    public static final int LOGIN_REQUEST = 101;
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvForgetPassword;
    private CheckBox cbRemember;
    private Button btLogin;
    private Button btRegister;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private Params params = Params.getInstance();

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @NonNull
    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etUsername = ((EditText) view.findViewById(R.id.et_username));
        etPassword = ((EditText) view.findViewById(R.id.et_password));
        tvForgetPassword = ((TextView) view.findViewById(R.id.tv_forget_password));
        cbRemember = ((CheckBox) view.findViewById(R.id.cb_remember));
        btLogin = ((Button) view.findViewById(R.id.bt_login));
        btRegister = ((Button) view.findViewById(R.id.bt_register));
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
    }

    private void initListener() {
        tvForgetPassword.setOnClickListener(this);
        cbRemember.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    private void initData() {
        etUsername.addTextChangedListener(this);//先监听，这样在下面设置账号密码的时候，
        etPassword.addTextChangedListener(this);//如果账号密码是空或者不为空就能起到作用，省掉了回复按钮可点击的代码。
        if (UserHelper.isRemember()) {
            cbRemember.setChecked(UserHelper.isRemember());
            etUsername.setText(UserHelper.getAccount());
            etPassword.setText(UserHelper.getPassword());
        }

        initStateBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_36dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    @Override
    public void start(Object response) {
        dismissLoading();
        UserHelper.setRemember(cbRemember.isChecked());
        EventBus.getDefault().post(new EventData(Constants.login));
        _mActivity.finish();
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        dismissLoading();
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean enabled = TextUtils.isEmpty(etUsername.getText().toString())
                || TextUtils.isEmpty(etPassword.getText().toString());
        btLogin.setEnabled(!enabled);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == LOGIN_REQUEST && resultCode == SupportFragment.RESULT_OK) {
            etUsername.setText(data.getString(UserHelper.ACCOUNT, ""));
            etPassword.setText(data.getString(UserHelper.PASSWORD, ""));
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_forget_password) {
            startForResult(ResetPasswordFragment.newInstance(), LOGIN_REQUEST);
        } else if (i == R.id.cb_remember) {
        } else if (i == R.id.bt_login) {
            showLoading();
            params.user = etUsername.getText().toString().trim();
            params.pwd = etPassword.getText().toString().trim();
            mPresenter.start(params);
        } else if (i == R.id.bt_register) {
            startForResult(RegisterFragment.newInstance(), LOGIN_REQUEST);
        }
    }
}


