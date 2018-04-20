package cn.itsite.amain.yicommunity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;

import org.litepal.LitePal;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.amain.yicommunity.common.boxing.BoxingGlideLoader;
import cn.itsite.apush.PushHelper;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class App extends cn.itsite.amain.s1.App implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = App.class.getSimpleName();
    public static App mApp;
    public static String deviceID;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initData();//数据的初始化要在友盟推送之前，因为要注册别名时，用到用户名。
        initPush();//初始化推送。
        initBoxing();//初始化图片选择器。
        LitePal.initialize(this);//初始化ORM。
    }

    private void initData() {
        UserHelper.init();
        registerActivityLifecycleCallbacks(this);
    }

    private void initBoxing() {
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
//        BoxingCrop.getInstance().init(new BoxingUcrop());初始化图片裁剪（可选）
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ALog.e("onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ALog.e("onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ALog.e("onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ALog.e("onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ALog.e("onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ALog.e("onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ALog.e("onActivityDestroyed");
    }

    /**
     * 初始化推送。
     */
    public void initPush() {
        PushHelper.init(this);
    }
}
