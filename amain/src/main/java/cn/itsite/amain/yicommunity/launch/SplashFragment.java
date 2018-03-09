package cn.itsite.amain.yicommunity.launch;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libhttp.entity.LoginResult;
import com.libhttp.subscribers.SubscriberListener;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PSpecial.HttpErrorCode;
import com.p2p.core.P2PSpecial.HttpSend;
import com.sipphone.sdk.access.WebReponse;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import butterknife.ButterKnife;
import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.common.RxManager;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.camera.P2PListener;
import cn.itsite.amain.s1.camera.SettingListener;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.DoorManager;
import cn.itsite.amain.yicommunity.common.LbsManager;
import cn.itsite.amain.yicommunity.common.UserHelper;
import cn.itsite.amain.yicommunity.main.MainActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class SplashFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = SplashFragment.class.getSimpleName();
    private static final int LOCATION = 122;
    private RxManager mRxManager = new RxManager();

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        location();
        initDoorManager();
        checkLogin();
        cameraLogin();
    }

    private void cameraLogin() {
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
                        P2PHandler.getInstance().p2pInit(_mActivity, new P2PListener(), new SettingListener());
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
                        ToastUtils.showToast(_mActivity, "用户不存在");
                        break;
                    case HttpErrorCode.ERROR_10902003:
                        ToastUtils.showToast(_mActivity, "密码错误");
                        break;
                    default:
                        //其它错误码需要用户自己实现
                        ToastUtils.showToast(_mActivity, "登录失败:" + loginResult.getError_code());
                        break;
                }
            }

            @Override
            public void onError(String error_code, Throwable throwable) {
//                dismissLoading();
                ToastUtils.showToast(_mActivity, "登录失败 error：" + error_code);
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

    private void saveAuthor(LoginResult loginResult) {
        int code1 = Integer.parseInt(loginResult.getP2PVerifyCode1());
        int code2 = Integer.parseInt(loginResult.getP2PVerifyCode2());
        String sessionId = loginResult.getSessionID();
        String sessionId2 = loginResult.getSessionID2();
        String userId = loginResult.getUserID();
        SharedPreferences sp = _mActivity.getSharedPreferences("Account", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("code1", code1);
        editor.putInt("code2", code2);
        editor.putString("sessionId", sessionId);
        editor.putString("sessionId2", sessionId2);
        editor.putString("userId", userId);
        ALog.e(TAG, "userid:" + userId);
        editor.apply();
    }

    private void initDoorManager() {
        DoorManager.getInstance().startService();
    }

    @AfterPermissionGranted(LOCATION)
    private void location() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(_mActivity, perms)) {
            //有权限就直接进行定位操作
//            ToastUtils.showToast(App.mContext, "正在定位……");
//            initLocate();最好还是不需要定位，只记录手动选择的城市和小区。定位只是辅助筛选。

            LbsManager.getInstance().startLocation(aMapLocation -> {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        LbsManager.getInstance().stopLocation();
                    }
                }
            });

        } else {
            EasyPermissions.requestPermissions(this, "亿社区需要定位权限", LOCATION, perms);
            ToastUtils.showToast(App.mContext, "申请权限……");
        }
    }

    private void checkLogin() {
        mRxManager.add(HttpHelper.getService(ApiService.class)
                .requestCheckToken(ApiService.requestCheckToken, UserHelper.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        if (bean.getData().getStatus() == 1) {
                            UserHelper.clear();
                            go2Main();
                        } else if (bean.getData().getStatus() == 0) {
                            checkSip();
                        }
                    } else {
                        go2Main();
                    }
                }, throwable -> {
                    ALog.e(throwable);
                    go2Main();
                }));
    }

    private void checkSip() {
        DoorManager.getInstance().initWebUserApi(UserHelper.sip, new DoorManager.AccessCallBack() {
            @Override
            public void onPreAccessToken() {
                go2Main();
            }

            @Override
            public void onPostAccessToken(WebReponse webReponse) {
                ALog.e("webReponse-->" + webReponse.getStatusCode());
                go2Main();
            }
        });
    }

    private void go2Main() {
        boolean welcomed = (boolean) SPCache.get(_mActivity, Constants.SP_KEY_WELCOME, false);
        if (!welcomed) {
            startActivity(new Intent(_mActivity, WelcomeActivity.class));
        } else {
            startActivity(new Intent(_mActivity, MainActivity.class));
        }
        _mActivity.overridePendingTransition(0, 0);
        //此处之所以延迟退出是因为立即退出在小米手机上会有一个退出跳转动画，而我不想要这个垂直退出的跳转动画。
        new Handler().postDelayed(() -> _mActivity.finish(), 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        ALog.e(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ALog.e(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //这里需要重新设置Rationale和title，否则默认是英文格式
            new AppSettingsDialog.Builder(this)
                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
                    .setTitle("必需权限")
                    .build()
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            ALog.e(TAG, "onPermissionsGranted:" + requestCode + ":" + resultCode);
        }
    }

    @Override
    public void onStop() {
        LbsManager.getInstance().stopLocation();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        ALog.e(TAG + "onDestroyView");
        mRxManager.clear();
        super.onDestroyView();
    }
}
