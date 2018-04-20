package cn.itsite.amain.s1.host.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.KeyBoardUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.HostSettingsBean;
import cn.itsite.amain.s1.host.contract.HostSettingsContract;
import cn.itsite.amain.s1.host.presenter.HostSettingsPresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */
public class AlertSmsFragment extends BaseFragment<HostSettingsContract.Presenter> implements HostSettingsContract.View {
    public static final String TAG = AlertSmsFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private TextView toolbarMenu;
    private Toolbar toolbar;
    private EditText etPhone1;
    private EditText etPhone2;
    private Params params = Params.getInstance();

    public static AlertSmsFragment newInstance() {
        return new AlertSmsFragment();
    }

    @NonNull
    @Override
    protected HostSettingsContract.Presenter createPresenter() {
        return new HostSettingsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_sms, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        etPhone1 = ((EditText) view.findViewById(R.id.et_phone1_alert_sms_fragment));
        etPhone2 = ((EditText) view.findViewById(R.id.et_phone2_alert_sms_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initData() {
        params.type = Constants.PHONE;
        mPresenter.requestHostSettings(params);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("报警短信");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
        toolbarMenu.setText("保存");
        toolbarMenu.setOnClickListener(v -> {
            params.subType = Constants.P_PUSH;
            params.val = etPhone1.getText().toString().trim() + "," + etPhone2.getText().toString().trim();
            mPresenter.requestSetHost(params);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(etPhone1, App.mContext);//必须在unbind之前调用。
    }

    @Override
    public void responseSetHost(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        pop();
    }

    @Override
    public void responseHostSettings(HostSettingsBean bean) {
        etPhone1.setText(bean.getData().getSmspush_number1());
        etPhone2.setText(bean.getData().getSmspush_number2());
    }

    @Override
    public void responseGatewayTest(BaseBean baseBean) {

    }
}
