package cn.itsite.amain.s1.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;

import cn.itsite.abase.common.ActivityHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.amain.s1.net.NetActivity;
import cn.itsite.amain.s1.net.view.SetWifiFragment;


/**
 * Created by leguang on 2017/9/13 0013.
 * Email：langmanleguang@qq.com
 */

public class NetworkConnectReceiver extends BroadcastReceiver {
    public static final String TAG = NetworkConnectReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        ALog.e("actioin:" + action);
        ALog.e(printBundle(extras));

        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {//这个监听wifi的打开与关闭，与wifi的连接无关
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
            ALog.e("WIFI状态", "wifiState:" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    ALog.e("WIFI状态", "wifiState:WIFI_STATE_DISABLED");
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    ALog.e("WIFI状态", "wifiState:WIFI_STATE_DISABLING");
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    ALog.e("WIFI状态", "wifiState:WIFI_STATE_ENABLED");
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    ALog.e("WIFI状态", "wifiState:WIFI_STATE_ENABLING");
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    ALog.e("WIFI状态", "wifiState:WIFI_STATE_UNKNOWN");
                    break;
                default:
            }
        }
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();

                if (state == NetworkInfo.State.CONNECTED) {
                    String ssid = wifiInfo.getSSID();
                    if (ssid.contains(SetWifiFragment.WIFI_NAME)) {
                        Intent intentGo2Net = new Intent(context, NetActivity.class);
                        intentGo2Net.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intentGo2Net.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intentGo2Net);
                    } else {
                        ActivityHelper.getInstance().finishActivity(NetActivity.class);
                    }
                } else {
                    ActivityHelper.getInstance().finishActivity(NetActivity.class);
                }
            }
        }
    }

    private String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(WifiManager.EXTRA_WIFI_STATE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        ALog.e("bundle:" + bundle);
        return sb.toString();
    }
}
