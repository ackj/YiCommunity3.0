package cn.itsite.abase;

import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.itsite.abase.BuildConfig;

import cn.itsite.abase.exception.AppExceptionHandler;
import cn.itsite.abase.log.ALog;
import me.yokeyword.fragmentation.Fragmentation;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class BaseApp extends MultiDexApplication {
    private static final String TAG = BaseApp.class.getSimpleName();
    public static Context mContext;
    //    private RefWatcher mRefWatcher;
    public static String PUSH_TYPE = "";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initData();//根据是不是Debug版本来设置。
        initStrictMode();
        initRouter();
    }

    private void initData() {
        if (BuildConfig.DEBUG) {
            Fragmentation.builder()
                    .stackViewMode(Fragmentation.BUBBLE)
                    .install();
            ALog.init(true, "debug-ysq");

            //初始化内存泄露监听
//            mRefWatcher = LeakCanary.install(this);
            // 初始化卡顿监听
//            BlockCanary.install(this, new BlockCanaryContext() {
//                @Override
//                public String provideQualifier() {
//                    String qualifier = "";
//                    try {
//                        PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
//                        qualifier += info.versionCode + "_" + info.versionName + "_YYB";
//                    } catch (PackageManager.NameNotFoundException e) {
//                        Log.e(TAG, "provideQualifier exception", e);
//                    }
//                    return qualifier;
//                }
//
//                @Override
//                public String provideUid() {
//                    return "87224330";
//                }
//
//                @Override
//                public String provideNetworkType() {
//                    return "4G";
//                }
//
//                @Override
//                public int provideMonitorDuration() {
//                    return 9999;
//                }
//
//                @Override
//                public int provideBlockThreshold() {
//                    return 500;
//                }
//
//                @Override
//                public boolean displayNotification() {
//                    return BuildConfig.DEBUG;
//                }
//
//                @Override
//                public List<String> concernPackages() {
//                    List<String> list = super.provideWhiteList();
//                    list.add("com.example");
//                    return list;
//                }
//
//                @Override
//                public List<String> provideWhiteList() {
//                    List<String> list = super.provideWhiteList();
//                    list.add("com.whitelist");
//                    return list;
//                }
//
//                @Override
//                public boolean stopWhenDebugging() {
//                    return true;
//                }
//            }).start();

        } else {
            ALog.init(false, "release-ysq");//在release版中禁止打印log。
            Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler.getInstance(this));//在release版中处理全局异常。
        }
    }


    private void initStrictMode() {
// 分别为MainThread和VM设置Strict Mode
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
//                    .detectResourceMismatches()
                    .detectCustomSlowCalls()
                    .penaltyLog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .detectLeakedRegistrationObjects()
                    .detectActivityLeaks()
                    .penaltyLog()
                    .build());
        }
    }

    private void initRouter() {
        if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}
