package cn.itsite.amain.s1.net.view;

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
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.KeyBoardUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.s1.net.contract.NetContract;
import cn.itsite.amain.s1.net.presenter.NetPresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by leguang on 2017/5/24 0029.
 * Email：langmanleguang@qq.com
 */
public class HotPasswordFragment extends BaseFragment<NetContract.Presenter> implements NetContract.View, TextWatcher {
    private static final String TAG = HotPasswordFragment.class.getSimpleName();
    public static final int CMD_REQUEST_HOST_INFO = 212;
    public static final int CMD_UPDATE_HOST_INFO = 213;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private EditText etPassword;
    private EditText etConfirm;
    private Button btSave;
    private JSONArray jsonArray;

    public static HotPasswordFragment newInstance() {
        return new HotPasswordFragment();
    }

    @NonNull
    @Override
    protected NetContract.Presenter createPresenter() {
        return new NetPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_password, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        etPassword = ((EditText) view.findViewById(R.id.et_password_hot_password_fragment));
        etConfirm = ((EditText) view.findViewById(R.id.et_confirm_hot_password_fragment));
        btSave = ((Button) view.findViewById(R.id.bt_save_hot_password_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("修改热点密码");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        etConfirm.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
        mPresenter.command(CMD_REQUEST_HOST_INFO, "");
        btSave.setOnClickListener(v -> {
            if (!etPassword.getText().toString().equals(etConfirm.getText().toString())) {
                DialogHelper.warningSnackbar(getView(), "两次密码必须相同！");
                return;
            }

            try {
                jsonArray.put(0, CMD_UPDATE_HOST_INFO);
                jsonArray.optJSONArray(1).put(1, etPassword.getText().toString());
                jsonArray.optJSONArray(1).remove(8);
                mPresenter.command(CMD_UPDATE_HOST_INFO, jsonArray.toString());
            } catch (JSONException e) {
                DialogHelper.warningSnackbar(getView(), "设置异常！");
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(etPassword, App.mContext);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean enabled = TextUtils.isEmpty(etPassword.getText().toString())
                || TextUtils.isEmpty(etConfirm.getText().toString());
        btSave.setEnabled(!enabled);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void command(int cmd, String response) {
        switch (cmd) {
            case CMD_REQUEST_HOST_INFO:
                try {
                    jsonArray = new JSONArray(response);
                } catch (JSONException e) {
                    error("数据解析异常！");
                    e.printStackTrace();
                }

                break;
            case CMD_UPDATE_HOST_INFO:
                if (response == null || response.equals("fail")) {
                    DialogHelper.errorSnackbar(getView(), "密码设置失败！");
                } else {
                    DialogHelper.successSnackbar(getView(), "密码设置成功！");
                }
                break;
            default:
                break;
        }
    }
}
