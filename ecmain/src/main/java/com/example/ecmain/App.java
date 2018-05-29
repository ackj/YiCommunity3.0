package com.example.ecmain;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;

import org.litepal.LitePal;

import cn.itsite.abase.BaseApp;
import cn.itsite.acommon.boxing.BoxingGlideLoader;
import cn.itsite.apush.PushHelper;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class App extends BaseApp {
    public static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        initRouter();
        LitePal.initialize(this);//初始化ORM。
        initBoxing();
        initPush();
    }

    private void initRouter() {
        //todo：这里官方的注释说调试模式在开启，但是实际使用(realease)过程中不加这两行会报错
        //if (BuildConfig.DEBUG) {// 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        //}
        ARouter.init(this);
    }

    private void initBoxing() {
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        //BoxingCrop.getInstance().init(new BoxingUcrop());初始化图片裁剪（可选）
    }

    /**
     * 初始化推送。
     */
    public void initPush() {
        PushHelper.init(this);
    }
}
