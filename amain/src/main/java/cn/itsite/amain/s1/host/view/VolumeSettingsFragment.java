package cn.itsite.amain.s1.host.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.amain.R;
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

public class VolumeSettingsFragment extends BaseFragment<HostSettingsContract.Presenter> implements HostSettingsContract.View, View.OnClickListener {
    public static final String TAG = VolumeSettingsFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvTips;
    private TextView tvMessage;
    private TextView tvAlarm;
    private Params params = Params.getInstance();
    private List<String> volumes = new ArrayList<>();
    private TextView tvCurrent;

    public static VolumeSettingsFragment newInstance() {
        return new VolumeSettingsFragment();
    }

    @NonNull
    @Override
    protected HostSettingsContract.Presenter createPresenter() {
        return new HostSettingsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volume_settings, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvTips = ((TextView) view.findViewById(R.id.tv_tips_volume_settings_fragment));
        tvMessage = ((TextView) view.findViewById(R.id.tv_message_volume_settings_fragment));
        tvAlarm = ((TextView) view.findViewById(R.id.tv_alarm_volume_settings_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initData() {
        params.type = Constants.VOLUME;
        mPresenter.requestHostSettings(params);
        volumes.add("静音");
        volumes.add("小声");
        volumes.add("大声");
    }

    private void initListener() {
        tvTips.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        tvAlarm.setOnClickListener(this);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("音量设置");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    @Override
    public void responseSetHost(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        tvCurrent.setText((String) tvCurrent.getTag());
    }

    @Override
    public void responseHostSettings(HostSettingsBean hostSettingsBean) {
        HostSettingsBean.DataBean bean = hostSettingsBean.getData();
        tvTips.setText(getVolume(bean.getTone()));
        tvMessage.setText(getVolume(bean.getGsm()));
        tvAlarm.setText(getVolume(bean.getAlarm()));
    }

    @Override
    public void responseGatewayTest(BaseBean baseBean) {

    }

    private String getVolume(int intVolume) {
        String strVolume = "小声";
        switch (intVolume) {
            case 0:
                strVolume = "静音";
                break;
            case 1:
                strVolume = "小声";
                break;
            case 2:
                strVolume = "大声";
                break;
            default:
                break;
        }
        return strVolume;
    }

    private void showSelector(String subType) {
        new SelectorDialogFragment()
                .setTitle("选择音量")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(volumes)
                .setOnItemConvertListener((holder, which, dialog) ->
                        holder.setText(R.id.tv_item_rv_simple_selector, volumes.get(which)))
                .setOnItemClickListener((view, baseViewHolder, which, dialog) -> {
                    dialog.dismiss();
                    params.subType = subType;
                    params.val = which + "";
                    tvCurrent.setTag(volumes.get(which));
                    mPresenter.requestSetHost(params);
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_tips_volume_settings_fragment) {
            tvCurrent = tvTips;
            showSelector(Constants.V_TONE);

        } else if (i == R.id.tv_message_volume_settings_fragment) {
            tvCurrent = tvMessage;
            showSelector(Constants.V_GSM);

        } else if (i == R.id.tv_alarm_volume_settings_fragment) {
            tvCurrent = tvAlarm;
            showSelector(Constants.V_ALARM);

        }
    }
}
