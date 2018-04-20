package cn.itsite.amain.s1.camera;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hdl.udpsenderlib.UDPResult;
import com.jwkj.soundwave.ResultCallback;
import com.jwkj.soundwave.SoundWaveManager;
import com.jwkj.soundwave.SoundWaveSender;
import com.jwkj.soundwave.bean.NearbyDevice;
import com.mediatek.elian.ElianNative;

import java.util.List;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.s1.widget.CameraCountDownDialog;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/11/9 0009 15:56.
 * Email: liujia95me@126.com
 */

public class CameraWifiInput2Fragment extends BaseFragment {
    public static final String TAG = CameraWifiInput2Fragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvWifiname;
    private EditText etPassword;
    boolean isLinking = false;
    ElianNative elain;
    UDPHelper mHelper;
    //wifi相关
    String wifiName;
    String wifiPassword;
    boolean is5GWifi = false;
    boolean isWifiEncrypt = false;
    WifiManager wifiManager;
    private CameraCountDownDialog countDownDialog;
    private Button btSend;

    public static CameraWifiInput2Fragment newInstance() {
        return new CameraWifiInput2Fragment();
    }

    static {
        System.loadLibrary("elianjni");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_wifi, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvWifiname = ((TextView) view.findViewById(R.id.tv_wifiname));
        etPassword = ((EditText) view.findViewById(R.id.et_password));
        btSend = ((Button) view.findViewById(R.id.btn_send));
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
        toolbarTitle.setText("准备配置");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        countDownDialog = new CameraCountDownDialog();
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        _mActivity.registerReceiver(br, filter);

        notifyCurrenWifi();
        SoundWaveManager.init(_mActivity);//初始化声波配置
        //监听UDP广播
        mHelper = new UDPHelper(_mActivity, 9988);
        mHelper.StartListen();
    }

    @SuppressLint("HandlerLeak")
    private void initListener() {
        mHelper.setCallBack(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case UDPHelper.HANDLER_MESSAGE_BIND_ERROR:
                        ALog.e(TAG, "HANDLER_MESSAGE_BIND_ERROR");
                        break;
                    case UDPHelper.HANDLER_MESSAGE_RECEIVE_MSG:
                        ALog.e(TAG, "智能配网成功");
                        stopLink(true);
                        break;
                    default:
                        break;
                }
            }
        });

        countDownDialog.setOnListener(() -> {
            //停止配网
            stopLink(false);
        });

        btSend.setOnClickListener(v -> {
            if (checkNetwork()) {
                startLink();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.unregisterReceiver(br);
        if (isLinking) {
            stopLink(false);
        }
        if (mHelper != null) {
            mHelper.StopListen();
        }
    }

    private boolean checkNetwork() {
        wifiPassword = etPassword.getText().toString().trim();
        if (!isWifiConnected() || wifiName == null || wifiName.equals("") || wifiName.equals("<unknown ssid>")) {
            ToastUtils.showToast(_mActivity, "请先将手机连接到WiFi");
            return false;
        }
        if (is5GWifi) {
            ToastUtils.showToast(_mActivity, "设备不支持5G网络");
            return false;
        }
        if (!isWifiEncrypt) {
            if (TextUtils.isEmpty(wifiPassword)) {
                ToastUtils.showToast(_mActivity, "请输入WiFi密码");
                return false;
            }
        }
        return true;
    }

    //todo(高亮)-------------------------------  以下是Wifi部分 --------------------------------

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                notifyCurrenWifi();
            }
        }
    };

    private byte mAuthMode;
    private byte AuthModeAutoSwitch = 2;
    private byte AuthModeOpen = 0;
    private byte AuthModeShared = 1;
    private byte AuthModeWPA = 3;
    private byte AuthModeWPA1PSKWPA2PSK = 9;
    private byte AuthModeWPA1WPA2 = 8;
    private byte AuthModeWPA2 = 6;
    private byte AuthModeWPA2PSK = 7;
    private byte AuthModeWPANone = 5;
    private byte AuthModeWPAPSK = 4;

    //获取当前连接wifi
    public void notifyCurrenWifi() {
        WifiInfo wifiInfo = getConnectWifiInfo();
        if (wifiInfo == null) {
            wifiName = "";
            tvWifiname.setText("请先连接wifi");
            return;
        }
        wifiName = wifiInfo.getSSID();
        if (wifiName == null) {
            return;
        }
        if (wifiName.equals("")) {
            return;
        }
        if (wifiName.length() <= 0) {
            return;
        }
        int a = wifiName.charAt(0);
        if (a == 34) {
            wifiName = wifiName.substring(1, wifiName.length() - 1);
        }
        if (!wifiName.equals("<unknown ssid>") && !wifiName.equals("0x")) {
            tvWifiname.setText(wifiName);
        }
        List<ScanResult> wifiList = getLists(_mActivity);
        if (wifiList == null) {
            return;
        }
        for (int i = 0; i < wifiList.size(); i++) {
            ScanResult result = wifiList.get(i);
            if (!result.SSID.equals(wifiName)) {
                continue;
            }
            is5GWifi = is5GWifi(result.frequency);
            isWifiEncrypt = isWifiEncrypt(result);
            boolean bool1, bool2, bool3, bool4;
            bool1 = result.capabilities.contains("WPA-PSK");
            bool2 = result.capabilities.contains("WPA2-PSK");
            bool3 = result.capabilities.contains("WPA-EAP");
            bool4 = result.capabilities.contains("WPA2-EAP");
            if (result.capabilities.contains("WEP")) {
                this.mAuthMode = this.AuthModeOpen;
            }
            if ((bool1) && (bool2)) {
                mAuthMode = AuthModeWPA1PSKWPA2PSK;
            } else if (bool2) {
                this.mAuthMode = this.AuthModeWPA2PSK;
            } else if (bool1) {
                this.mAuthMode = this.AuthModeWPAPSK;
            } else if ((bool3) && (bool4)) {
                this.mAuthMode = this.AuthModeWPA1WPA2;
            } else if (bool4) {
                this.mAuthMode = this.AuthModeWPA2;
            } else {
                if (!bool3) {
                    break;
                }
                this.mAuthMode = this.AuthModeWPA;
            }
        }
    }

    //判断是否连接上wifi
    public boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    //获取当前连接wifi的WifiInfo
    public WifiInfo getConnectWifiInfo() {
        if (!isWifiConnected()) {
            return null;
        }
        WifiManager wifiManager = (WifiManager) App.mContext.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            return null;
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo;
    }

    //获取wifi列表
    public List<ScanResult> getLists(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        List<ScanResult> lists = wifiManager.getScanResults();
        return lists;
    }

    //判断是不是5Gwifi
    public static boolean is5GWifi(int frequency) {
        String str = String.valueOf(frequency);
        if (str.length() > 0) {
            char a = str.charAt(0);
            if (a == '5') {
                return true;
            }
        }
        return false;
    }

    //WiFi是否加密
    public static boolean isWifiEncrypt(ScanResult result) {
        return !(result.capabilities.toLowerCase().indexOf("wep") != -1
                || result.capabilities.toLowerCase().indexOf("wpa") != -1);
    }

    //todo(高亮)-------------------------------  以下是配网部分 --------------------------------

    //智能配网
    private void startSmartLink() {
        if (elain == null) {
            elain = new ElianNative();
        }
        if (null != wifiName && !"".equals(wifiName)) {
            elain.InitSmartConnection(null, 1, 1);
            //wifi名  wifi密码  加密方式
            elain.StartSmartConnection(wifiName, wifiPassword, "", mAuthMode);
        }
    }

    private void stopSmartLink() {
        if (elain != null) {
            elain.StopSmartConnection();
        }
    }

    //声波配网
    private void startSoundWave() {
        ALog.e(TAG, "sendSoundWave 开始发送声波");
        SoundWaveSender.getInstance()
                .with(_mActivity)
                .setWifiSet(wifiName, wifiPassword)
                .send(new ResultCallback() {
                    @Override
                    public void onNext(UDPResult udpResult) {
                        ALog.e(TAG, "声波配网成功:" + udpResult.getIp());
                        NearbyDevice device = NearbyDevice.getDeviceInfoByByteArray(udpResult.getResultData());
                        ALog.e(TAG, "设备信息:" + device.toString());
                        device.setIp(udpResult.getIp());
                        stopLink(true);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        ALog.e(TAG, "声波配网ERROR:" + throwable.getMessage());
                        onStopSend();
                    }

                    /**
                     * 当声波停止的时候
                     */
                    @Override
                    public void onStopSend() {
                        ALog.e(TAG, "声波配网STOP:" + isLinking);
                        if (isLinking) {//是否需要继续发送声波
                            new Thread(() -> {
                                Looper.prepare();
                                startSoundWave();
                            }).start();
                        } else {//结束了就需要将发送器关闭
                            stopLink(false);
                        }
                    }
                });
    }

    public void stopSoundWave() {
        ALog.e(TAG, "onStopSoundWave");
        SoundWaveSender.getInstance().stopSend();
    }

    private void startLink() {
        isLinking = true;
        countDownDialog.show(getFragmentManager());
        startSmartLink();
        startSoundWave();
    }

    private void stopLink(boolean isSuccess) {
        isLinking = false;
        stopSoundWave();
        stopSmartLink();
        if (countDownDialog != null && countDownDialog.isShowing()) {
            countDownDialog.dismiss();
        }
        if (isSuccess) {
            ToastUtils.showToast(_mActivity, "配网成功");
            pop();
        }
    }

}
