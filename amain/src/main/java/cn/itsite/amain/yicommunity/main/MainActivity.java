package cn.itsite.amain.yicommunity.main;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCore;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.abase.utils.system.StatusBarHelper;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.DoorManager;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.main.door.call.CallActivity;
import cn.itsite.amain.yicommunity.main.parking.TempParkActivity;
import cn.itsite.amain.yicommunity.main.view.MainFragment;
import cn.itsite.amain.yicommunity.qrcode.QRCodeActivity;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 主页模块的容器，负责装载一个MainFragment和一些需要在这个容器里初始化的东西。
 */

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarHelper.statusBarLightMode(this);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, MainFragment.newInstance());
        }
        handler = new Handler();
        setCallListener();

    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }

    public void setCallListener() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DoorManager.getInstance().addCallListener(new DoorManager.LinphoneCallBack() {
                    @Override
                    public void callState(LinphoneCore lc, LinphoneCall call, LinphoneCall.State state, String message) {
                        ALog.e("state-->" + state + "-----" + "message-->" + message);
                        if (state == LinphoneCall.State.OutgoingInit || state == LinphoneCall.State.OutgoingProgress) {
                            startActivity(new Intent(MainActivity.this, CallActivity.class));
                        }
                    }
                });
            }
        }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent != null && intent.getExtras() != null) {
            String result = (String) intent.getExtras().get(QRCodeActivity.QRCODE_RESULT);
            if (result == null) return;
            Uri uri = Uri.parse(result);
            String type = uri.getQueryParameter(Constants.TYPE);
            switch (type) {
                case Constants.TYPE_TEMPORARYPARKPAY:
                    intent.setComponent(new ComponentName(this, TempParkActivity.class));
                    startActivity(intent);
                    break;
                case Constants.TYPE_SMARTDOOROPEN:
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainFragment.class.getName());
                    if (fragment instanceof MainFragment) {
                        Params params = Params.getInstance();
                        params.acsStoreDeviceFid = uri.getQueryParameter(Constants.PARAM_ACSSTOREDEVICEFID);
                        params.accessKey = uri.getQueryParameter(Constants.PARAM_ACCESSKEY);
                        ((MainFragment) fragment).scanOpenDoor(params);
                    }
                    break;
                default:
            }
        }
    }
}
