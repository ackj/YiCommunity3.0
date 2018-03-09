package cn.itsite.amain.s1;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.google.gson.Gson;
import com.p2p.core.P2PSpecial.P2PSpecial;

import org.greenrobot.eventbus.EventBus;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.log.ALog;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.entity.bean.NotificationBean;
import cn.itsite.amain.yicommunity.common.UserHelper;
import cn.itsite.amain.yicommunity.common.boxing.BoxingGlideLoader;
import cn.itsite.apush.event.EventLearnSensor;
import cn.itsite.apush.event.EventRefreshSecurity;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */
public class App extends BaseApp implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = App.class.getSimpleName();
    public Gson gson = new Gson();
    //    public final static String APPID="1e9a2c3ead108413e8218a639c540e44";
    public final static String APPID = "0428a5857dc408a3bd621949eee60d63";
    //    public final static String APPToken="7db7b2bff80a025a3dad546a4d5a6c3ee545568d4e0ce9609c0585c71c287d08";
    public final static String APPToken = "e55711e02045ffe1a2fae9a080072cabedd356ff630021c46b8111dac77aec2c";
    //前两位是客户APP唯一编号(00.00 由技威分配),后两位是APP版本号(客户自定义),此参数不可省略
    public final static String APPVersion = "05.42.00.00";

    @Override
    public void onCreate() {
        super.onCreate();
        initDate();
//        initPush();//初始化友盟推送。
        initBoxing();
        initP2P(this);
    }

    private void initDate() {
        UserHelper.init();
    }

    private void initBoxing() {
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
    }

    private void initP2P(App app) {
        P2PSpecial.getInstance().init(app, APPID, APPToken, APPVersion);
    }

    //初始化友盟推送
//    private void initPush() {
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.setDebugMode(true);
//        mPushAgent.setResourcePackageName("com.aglhz.s1");
//
//        //sdk开启通知声音
//        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
//
//        ALog.e(TAG, "UserHelper.account-->" + UserHelper.account);
////
//        mPushAgent.addAlias(UserHelper.account, "userType", new UTrack.ICallBack() {
//            @Override
//            public void onMessage(boolean b, String s) {
//                ALog.e(TAG, "addAlias-->" + b + "……" + s);
//            }
//        });
//
//        //注册推送服务 每次调用register都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                ALog.e(TAG, "deviceToken-->" + deviceToken);
//                HttpHelper.getService(ApiService.class)
//                        .requestRegisterUMeng(ApiService.requestRegisterUMeng, UserHelper.token, "and_" + deviceToken, UserHelper.account, "userType")
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(baseBean -> ALog.e(TAG, baseBean.getOther().getMessage()));
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                ALog.e(TAG, "register failed: " + s + " ---  " + s1);
//            }
//        });
//
//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
////            @Override
////            public void dealWithCustomMessage(final Context context, final UMessage msg) {
////                ALog.e(TAG, msg.getRaw().toString());//未来考虑把这个写入本地日志系统，当然要考虑异步形式。
////
////                new Handler().post(new Runnable() {
////                    @Override
////                    public void run() {
////
////                        // TODO Auto-generated method stub
////                        // 对自定义消息的处理方式，点击或者忽略
////                        boolean isClickOrDismissed = true;
////                        if (isClickOrDismissed) {
//////统计自定义消息的打开
////                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
////                        } else {
//////统计自定义消息的忽略
////                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
////                        }
////                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
////
////                    }
////                });
////            }
//
//            //自定义通知样式
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
//                //每当有通知送达时，均会回调getNotification方法，因此可以通过监听此方法来判断通知是否送达。
//                ALog.e(TAG, msg.getRaw().toString());
//                updateUI(msg.getRaw().toString());
//
//                switch (msg.builder_id) {
//                    //自定义通知样式编号
//                    case 1:
//                        ALog.e(TAG, msg.builder_id);
////                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
////                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
////                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
////                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
////                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
////                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
////                        builder.setContent(myNotificationView);
////                        builder.setAutoCancel(true);
////                        Notification mNotification = builder.build();
////                        //由于Android v4包的bug，在2.3及以下系统，Builder创建出来的Notification，并没有设置RemoteView，故需要添加此代码
////                        mNotification.contentView = myNotificationView;
////                        return mNotification;
//                    default:
//                        //默认为0，若填写的builder_id并不存在，也使用默认。
//                        return super.getNotification(context, msg);
//                }
//            }
//        };
//
//        mPushAgent.setMessageHandler(messageHandler);
//
//        /**
//         * 该Handler是在BroadcastReceiver中被调用，故
//         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
//         * 参考集成文档的1.6.2
//         * [url=http://dev.umeng.com/push/android/integration#1_6_2]http://dev.umeng.com/push/android/integration#1_6_2[/url]
//         * */
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//            //点击通知的自定义行为
//            @Override
//            public void dealWithCustomAction(Context context, UMessage msg) {
//                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                ALog.e(TAG, msg.getRaw().toString());//未来考虑把这个写入本地日志系统，当然要考虑异步形式。
//                ALog.e(TAG, msg.custom);
//            }
//        };
//
//        mPushAgent.setNotificationClickHandler(notificationClickHandler);
//
//    }

    /**
     * 依靠推送来刷新界面。
     *
     * @param msg 友盟推送的json。
     */
    private void updateUI(String msg) {
        NotificationBean notification = gson.fromJson(msg, NotificationBean.class);
        switch (notification.getExtra().getCtype()) {
            case Constants.SENSOR_LEARN:
                EventBus.getDefault().post(new EventLearnSensor());
            case Constants.GW_NOTIFIY_DEFENSE_ST:
                EventBus.getDefault().post(new EventRefreshSecurity());
                break;
            case Constants.DEVICE_LEARN:
                break;
            case Constants.GW_ALARM_GAS:
                break;
            case Constants.GW_ALARM_SOS:
                break;
            case Constants.ALARM_RED:
                break;
            case Constants.ALARM_DOOR:
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ALog.e("onActivityCreated");
//        PushAgent.getInstance(mContext).onAppStart();
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
}
