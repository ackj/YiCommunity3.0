package cn.itsite.amain.yicommunity.common;

import android.content.Intent;
import android.text.TextUtils;

import cn.itsite.abase.log.ALog;

import com.sipphone.sdk.SipCoreManager;
import com.sipphone.sdk.SipCorePreferences;
import com.sipphone.sdk.SipService;
import com.sipphone.sdk.access.WebApiConstants;
import com.sipphone.sdk.access.WebUserApi;

import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreListenerBase;

import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.main.door.call.CallActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Intent.ACTION_MAIN;

/**
 * Author：leguang on 2017/5/5 0009 10:49
 * Email：langmanleguang@qq.com
 * <p>
 * 门禁管理类
 */

public class DoorManager {
    public static final String TAG = DoorManager.class.getSimpleName();
    private static final String WEB_SERVER = Constants.WEB_SERVER;
    private static DoorManager mDoorManager;
    private WebUserApi mWebUserApi;
    private LinphoneCallBack mListener;
    public final static String UUID = Constants.UUID;
    public final static String UserName = "da";//暂时没用到。
    public final static String tenantCode = "T0001";//暂时没用到。

    private DoorManager() {
        ALog.e("WEB_SERVER-->" + WEB_SERVER);
        ALog.e("WEB_SERVER-->" + UUID);
        WebApiConstants.setHttpServer(WEB_SERVER);
    }

    //获取单例
    public static DoorManager getInstance() {
        if (mDoorManager == null) {
            synchronized (DoorManager.class) {
                if (mDoorManager == null) {
                    mDoorManager = new DoorManager();
                }
            }
        }
        return mDoorManager;
    }

    public DoorManager startService() {
        //全视通的要求最好在启动服务之前再停一遍。
        exit();
        App.mContext.startService(new Intent(ACTION_MAIN)
                .setClass(App.mContext, SipService.class));

        Observable.create(o -> {
            try {
                while (!SipService.isReady()) {
                    try {
                        ALog.e(TAG, "while");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.onNext(o);
            } catch (Exception e) {
                o.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    SipService.instance()
                            .setActivityToLaunchOnIncomingReceived(CallActivity.class);
                });
        return this;
    }

    public void exit() {
        // 停止SipService，用户明确的退出
        removeCallListener();
        App.mContext.stopService(new Intent(ACTION_MAIN)
                .setClass(App.mContext, SipService.class));
    }

    public DoorManager initWebUserApi(String userName, AccessCallBack accessCallBack) {
        ALog.e("userName-->" + userName);
        mWebUserApi = new WebUserApi(App.mContext);
        mWebUserApi.setOnAccessTokenListener(accessCallBack);
        mWebUserApi.accessToken(UUID, userName);
        return this;
    }

    public DoorManager addCallListener(LinphoneCallBack callBack) {
        mListener = callBack;

        // 添加监听器对象到Native层的LinhoneCore监听器对象接口
        LinphoneCore lc = SipCoreManager.getLcIfManagerNotDestroyedOrNull();

        if (lc != null) {
            lc.addListener(mListener);

            try {
                // 这里总是报空指针，干脆让这个代码运行多次注册，同时捕获异常。
                SipCorePreferences.instance().setAccountOutboundProxyEnabled(0, true);
                ALog.e(TAG, "成功注册代理服务器…………………………………………");
            } catch (Exception e) {
                ALog.e(TAG, "异常出错了");
                e.printStackTrace();
            }
        }
        return this;
    }

    public DoorManager removeCallListener() {
        LinphoneCore lc = SipCoreManager.getLcIfManagerNotDestroyedOrNull();
        if (lc != null) {
            lc.removeListener(mListener);
        }

        return this;
    }

    public void callOut(String to) {
        if (TextUtils.isEmpty(to)) {
            return;
        }

        try {
            if (!SipCoreManager.getInstance().acceptCallIfIncomingPending()) {
                ALog.e(TAG, "sip:D" + to + "@member");
                SipCoreManager.getInstance().newOutgoingCall("sip:D" + to + "@member");
            }
        } catch (LinphoneCoreException e) {    // 呼叫发生异常，终止当前的Call
            SipCoreManager.getInstance().terminateCall();
        }
    }

    public static class LinphoneCallBack extends LinphoneCoreListenerBase {

    }

    public interface AccessCallBack extends WebUserApi.onAccessTokenListener {

    }

}
