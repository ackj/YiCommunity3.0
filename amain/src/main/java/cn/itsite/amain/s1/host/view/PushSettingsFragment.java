package cn.itsite.amain.s1.host.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
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

public class PushSettingsFragment extends BaseFragment<HostSettingsContract.Presenter> implements HostSettingsContract.View, View.OnClickListener {
    public static final String TAG = PushSettingsFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private SwitchButton sbWaijiedianyuandiaodian;
    private LinearLayout llWaijiedianyuandiaodian;
    private SwitchButton sbHuifuwaijiedianyuan;
    private LinearLayout llHuifuwaijiedianyuan;
    private SwitchButton sbBufangchefang;
    private LinearLayout llBufangchefang;
    private SwitchButton sbZhujidianchidianliangdi;
    private LinearLayout llZhujidianchidianliangdi;
    private SwitchButton sbChuanganqidianliangdi;
    private LinearLayout llChuanganqidianliangdi;
    private SwitchButton sbWifilianjei;
    private LinearLayout llWifilianjei;
    private SwitchButton sbWifiduankai;
    private LinearLayout llWifiduankai;
    private SwitchButton sbDuanxintuisong;
    private LinearLayout llDuanxintuisong;
    private Params params = Params.getInstance();
    private SwitchButton sbCurrent;

    public static PushSettingsFragment newInstance() {
        return new PushSettingsFragment();
    }

    @NonNull
    @Override
    protected HostSettingsContract.Presenter createPresenter() {
        return new HostSettingsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_settings, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        sbWaijiedianyuandiaodian = ((SwitchButton) view.findViewById(R.id.sb_waijiedianyuandiaodian));
        llWaijiedianyuandiaodian = ((LinearLayout) view.findViewById(R.id.ll_waijiedianyuandiaodian));
        sbHuifuwaijiedianyuan = ((SwitchButton) view.findViewById(R.id.sb_huifuwaijiedianyuan));
        llHuifuwaijiedianyuan = ((LinearLayout) view.findViewById(R.id.ll_huifuwaijiedianyuan));
        sbBufangchefang = ((SwitchButton) view.findViewById(R.id.sb_bufangchefang));
        llBufangchefang = ((LinearLayout) view.findViewById(R.id.ll_bufangchefang));
        sbZhujidianchidianliangdi = ((SwitchButton) view.findViewById(R.id.sb_zhujidianchidianliangdi));
        llZhujidianchidianliangdi = ((LinearLayout) view.findViewById(R.id.ll_zhujidianchidianliangdi));
        sbChuanganqidianliangdi = ((SwitchButton) view.findViewById(R.id.sb_chuanganqidianliangdi));
        llChuanganqidianliangdi = ((LinearLayout) view.findViewById(R.id.ll_chuanganqidianliangdi));
        sbWifilianjei = ((SwitchButton) view.findViewById(R.id.sb_wifilianjei));
        llWifilianjei = ((LinearLayout) view.findViewById(R.id.ll_wifilianjei));
        sbWifiduankai = ((SwitchButton) view.findViewById(R.id.sb_wifiduankai));
        llWifiduankai = ((LinearLayout) view.findViewById(R.id.ll_wifiduankai));
        sbDuanxintuisong = ((SwitchButton) view.findViewById(R.id.sb_duanxintuisong));
        llDuanxintuisong = ((LinearLayout) view.findViewById(R.id.ll_duanxintuisong));
        return attachToSwipeBack(view);
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
        toolbarTitle.setText("推送设置");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        params.type = Constants.PUSH;
        mPresenter.requestHostSettings(params);
    }

    private void initListener() {
        llWaijiedianyuandiaodian.setOnClickListener(this);
        llHuifuwaijiedianyuan.setOnClickListener(this);
        llBufangchefang.setOnClickListener(this);
        llZhujidianchidianliangdi.setOnClickListener(this);
        llChuanganqidianliangdi.setOnClickListener(this);
        llWifilianjei.setOnClickListener(this);
        llWifiduankai.setOnClickListener(this);
        llDuanxintuisong.setOnClickListener(this);
    }

    @Override
    public void responseSetHost(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        sbCurrent.setChecked(params.val.equals("1"));
    }

    @Override
    public void responseHostSettings(HostSettingsBean baseBean) {
        HostSettingsBean.DataBean bean = baseBean.getData();
        sbWaijiedianyuandiaodian.setChecked(bean.getPower_fail() == 1);
        sbHuifuwaijiedianyuan.setChecked(bean.getPower_recover() == 1);
        sbBufangchefang.setChecked(bean.getDefense_chg() == 1);
        sbZhujidianchidianliangdi.setChecked(bean.getHost_power_low() == 1);
        sbChuanganqidianliangdi.setChecked(bean.getSensor_power_low() == 1);
        sbWifilianjei.setChecked(bean.getWifi_connect() == 1);
        sbWifiduankai.setChecked(bean.getWifi_disconnect() == 1);
        sbDuanxintuisong.setChecked(bean.getSms_tophone() == 1);
    }

    @Override
    public void responseGatewayTest(BaseBean baseBean) {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_waijiedianyuandiaodian) {
            params.subType = Constants.PS_POWER_FAIL;
            sbCurrent = sbWaijiedianyuandiaodian;

        } else if (i == R.id.ll_huifuwaijiedianyuan) {
            params.subType = Constants.PS_POWER_RECOVER;
            sbCurrent = sbHuifuwaijiedianyuan;

        } else if (i == R.id.ll_bufangchefang) {
            params.subType = Constants.PS_DEFENSE_CHG;
            sbCurrent = sbBufangchefang;

        } else if (i == R.id.ll_zhujidianchidianliangdi) {
            params.subType = Constants.PS_HOST_POWER_LOW;
            sbCurrent = sbZhujidianchidianliangdi;

        } else if (i == R.id.ll_chuanganqidianliangdi) {
            params.subType = Constants.PS_SENSOR_POWER_LOW;
            sbCurrent = sbChuanganqidianliangdi;

        } else if (i == R.id.ll_wifilianjei) {
            params.subType = Constants.PS_WIFI_CONNECT;
            sbCurrent = sbWifilianjei;

        } else if (i == R.id.ll_wifiduankai) {
            params.subType = Constants.PS_WIFI_DISCONNECT;
            sbCurrent = sbWifiduankai;

        } else if (i == R.id.ll_duanxintuisong) {
            params.subType = Constants.PS_SMS_TOPHONE;
            sbCurrent = sbDuanxintuisong;

        }
        //这里之所以这么做是因为不想看到当网络出错的时候，而开关却显示已经切换了，所以为了避免这种情况，在网络访问成功的时候才切换。
        params.val = sbCurrent.isChecked() ? "0" : "1";//是相反的，这里需要注意一下。
        mPresenter.requestSetHost(params);
    }
}
