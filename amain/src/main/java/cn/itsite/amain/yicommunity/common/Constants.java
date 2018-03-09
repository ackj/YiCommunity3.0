package cn.itsite.amain.yicommunity.common;

import cn.itsite.abase.common.BaseConstants;
import cn.itsite.amain.BuildConfig;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class Constants extends BaseConstants {
    private final String TAG = Constants.class.getSimpleName();

    //——————————————以下是区分debug版和非debug版的baseurl——————————————————————
    public static String BASE_PROPERTY = "";
    public static String BASE_USER = "";
    public static String BASE_PROPERTYCFG_M = "";
    public static String WEB_SERVER = "";
    public static String UUID = "";

    static {
        if (BuildConfig.DEBUG) {
            //调试可以改这里的地址。
            WEB_SERVER = "http://member.planidea.cn"; //门禁机WebServer
            UUID = "b4ec659b-af86-4333-b753-98839a2d4dbb";//门禁机后台UUID
            BASE_USER = "http://www.aglhz.com:8076/memberSYS-m";           //用户
            BASE_PROPERTYCFG_M = "http://www.aglhz.com:8096/propertyCFG-m";//配置系统
            BASE_PROPERTY = "http://www.aglhz.com:8090/sub_property_ysq";   //物业

            //给成都电信测试。
//            WEB_SERVER = "http://mem.planidea.cn";
//            UUID = "5D7F8D33-2D37-4EBC-8E38-172E8AFD9136";
//            BASE_USER = "http://www.aglhz.com:8076/memberSYS-m";           //用户
//            BASE_PROPERTYCFG_M = "http://mem.planidea.cn:8096/propertyCFG-m";//配置系统
//            BASE_PROPERTY = "http://mem.planidea.cn:8090/sub_property_ysq";   //物业

        } else {
            //这里的是正式版的基础地址，永远不要动。
            WEB_SERVER = "http://member.planidea.cn"; //门禁机WebServer
            UUID = "b4ec659b-af86-4333-b753-98839a2d4dbb";//门禁机后台UUID
            BASE_USER = "http://www.aglhz.com:8076/memberSYS-m";           //用户
            BASE_PROPERTYCFG_M = "http://www.aglhz.com:8096/propertyCFG-m";//配置系统
            BASE_PROPERTY = "http://www.aglhz.com:8090/sub_property_ysq";   //物业

            //给成都电信测试。
//            WEB_SERVER = "http://mem.planidea.cn";
//            UUID = "5D7F8D33-2D37-4EBC-8E38-172E8AFD9136";
//            BASE_USER = "http://www.aglhz.com:8076/memberSYS-m";           //用户
//            BASE_PROPERTYCFG_M = "http://mem.planidea.cn:8096/propertyCFG-m";//配置系统
//            BASE_PROPERTY = "http://mem.planidea.cn:8090/sub_property_ysq";   //物业
        }
    }
    //——————————————以上是区分debug版和非debug版的baseurl——————————————————————

    public static final String PRESS_AGAIN = "再按一次退出";
    public static final int PAGE_SIZE = 20;
    public static final String TITLE = "title";

    //上门服务
    public static final String SERVICE_TYPE = "service_type";
    public static final String SERVICE_FID = "service_fid";
    public static final String COMMODITY_FID = "commodity_fid";
    public static final String FIRM_NAME = "firm_name";
    public static final String SERVICE_NAME = "service_name";
    public static final String SERVICE_ID = "service_id";

    //微信
    public static final String WX_APP_ID = "wx160fff7b6ed86ef7";

    //网络部分
    public static final int RESPONSE_CODE_SUCCESS = 200;
    public static final int RESPONSE_CODE_LOGOUT = 123;

    //停车模块
    public static final int MY_CARD = 0;
    public static final int CARPORT = 1;
    //报修模块
    public static final int CARD_TRANSACT = 2;
    public static final int PROPERTY_REPAIR = 0;

    //智能门禁模块
    public static final int COMPLAIN = 2;
    public static final int SET_OPEN_DOOR = 0;
    public static final int APPOINT_OPEN_DOOR = 1;
    public static final int PASSWORD_OPEN_DOOR = 2;
    public static final int CALL_DOOR = 3;
    public static final int OPEN_RECORD = 3;
    public static final String DOOR_DIR = "door_dir";

    //房屋模块
    public static final int ADD_HOUSE = 1;
    public static final int HOUSE_RIGHTS = 0;

    //**************以下是EventBus系列,值是随便取，只要不相同即可。*************************************
    //登录成功
    public static final int login = 11;

    //刷新“我的”模块里的消息中心未读红点标记
    public static final int refresh_unread_mark = 22;

    //刷新社区服务模块里的点评
    public static final int refresh_remark = 33;

    //**************以上是EventBus系列,值是随便取，只要不相同即可。*************************************

    public static final String CITY = "city";

    //********************以下为物业缴费模块************************
    public static final int TYPE_ALIPAY = 102;
    public static final int TYPE_WXPAY = 203;
    public static final String ALIPAY = "支付宝支付";
    public static final String WXPAY = "微信支付";

    //********************以上为物业缴费模块************************

    //******************** Bundle key ***********************
    public static final String KEY_TITLE = "title";
    public static final String KEY_LINK = "link";
    public static final String KEY_FID = "fid";
    public static final String KEY_ID = "id";
    public static final String KEY_BEAN = "bean";
    public static final String KEY_DES = "des";
    public static final String KEY_ORDER = "order";
    public static final String KEY_SHORTFROM = "shortfrom";
    public static final String KEY_CARNO = "carNo";
    public static final String KEY_TYPE = "type";
    public static final String KEY_CITY = "city";
    public static final String KEY_WHICH = "which";
    public static final String KEY_IS_PASS = "isPass";
    public static final String KEY_PAY_STATE = "pay_position";
    public static final String KEY_FROM_TO = "from_to";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_MEMBER = "member";
    public static final String KEY_ITEM = "item";

    //******************** SP key ***********************
    public static final String SP_KEY_USED_CITYS = "sp_key_used_citys";
    //\主界面
    public static final String SP_KEY_GUIDE_MAIN = "sp_key_guide_main";
    //月卡缴费
    public static final String SP_KEY_GUIDE_CARD_PAY = "sp_key_guide_card_pay";
    //我的车卡
    public static final String SP_KEY_GUIDE_MY_CARD = "sp_key_guide_my_card";
    //临停缴费
    public static final String SP_KEY_GUIDE_TEMPPORARY_PARK_PAY = "sp_key_guide_tempporary_park_pay";
    //欢迎页
    public static final String SP_KEY_WELCOME = "sp_key_welcome";


    //********************以下权限名称***********************
    public static final String PERMISSION_REMOTEWATCH = "RemoteWatch";

    //********************以上权限名称***********************

    public static final int TYPE_REMARK = 107;//社区服务点评回复

    //-----------------------扫描二维码跳转类型------------------

    public static final String TYPE_TEMPORARYPARKPAY = "temporaryParkPay";
    public static final String TYPE_SMARTDOOROPEN = "smartdoorOpen";
    public static final String TYPE = "type";
    public static final String PARAM_PARKPLACEFID = "parkPlaceFid";
    public static final String PARAM_PARKNAME = "parkName";
    public static final String PARAM_ACSSTOREDEVICEFID = "acsStoreDeviceFid";
    public static final String PARAM_ACCESSKEY = "accessKey";

    //历史记录最多缓存数。
    public static final int HISTORY_SIZE = 3;
    public static final String KEY_PARK = "key_park";
    public static final String KEY_PAR_KPAY_RESULT = "key_par_kpay_result";

    public static final String CARD_TYPE_MONTHLY = "月租卡";
    public static final String CARD_TYPE_CARPORT = "免费卡";

    //App更新
    public static final String APP_UPDATE_URL = "http://119.23.129.133:8066/appstore/client/info/checkVersion";
    public static final String SYS_ACCESS_PREFIX = "centro";
    public static final String SYS_ACCESS_KEY = "aglapps@0752";
    public static String SC = "AglhzYsq";
    public static String APP_TYPE = "1";//1为android。2为iOS。

}
