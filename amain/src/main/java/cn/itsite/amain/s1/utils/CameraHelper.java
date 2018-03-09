package cn.itsite.amain.s1.utils;

import android.content.SharedPreferences;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.s1.camera.P2PListener;
import cn.itsite.amain.s1.camera.SettingListener;

import com.libhttp.entity.LoginResult;
import com.libhttp.subscribers.SubscriberListener;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PSpecial.HttpErrorCode;
import com.p2p.core.P2PSpecial.HttpSend;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Author: LiuJia on 2017/11/16 0016 11:59.
 * Email: liujia95me@126.com
 */

public class CameraHelper {
    private static final String TAG = CameraHelper.class.getSimpleName();

    public static void cameraLogin() {
        SubscriberListener<LoginResult> subscriberListener = new SubscriberListener<LoginResult>() {

            @Override
            public void onStart() {
//                showLoading();
            }

            @Override
            public void onNext(LoginResult loginResult) {
//                dismissLoading();
                //error code 全部改为了新版,如果没有老版对应 的反馈码则可忽略此错误
                //如果不可以忽略,则反馈给技术支持即可
                switch (loginResult.getError_code()) {
                    case HttpErrorCode.ERROR_0:
                        //成功的逻辑不需要改成下面这样,以下仅演示过程
                        //原有的这部分代码可以不修改
                        //code1与code2是p2p连接的鉴权码,只有在帐号异地登录或者服务器强制刷新(一般不会干这件事)时才会改变
                        //所以可以将code1与code2保存起来,只需在下次登录时刷新即可
                        saveAuthor(loginResult);
                        P2PHandler.getInstance().p2pInit(App.mContext, new P2PListener(), new SettingListener());
                        ALog.e(TAG, "haha1111111");
                        ALog.e(TAG, "session:" + loginResult.getSessionID() + " -- session2:" + loginResult.getSessionID2()
                                + "--code:" + loginResult.getP2PVerifyCode1() + " -- code2:" + loginResult.getP2PVerifyCode2());

                        int sessionid1 = (int) Long.parseLong(loginResult.getSessionID());
                        int sessionid2 = (int) Long.parseLong(loginResult.getSessionID2());

                        P2PHandler.getInstance().p2pConnect(
                                loginResult.getUserID(),
                                sessionid1,
                                sessionid2,
                                Integer.parseInt(loginResult.getP2PVerifyCode1()),
                                Integer.parseInt(loginResult.getP2PVerifyCode2()));

                        ALog.e(TAG, "摄像头登录成功！");
                        break;
                    case HttpErrorCode.ERROR_10902011:
                        ToastUtils.showToast(App.mContext, "用户不存在");
                        break;
                    case HttpErrorCode.ERROR_10902003:
                        ToastUtils.showToast(App.mContext, "密码错误");
                        break;
                    default:
                        //其它错误码需要用户自己实现
                        ToastUtils.showToast(App.mContext, "登录失败:" + loginResult.getError_code());
                        break;
                }
            }

            @Override
            public void onError(String error_code, Throwable throwable) {
//                dismissLoading();
                ToastUtils.showToast(App.mContext, "登录失败 error：" + error_code);
                ALog.e(TAG, "onError:" + throwable.getMessage());
            }
        };

        try {
            HttpSend.getInstance().SpecialEmailLogin("huangyk@aglhz.com", subscriberListener);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static void saveAuthor(LoginResult loginResult) {
        int code1 = Integer.parseInt(loginResult.getP2PVerifyCode1());
        int code2 = Integer.parseInt(loginResult.getP2PVerifyCode2());
        String sessionId = loginResult.getSessionID();
        String sessionId2 = loginResult.getSessionID2();
        String userId = loginResult.getUserID();
        SharedPreferences sp = App.mContext.getSharedPreferences("Account", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("code1", code1);
        editor.putInt("code2", code2);
        editor.putString("sessionId", sessionId);
        editor.putString("sessionId2", sessionId2);
        editor.putString("userId", userId);
        editor.apply();
    }
}
