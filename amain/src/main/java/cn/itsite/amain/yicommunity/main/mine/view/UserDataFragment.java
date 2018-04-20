package cn.itsite.amain.yicommunity.main.mine.view;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.KeyBoardUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.common.UserBean;
import cn.itsite.amain.yicommunity.event.EventData;
import cn.itsite.amain.yicommunity.main.mine.contract.UserDataContract;
import cn.itsite.amain.yicommunity.main.mine.presenter.UserDataPresenter;


/**
 * Author: LiuJia on 2017/4/21 18:06.
 * Email: liujia95me@126.com
 */
public class UserDataFragment extends BaseFragment<UserDataContract.Presenter> implements UserDataContract.View, View.OnClickListener {
    private static final String TAG = UserDataFragment.class.getSimpleName();
    ImageView ivPortrait;
    LinearLayout llPortrait;
    EditText etNickname;
    LinearLayout llNickname;
    TextView tvGender;
    LinearLayout llGender;
    TextView tvPhone;
    LinearLayout llPhone;
    LinearLayout llChangePassword;
    TextView toolbarTitle;
    Toolbar toolbar;
    EditText etPresentPassword;
    EditText etNewPassword;
    EditText etReput;
    Button btSubmit;
    LinearLayout llChangePasswordLayout;
    TextView toolbarMenu;
    private Params params = Params.getInstance();
    private static final String[] arrGender = {"保密", "男", "女"};

    public static UserDataFragment newInstance() {
        return new UserDataFragment();
    }

    @NonNull
    @Override
    protected UserDataContract.Presenter createPresenter() {
        return new UserDataPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_data, container, false);
        ivPortrait = ((ImageView) view.findViewById(R.id.iv_portrait_user_data_fragment));
        llPortrait = ((LinearLayout) view.findViewById(R.id.ll_portrait_user_data_fragment));
        etNickname = ((EditText) view.findViewById(R.id.et_nickname_user_data_fragment));
        llNickname = ((LinearLayout) view.findViewById(R.id.ll_nickname_user_data_fragment));
        tvGender = ((TextView) view.findViewById(R.id.tv_gender_user_data_fragment));
        llGender = ((LinearLayout) view.findViewById(R.id.ll_gender_user_data_fragment));
        tvPhone = ((TextView) view.findViewById(R.id.tv_phone_user_data_fragment));
        llPhone = ((LinearLayout) view.findViewById(R.id.ll_phone_user_data_fragment));
        llChangePassword = ((LinearLayout) view.findViewById(R.id.ll_change_password_user_data_fragment));
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        etPresentPassword = ((EditText) view.findViewById(R.id.et_present_password_user_data_fragment));
        etNewPassword = ((EditText) view.findViewById(R.id.et_new_password_user_data_fragment));
        etReput = ((EditText) view.findViewById(R.id.et_reput_user_data_fragment));
        btSubmit = ((Button) view.findViewById(R.id.bt_submit_user_data_fragment));
        llChangePasswordLayout = ((LinearLayout) view.findViewById(R.id.ll_change_password_layout_user_data_fragment));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
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
        llPortrait.setOnClickListener(this);
        llNickname.setOnClickListener(this);
        llGender.setOnClickListener(this);
        llPhone.setOnClickListener(this);
        llChangePassword.setOnClickListener(this);
        toolbarMenu.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
    }

    private void initData() {
        Glide.with(this)
                .load(UserHelper.userInfo.getFace())
                .apply(new RequestOptions().circleCrop())
                .into(ivPortrait);

        if (UserHelper.getUserInfo() != null
                && !TextUtils.isEmpty(UserHelper.getUserInfo().getNickName())) {
            etNickname.setText(UserHelper.getUserInfo().getNickName());

            switch (UserHelper.getUserInfo().getSex()) {
                case 0:
                    tvGender.setText("保密");
                    break;
                case 1:
                    tvGender.setText("男");
                    break;
                case 2:
                    tvGender.setText("女");
                    break;
                default:
                    break;
            }
            tvPhone.setText(UserHelper.getUserInfo().getMobile());
        }

        etNickname.setOnFocusChangeListener((v, hasFocus) -> {
            etNickname.post(() -> etNickname.selectAll());
        });

    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("个人信息");
        toolbarMenu.setText("保存");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> pop());
    }

    @Override
    public void start(Object response) {
        dismissLoading();

        if (response instanceof String) {
            DialogHelper.successSnackbar(getView(), (String) response);
        }
        UserBean.DataBean.MemberInfoBean userInfo = UserHelper.getUserInfo();

        if (params.field.equals("sex")) {
            int genderIndex = Integer.parseInt(params.val);
            userInfo.setSex(genderIndex);
            tvGender.setText(arrGender[genderIndex]);
        } else {
            userInfo.setNickName(etNickname.getText().toString());
        }
        UserHelper.setUserInfo(userInfo);
        EventBus.getDefault().post(new EventData(Constants.login));
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        dismissLoading();
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(getView(), _mActivity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ALog.d(TAG, "onActivityResult:" + requestCode + " --- :" + resultCode);
        if (resultCode == RESULT_OK && requestCode == 100) {
            ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (medias.size() > 0) {
                File file = new File(medias.get(0).getPath());
                Glide.with(_mActivity)
                        .load(file.getPath())
                        .apply(new RequestOptions().circleCrop())
                        .into(ivPortrait);

                params.file = file;
                showLoading();
                mPresenter.requestChangePortrait(params);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_portrait_user_data_fragment) {
            BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
            config.needCamera(R.drawable.ic_boxing_camera_white) // 支持gif，相机，设置最大选图数
                    .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image); // 设置默认图片占位图，默认无
            Boxing.of(config).withIntent(_mActivity, BoxingActivity.class).start(this, 100);

        } else if (i == R.id.ll_nickname_user_data_fragment) {
            etNickname.selectAll();
        } else if (i == R.id.ll_gender_user_data_fragment) {
            new AlertDialog.Builder(_mActivity).setSingleChoiceItems(arrGender, 0, (dialog, which) -> {
                //网络访问
                dialog.dismiss();
                params.field = "sex";//写死就好
                params.val = which + "";
                showLoading();
                mPresenter.requestUpdateUserData(params);
            }).show();
        } else if (i == R.id.ll_phone_user_data_fragment) {

        } else if (i == R.id.ll_change_password_user_data_fragment) {
            llChangePasswordLayout.setVisibility(llChangePasswordLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        } else if (i == R.id.toolbar_menu) {
            String nickName = etNickname.getText().toString().trim();
            if (TextUtils.isEmpty(nickName)) {
                DialogHelper.warningSnackbar(getView(), "昵称不能为空");
                return;
            }
            //网络请求
            params.field = "nickName";//写死就好
            params.val = nickName;
            showLoading();
            mPresenter.requestUpdateUserData(params);
        } else if (i == R.id.bt_submit_user_data_fragment) {
            params.pwd0 = etPresentPassword.getText().toString();
            params.pwd1 = etNewPassword.getText().toString();
            params.pwd2 = etReput.getText().toString();
            if (TextUtils.isEmpty(params.pwd0) | TextUtils.isEmpty(params.pwd1)
                    | TextUtils.isEmpty(params.pwd2)) {
                DialogHelper.warningSnackbar(getView(), "密码不能为空！");
                return;
            }
            if (!TextUtils.equals(params.pwd1, params.pwd2)) {
                DialogHelper.warningSnackbar(getView(), "两次输入密码不相同！");
                return;
            }
            showLoading();
            mPresenter.requestUpdatePassword(params);
        }
    }
}
