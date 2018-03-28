package cn.itsite.amain.yicommunity.main.door.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.acommon.UserHelper;
import cn.itsite.amain.yicommunity.common.share.WxShare;
import cn.itsite.amain.yicommunity.entity.bean.DoorListBean;
import cn.itsite.amain.yicommunity.entity.bean.PasswordBean;
import cn.itsite.amain.yicommunity.main.door.contract.PasswordOpenDoorContract;
import cn.itsite.amain.yicommunity.main.door.presenter.PasswordOpenDoorPresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/4/21 9:54.
 * Email: liujia95me@126.com
 * [密码开门]的View层
 * 打开方式：Start App-->管家-->智慧门禁[密码开门]
 */
public class PasswordOpenDoorFragment extends BaseFragment<PasswordOpenDoorContract.Presenter> implements PasswordOpenDoorContract.View, View.OnClickListener {
    private static final String TAG = PasswordOpenDoorFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvPassword;
    private Button btShare;
    private EditText etDuration;
    private EditText etTime;
    private TextView tvDoorName;
    private LinearLayout llDoorName;
    private Params params = Params.getInstance();
    private List<DoorListBean.DataBean> doors;
    private Button btPassword;

    public static PasswordOpenDoorFragment newInstance() {
        return new PasswordOpenDoorFragment();
    }

    @NonNull
    @Override
    protected PasswordOpenDoorContract.Presenter createPresenter() {
        return new PasswordOpenDoorPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_opendoor, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvPassword = ((TextView) view.findViewById(R.id.tv_password));
        btShare = ((Button) view.findViewById(R.id.bt_share));
        etDuration = ((EditText) view.findViewById(R.id.et_duration_password_opendoor_fragment));
        etTime = ((EditText) view.findViewById(R.id.et_time_password_opendoor_fragment));
        tvDoorName = ((TextView) view.findViewById(R.id.tv_door_name_password_opendoor_fragment));
        llDoorName = ((LinearLayout) view.findViewById(R.id.ll_door_name_password_opendoor_fragment));
        btPassword = ((Button) view.findViewById(R.id.bt_password));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initDate();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("密码开门");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initDate() {
        mPresenter.requestDoors(params);
    }

    private void initListener() {
        btShare.setOnClickListener(this);
        llDoorName.setOnClickListener(this);
        btPassword.setOnClickListener(this);
    }

    /**
     * 响应请求密码
     *
     * @param mPasswordBean
     */
    @Override
    public void responsePassword(PasswordBean mPasswordBean) {
        dismissLoading();
        tvPassword.setTextColor(Color.parseColor("#000000"));
        tvPassword.setText(mPasswordBean.getData().getSecretCode());
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.bt_password) {
            if (TextUtils.isEmpty(tvDoorName.getText().toString())) {
                DialogHelper.errorSnackbar(getView(), "请先选择门禁！");
                return;
            }
            params.timeset = Integer.valueOf(etDuration.getText().toString());
            params.maxTimes = Integer.valueOf(etTime.getText().toString());
            mPresenter.requestPassword(params);
            showLoading();
        } else if (i == R.id.bt_share) {
            if (!tvPassword.getText().toString().matches("^\\d{4}$")) {
                DialogHelper.warningSnackbar(getView(), "请先生成数字密码！");
                return;
            }
            sharePassword();
        } else if (i == R.id.ll_door_name_password_opendoor_fragment) {
            showSelector();
        }
    }

    /**
     * 密码分享
     */
    private void sharePassword() {
        new AlertDialog.Builder(_mActivity)
                .setTitle("选择分享类型")
                .setItems(new String[]{"微信", "短信"}, (dialog, which) -> {
                    switch (which) {
                        case 0: //微信
                            WxShare.sendText(createMessage());
                            break;
                        case 1: //短信
                            sendSMS();
                            break;
                        default:
                            break;
                    }
                }).show();
    }

    /**
     * 短信分享
     */
    private void sendSMS() {
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        //短信内容

        sendIntent.putExtra("sms_body", createMessage());
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent, 1002);
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        dismissLoading();
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    @Override
    public void responseDoors(DoorListBean data) {
        if (data == null || data.getData() == null
                || data.getData().isEmpty()) {
            return;
        }
        doors = data.getData();
        DoorListBean.DataBean dataBean = doors.get(0);
        tvDoorName.setText(dataBean.getName());
        params.dir = dataBean.getDir();
    }

    public String createMessage() {
        StringBuffer sb = new StringBuffer()
                .append("很高兴为您服务^_^\r\n本次生成的")
                .append("<" + tvDoorName.getText().toString() + ">")
                .append("临时密码为：")
                .append(tvPassword.getText().toString())
                .append("\r\n")
                .append("有效时长为：")
                .append(etDuration.getText().toString())
                .append("分钟")
                .append("\r\n")
                .append("有效次数为：")
                .append(etTime.getText().toString())
                .append("次")
                .append("\r\n")
                .append("请妥善保管。");
        return sb.toString();
    }

    private void showSelector() {
        new SelectorDialogFragment()
                .setTitle("请选择门禁")
                .setItemLayoutId(R.layout.item_rv_door_selector)
                .setData(doors)
                .setOnItemConvertListener((holder, position, dialog) -> {
                    DoorListBean.DataBean item = doors.get(position);
                    holder.setText(R.id.tv_name_item_rv_door_selector, item.getName())
                            .setText(R.id.tv_community_item_rv_door_selector, UserHelper.communityName)
                            .setText(R.id.tv_online_item_rv_door_selector, item.isOnline() ? "在线" : "离线")
                            .setTextColor(R.id.tv_online_item_rv_door_selector,
                                    item.isOnline() ? Color.parseColor("#999999") : Color.parseColor("#FF0000"));
                })
                .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                    dialog.dismiss();
                    params.dir = doors.get(position).getDir();
                    tvDoorName.setText(doors.get(position).getName());
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .setHeight(350)
                .show(getChildFragmentManager());
    }
}
