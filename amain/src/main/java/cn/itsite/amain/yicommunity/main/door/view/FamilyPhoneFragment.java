package cn.itsite.amain.yicommunity.main.door.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.KeyBoardUtils;
import cn.itsite.abase.utils.RegexUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.main.door.contract.FamilyPhoneContract;
import cn.itsite.amain.yicommunity.main.door.presenter.FamilyPhonePresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/9/15 0015 10:51.
 * Email: liujia95me@126.com
 */

public class FamilyPhoneFragment extends BaseFragment<FamilyPhoneContract.Presenter> implements FamilyPhoneContract.View {
    public static final String TAG = QuickOpenDoorFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView toolbarMenu;
    private EditText etPhone;
    private Params params = Params.getInstance();

    public static FamilyPhoneFragment newInstance(String roomDir, String phone) {
        FamilyPhoneFragment fragment = new FamilyPhoneFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomDir", roomDir);
        bundle.putString("phone", phone);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected FamilyPhoneContract.Presenter createPresenter() {
        return new FamilyPhonePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            params.roomDir = getArguments().getString("roomDir");
            params.phoneNo = getArguments().getString("phone");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_phone, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        etPhone = ((EditText) view.findViewById(R.id.et_phone));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initListener() {
        toolbarMenu.setOnClickListener(v -> {
            String phone = etPhone.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                DialogHelper.warningSnackbar(getView(), "请输入亲情号码");
                return;
            }
            if (RegexUtils.isMobileExact(phone) || RegexUtils.isTel(phone)) {
                params.phoneNo = phone;
                mPresenter.requestSetFamilyPhone(params);
            } else {
                DialogHelper.warningSnackbar(getView(), "请输入正确的手机号码或固话号码");
            }
        });
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("设置亲情号码");
        toolbarMenu.setText("保存");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        if (!TextUtils.isEmpty(params.phoneNo)) {
            etPhone.setText(params.phoneNo);
        }
    }

    @Override
    public void onDestroyView() {
        KeyBoardUtils.hideKeybord(etPhone, App.mContext);
        super.onDestroyView();
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    @Override
    public void responseSetFamilyPhone(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), "设置亲情号码成功");
        pop();
    }
}
