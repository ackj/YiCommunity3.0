package cn.itsite.abase.utils.system;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 适配4.4以上版本 MIUI6、Flyme 和其他 Android6.0 及以上版本状态栏字体颜色
 *
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 *         2017/6/2
 */
public class StatusBarHelper {

    @IntDef({SystemType.MIUI, SystemType.FLYME, SystemType.ANDROID_M, SystemType.OTHER})
    @Retention(RetentionPolicy.SOURCE)
    @interface SystemType {
        int MIUI = 1;
        int FLYME = 2;
        int ANDROID_M = 3;
        int OTHER = 4;
    }

    public static int statusBarLightMode(Activity activity) {
        return statusMode(activity, true);
    }

    public static int statusBarDarkMode(Activity activity) {
        return statusMode(activity, false);
    }

    private static int statusMode(Activity activity, boolean isFontColorDark) {
        @SystemType int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (new MIUIHelper().setStatusBarLightMode(activity, isFontColorDark)) {
                result = SystemType.MIUI;
            } else if (new FlymeHelper().setStatusBarLightMode(activity, isFontColorDark)) {
                result = SystemType.FLYME;
            }
//            else if (new AndroidMHelper().setStatusBarLightMode(activity, isFontColorDark)) {
//                result = ANDROID_M;
//            }
        }
        return result;
    }


    public static void statusBarLightMode(Activity activity, @SystemType int type) {
        statusBarMode(activity, type, true);

    }

    public static void statusBarDarkMode(Activity activity, @SystemType int type) {
        statusBarMode(activity, type, false);
    }

    private static void statusBarMode(Activity activity, @SystemType int type, boolean isFontColorDark) {
        if (type == SystemType.MIUI) {
            new MIUIHelper().setStatusBarLightMode(activity, isFontColorDark);
        } else if (type == SystemType.FLYME) {
            new FlymeHelper().setStatusBarLightMode(activity, isFontColorDark);
        }
//        else if (type == ANDROID_M) {
//            new AndroidMHelper().setStatusBarLightMode(activity, isFontColorDark);
//        }
    }
}
