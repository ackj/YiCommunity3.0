package cn.itsite.amain.s1.common;

import java.io.File;

import cn.itsite.amain.s1.entity.bean.AuthorizationBean;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.CameraBean;
import cn.itsite.amain.s1.entity.bean.CheckTokenBean;
import cn.itsite.amain.s1.entity.bean.DeviceListBean;
import cn.itsite.amain.s1.entity.bean.DeviceLogBean;
import cn.itsite.amain.s1.entity.bean.DevicesBean;
import cn.itsite.amain.s1.entity.bean.EquipmentBean;
import cn.itsite.amain.s1.entity.bean.GatewaysBean;
import cn.itsite.amain.s1.entity.bean.HostSettingsBean;
import cn.itsite.amain.s1.entity.bean.LinkageBean;
import cn.itsite.amain.s1.entity.bean.RoomTypesBean;
import cn.itsite.amain.s1.entity.bean.RoomsBean;
import cn.itsite.amain.s1.entity.bean.SceneBean;
import cn.itsite.amain.s1.entity.bean.SecurityBean;
import cn.itsite.amain.s1.entity.bean.SubDeviceDetBean;
import cn.itsite.amain.s1.entity.bean.UserBean;
import cn.itsite.amain.yicommunity.entity.bean.MainDeviceListBean;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by leguang on 2017/7/4 0022.
 * Email：langmanleguang@qq.com
 */

public interface ApiService {

    String BASE_USER = Constants.BASE_USER;           //用户

    String BASE_URL = Constants.BASE_URL;

    //*************以上基础路径*******************

    //-----------------以下为推送相关---------------------
    //友盟用户登记接口
    String requestRegisterUMeng = BASE_USER + "/client/logUMengParams.do";

    @POST
    Observable<BaseBean> requestRegisterUMeng(@Url String url,
                                              @Query("token") String token,
                                              @Query("deviceToken") String deviceToken,
                                              @Query("alias") String alias,
                                              @Query("aliasType") String aliasType);
    //-----------------以上为推送相关---------------------


    //----------以下为Launch模块--------------
    //登录
    String requestLogin = BASE_USER + "/client/login.do";

    @POST
    Observable<UserBean> requestLogin(@Url String url,
                                      @Query("sc") String sc,
                                      @Query("user") String user,
                                      @Query("pwd") String pwd);

    //注册
    String requestRegister = BASE_USER + "/client/register.do";

    @POST
    Observable<BaseBean> requestRegister(@Url String url,
                                         @Query("sc") String sc,
                                         @Query("account") String account,
                                         @Query("code") String code,
                                         @Query("Password1") String password1,
                                         @Query("Password2") String password2);

    //获取验证码
    String requestVerifyCode = BASE_USER + "/client/validCode.do";

    @POST
    Observable<BaseBean> requestVerifyCode(@Url String url,
                                           @Query("sc") String sc,
                                           @Query("phone") String phone,
                                           @Query("type") String type);

    //重置密码
    String requestResetPassword = BASE_USER + "/client/renewMemberPwd.do";

    @POST
    Observable<BaseBean> requestResetPassword(@Url String url,
                                              @Query("sc") String sc,
                                              @Query("account") String account,
                                              @Query("code") String code,
                                              @Query("pwd1") String password1,
                                              @Query("pwd2") String password2);

    //登录验证
    String requestCheckToken = BASE_USER + "/client/checkIfTokenInvalid.do";

    @POST
    Observable<CheckTokenBean> requestCheckToken(@Url String url,
                                                 @Query("token") String token);

    //登出
    String requestLogout = BASE_USER + "/client/logout.do";

    @POST
    Observable<BaseBean> requestLogout(@Url String url,
                                       @Query("fc") String fc,
                                       @Query("token") String token);

    //----------以上为Launch模块--------------

    //----------------------------- 以下为主机操作相关 ---------------------------------
    //获取主机列表
    String requestGateways = BASE_URL + "/client/info/gatewayList";

    @FormUrlEncoded
    @POST
    Observable<GatewaysBean> requestGateways(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("page") int page
            , @Field("pageSize") int pageSize
            , @Field("deviceSn") String deviceSn
    );

    //-----------------------------探测器 相关 ---------------------------------
    //获取探测器列表
    String requestSensorTypeList = BASE_URL + "/client/info/sensorTypeList";

    @FormUrlEncoded
    @POST
    Observable<DevicesBean> requestSensorTypeList(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("page") int page
            , @Field("pageSize") int pageSize
    );

    //添加探测器
    String reqeuestNewsensor = BASE_URL + "/ctrl/client/newsensor";


    @POST
    Observable<BaseBean> reqeuestNewsensor(@Url String url
            , @Body MultipartBody file);

    //取消传感器的学习。
    String reqeuestCancellationOfSensorLearning = BASE_URL + "/ctrl/client/sensorLearnCancle";

    @POST
    Observable<BaseBean> reqeuestCancellationOfSensorLearning(@Url String url
            , @Query("token") String token
            , @Query("fc") String fc
            , @Query("gateway") String deviceSn
    );

    // 修改探测器
    String requestModsensor = BASE_URL + "/ctrl/client/modsensor";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestModsensor(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("file") File file
            , @Field("index") int index
            , @Field("name") String name
            , @Field("defenseLevel") String defenseLevel
            , @Field("alarmDelay") int alarmDelay
            , @Field("gateway") String deviceSn);

    @POST
    Observable<BaseBean> requestModsensor(@Url String url
            , @Query("token") String token
            , @Body MultipartBody file
    );

    // 删除探测器
    String requestDelsensor = BASE_URL + "/ctrl/client/delsensor";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestDelsensor(@Url String url,
                                          @Field("token") String token,
                                          @Field("fc") String fc,
                                          @Field("index") int index,
                                          @Field("gateway") String deviceSn);

    //获取探测器/设备详情
    String requestSubDeviceDet = BASE_URL + "/client/info/subDeviceDet";

    @FormUrlEncoded
    @POST
    Observable<SubDeviceDetBean> requestSubDeviceDet(@Url String url,
                                                     @Field("token") String token,
                                                     @Field("fc") String fc,
                                                     @Field("category") String category,
                                                     @Field("index") int index,
                                                     @Field("gateway") String deviceSn);

    //----------------------------- 设备相关 ---------------------------------
    //添加设备
    String requestNewDevice = BASE_URL + "/ctrl/client/newdevice";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestNewDevice(@Url String url,
                                          @Field("token") String token,
                                          @Field("fc") String fc,
                                          @Field("deviceType") String deviceType,
                                          @Field("name") String name,
                                          @Field("roomFid") String roomFid,
                                          @Field("gateway") String deviceSn);

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestNewCamera(@Url String url,
                                          @Field("token") String token,
                                          @Field("fc") String fc,
                                          @Field("deviceType") String deviceType,
                                          @Field("name") String name,
                                          @Field("roomFid") String roomFid,
                                          @Field("deviceId") String deviceId,
                                          @Field("password") String password);

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestNewCamera(@Url String url,
                                          @Field("token") String token,
                                          @Field("type") String type,
                                          @Field("serialNO") String serialNO,
                                          @Field("name") String name,
                                          @Field("password") String password,
                                          @Field("roomDir") String roomDir);

    //修改设备
    String requestModDevice = BASE_URL + "/ctrl/client/moddevice";

    @POST
    Observable<BaseBean> requestModDevice(@Url String url
            , @Query("token") String token
            , @Body MultipartBody file);

    //删除设备
    String requestDelDevice = BASE_URL + "/ctrl/client/deldevice";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestDelDevice(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("index") int index
            , @Field("gateway") String deviceSn);

    String requestSubDeviceList = BASE_URL + "/client/info/subDeviceList";

    //设备列表
    @FormUrlEncoded
    @POST
    Observable<DeviceListBean> requestSubDeviceList(@Url String url,
                                                    @Field("token") String token,
                                                    @Field("fc") String fc,
                                                    @Field("page") int page,
                                                    @Field("pageSize") int pageSize,
                                                    @Field("roomId") int roomId,
                                                    @Field("category") String category,
                                                    @Field("gateway") String deviceSn);

    //设备列表
    @FormUrlEncoded
    @POST
    Observable<DeviceListBean> requestSubDeviceList(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("page") int page
            , @Field("pageSize") int pageSize
            , @Field("category") String category
            , @Field("gateway") String deviceSn
    );

    //设备控制
    String requestDevicectrl = BASE_URL + "/ctrl/client/devicectrl";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestDevicectrl(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("index") int index
            , @Field("nodeId") String nodeId
            , @Field("status") int status
            , @Field("gateway") String deviceSn);

    //设备消息记录
    String requestDeviceLogs = BASE_URL + "/client/info/deviceLogs";

    @FormUrlEncoded
    @POST
    Observable<DeviceLogBean> requestDeviceLogs(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("page") int page
            , @Field("pageSize") int pageSize
            , @Field("gateway") String deviceSn
    );

    //设备类型
    String requestCtrlSDeviceTypeList = BASE_URL + "/client/info/ctrlSDeviceTypeList";

    @FormUrlEncoded
    @POST
    Observable<DevicesBean> requestCtrlSDeviceTypeList(@Url String url
            , @Field("fc") String fc);

    //确认添加433控制类设备(非探测器类)，是否成功
    String requestNewDeviceConfirm = BASE_URL + "/ctrl/client/newDeviceConfirm";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestNewDeviceConfirm(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("status") int status
            , @Field("gateway") String deviceSn);

    String requestNewDevice24 = BASE_URL + "/ctrl/client/newdevice24";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestNewDevice24(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("rommId") int rommId
            , @Field("gateway") String deviceSn);


    //----------------------------- 房间相关 ---------------------------------
    //获取房间列表
    String requestRoomList = BASE_URL + "/client/info/roomList";

    @FormUrlEncoded
    @POST
    Observable<RoomsBean> requestRoomList(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("page") int page
            , @Field("pageSize") int pageSize
            , @Field("gateway") String deviceSn
    );

    //添加房间
    String requestNewroom = BASE_URL + "/ctrl/client/newroom";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestNewroom(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("name") String roomName
            , @Field("roomTypeFid") String roomTypeFid
            , @Field("gateway") String deviceSn
    );

    //房间类型列表
    String requestRoomTypeList = BASE_URL + "/client/info/roomTypeList";

    @FormUrlEncoded
    @POST
    Observable<RoomTypesBean> requestRoomTypeList(@Url String url
            , @Field("fc") String fc
    );

    //删除房间
    String requestDelroom = BASE_URL + "/ctrl/client/delroom";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestDelroom(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("fid") String fid
    );

    //----------------------------- 联动相关 ---------------------------------
    //添加联动
    String requestNewlinkage = BASE_URL + "/ctrl/client/newlinkage";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestNewlinkage(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("name") String roomName
            , @Field("triggerType") String triggerType
            , @Field("cdt_sensorId") int cdt_sensorId
            , @Field("cdt_sensorAct") String cdt_sensorAct
            , @Field("cdt_day") String cdt_day
            , @Field("cdt_time") String cdt_time
            , @Field("targetType") String targetType
            , @Field("targetId") String targetId
            , @Field("nodeId") String nodeId
            , @Field("act1") String act1
            , @Field("delay") String delay
            , @Field("act2") String act2
            , @Field("gateway") String deviceSn
    );

    //修改联动
    String requestModLinkage = BASE_URL + "/ctrl/client/modlinkage";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestModLinkage(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("index") int index
            , @Field("name") String name
            , @Field("triggerType") String triggerType
            , @Field("cdt_sensorId") int cdt_sensorId
            , @Field("cdt_sensorAct") String cdt_sensorAct
            , @Field("cdt_day") String cdt_day
            , @Field("cdt_time") String cdt_time
            , @Field("targetType") String targetType
            , @Field("targetId") String targetId
            , @Field("nodeId") String nodeId
            , @Field("act1") String act1
            , @Field("delay") String delay
            , @Field("act2") String act2
            , @Field("status") int status
            , @Field("gateway") String deviceSn);


    //联动列表
    String requestLinkageList = BASE_URL + "/client/info/linkageList";

    @FormUrlEncoded
    @POST
    Observable<LinkageBean> requestLinkageList(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("page") int page
            , @Field("pageSize") int pageSize
            , @Field("gateway") String deviceSn
    );

    //删除联动
    String requestDellinkage = BASE_URL + "/ctrl/client/dellinkage";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestDellinkage(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("index") int index
            , @Field("gateway") String deviceSn
    );
    //----------------------------- 授权相关 ---------------------------------

    //会员主机授权列表
    String requestGatewayAuthList = BASE_URL + "/client/info/gatewayAuthList";

    @FormUrlEncoded
    @POST
    Observable<AuthorizationBean> requestGatewayAuthList(@Url String url,
                                                         @Field("token") String token,
                                                         @Field("fc") String fc,
                                                         @Field("gateway") String gateway,
                                                         @Field("page") int page,
                                                         @Field("pageSize") int pageSize);

    //解除主机授权
    String requestGatewayUnAuth = BASE_URL + "/client/gatewayUnAuth";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestGatewayUnAuth(@Url String url,
                                              @Field("token") String token,
                                              @Field("fc") String fc,
                                              @Field("gateway") String gateway,
                                              @Field("fid") String fid);

    //选择一个主机，授权给用户
    String requestGatewayAuth = BASE_URL + "/client/gatewayAuth";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestGatewayAuth(@Url String url
            , @Field("token") String token
            , @Field("fc") String fc
            , @Field("gateway") String gateway
            , @Field("mobile") String mobile);

    //解绑当前主机
    String requestUnbindHost = BASE_URL + "/client/delgateway";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestUnbindHost(@Url String url
            , @Field("token") String token
            , @Field("gateway") String gateway);

    //----------------------------- 关于 ---------------------------------
    String requestFeedback = BASE_URL + "/client/feedback";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestFeedback(@Url String url,
                                         @Field("token") String token,
                                         @Field("fc") String fc,
                                         @Field("des") String des,
                                         @Field("contact") String contact
    );

    //-----------------以下为主机操作相关---------------------
    //切换主机
    String requestSwichGateway = BASE_URL + "/client/gatewaySW";

    @POST
    Observable<BaseBean> requestSwichGateway(@Url String url,
                                             @Query("token") String token,
                                             @Query("fc") String fc,
                                             @Query("gateway") String gateway);

    //切换主机状态
    String requestSwichState = BASE_URL + "/ctrl/client/gatewayDSS";

    @POST
    Observable<BaseBean> requestSwichState(@Url String url,
                                           @Query("token") String token,
                                           @Query("fc") String fc,
                                           @Query("dstatus") String dstatus,
                                           @Query("gateway") String deviceSn);

    //主页显示内容
    String requestSecurity = BASE_URL + "/client/info/homePage ";

    @POST
    Observable<SecurityBean> requestSecurity(@Url String url,
                                             @Query("token") String token,
                                             @Query("fc") String fc,
                                             @Query("gateway") String deviceSn);

    //添加主机
    String requestAddHost = BASE_URL + "/ctrl/client/newgateway";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestAddHost(@Url String url,
                                        @Field("token") String token,
                                        @Field("gateway") String gateway,
                                        @Field("name") String name,
                                        @Field("roomDir") String roomDir,
                                        @Field("addr") String addr,
                                        @Field("addrDet") String addrDet,
                                        @Field("lng") String lng,
                                        @Field("lat") String lat);

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestAddHost(@Url String url,
                                        @Field("token") String token,
                                        @Field("fc") String fc,
                                        @Field("gateway") String gateway,
                                        @Field("name") String name,
                                        @Field("roomDir") String roomDir);


    //修改主机定位
    String requestEditHostLocation = BASE_URL + "/ctrl/client/gatewayAddrSet";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestEditHostLocation(@Url String url,
                                                 @Field("token") String token,
                                                 @Field("addr") String addr,
                                                 @Field("addrDet") String addrDet,
                                                 @Field("lng") String lng,
                                                 @Field("lat") String lat,
                                                 @Field("gateway") String gateway);

    //主机配置都归为这一个借口。如：音量，短信，推送等。
    String requestHostConfig = BASE_URL + "/ctrl/client/gatewayConfig";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestHostConfig(@Url String url,
                                           @Field("gateway") String gateway,
                                           @Field("token") String token,
                                           @Field("fc") String fc,
                                           @Field("type") String type,
                                           @Field("subType") String subType,
                                           @Field("val") String val);


    //获取主机设置信息。
    String requestHostSettings = BASE_URL + "/client/info/gatewayConfigInfo";

    @FormUrlEncoded
    @POST
    Observable<HostSettingsBean> requestHostSettings(@Url String url,
                                                     @Field("gateway") String gateway,
                                                     @Field("token") String token,
                                                     @Field("fc") String fc,
                                                     @Field("type") String type);


    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestUpdateHostName(@Url String url,
                                               @Field("token") String token,
                                               @Field("gateway") String gateway,
                                               @Field("name") String name);

    //主机留言。
    String requestLeaveMassge = BASE_URL + "/ctrl/client/gatewayMsgLeave";

    @POST
    Observable<BaseBean> requestLeaveMassge(@Url String url,
                                            @Query("token") String token,
                                            @Query("fc") String fc,
                                            @Body MultipartBody file);

    //----------------------------- 以上为主机操作相关 ---------------------------------

    //----------------------------- 以下为场景 ----------------------------------

    //场景列表
    String requestSceneList = BASE_URL + "/client/info/sceneList";

    @FormUrlEncoded
    @POST
    Observable<SceneBean> requestSceneList(@Url String url,
                                           @Field("pageSize") int pageSize,
                                           @Field("page") int page,
                                           @Field("token") String token,
                                           @Field("fc") String fc,
                                           @Field("gateway") String deviceSn);

    //启动场景
    String requestStartScene = BASE_URL + "/ctrl/client/scenectrl";

    @POST
    Observable<BaseBean> requestStartScene(@Url String url,
                                           @Query("token") String token,
                                           @Query("fc") String fc,
                                           @Query("index") int index,
                                           @Query("gateway") String deviceSn);

    //删除场景
    String requestDeleteScene = BASE_URL + "/ctrl/client/delscene";

    @POST
    Observable<BaseBean> requestDeleteScene(@Url String url,
                                            @Query("token") String token,
                                            @Query("fc") String fc,
                                            @Query("index") int index,
                                            @Query("gateway") String deviceSn);


    // 添加场景
    String requestAddScene = BASE_URL + "/ctrl/client/newscene";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestAddScene(@Url String url,
                                         @Field("token") String token,
                                         @Field("fc") String fc,
                                         @Field("name") String name,
                                         @Field("paramJson") String paramJson,
                                         @Field("gateway") String deviceSn);

    //----------------------------- 以上为场景 ----------------------------------


    //********************以下为更新App接口*******************************
    String requestAppUpdatae = BASE_URL + "/client/info/checkVersion";

    //********************以上为更新App接口*******************************


    //----------------------------- 摄像头相关 ----------------------------------

    //获取摄像头列表
    String requestCameraList = BASE_URL + "/client/info/cameraList";

    @FormUrlEncoded
    @POST
    Observable<CameraBean> requestCameraList(@Url String url,
                                             @Field("token") String token,
                                             @Field("fc") String fc,
                                             @Field("page") int page,
                                             @Field("pageSize") int pageSize);

    //删除摄像头
//    String requestDelcamera = BASE_URL + "/ctrl/client/delcamera";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestDelcamera(@Url String url,
                                          @Field("token") String token,
                                          @Field("fc") String fc,
                                          @Field("camera") String camera);

    //修改摄像头信息
    String requestModCamera = BASE_URL + "/ctrl/client/modcamera";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestModCamera(@Url String url,
                                          @Field("token") String token,
                                          @Field("fc") String fc,
                                          @Field("camera") String camera,
                                          @Field("type") String type,
                                          @Field("name") String name,
                                          @Field("password") String password);

    //添加摄像头
    String requestNewcamera = BASE_URL + "/ctrl/client/newcamera";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestNewcamera(@Url String url,
                                          @Field("token") String token,
                                          @Field("fc") String fc,
                                          @Field("deviceId") String deviceId,
                                          @Field("deviceName") String deviceName,
                                          @Field("devicePassword") String devicePassword);

    //todo(高亮)sssssssssssssssssssssssss 拼接亿社区 ssssssssssssssssssssssssssssss

    String requestEquipmentInfoList = cn.itsite.amain.yicommunity.common.Constants.BASE_PROPERTY + "/smartequipment/info/get-equipmentInfo-list";

    @FormUrlEncoded
    @POST
    Observable<EquipmentBean> requestEquipmentInfoList(@Url String url,
                                                       @Field("token") String token,
                                                       @Field("fc") String fc,
                                                       @Field("roomDir") String roomDir,
                                                       @Field("powerCode") String powerCode);

//    String requestUnBindEquipment = com.aglhz.yicommunity.common.Constants.BASE_PROPERTY + "/smartequipment/client/un-bind-equipment";
//    @FormUrlEncoded
//    @POST
//    Observable<BaseBean> requestUnBindEquipment(@Url String url,
//                                                @Field("token") String token,
//                                                @Field("deviceSn") String deviceSn);

    String requestDelGateway = BASE_URL + "/ctrl/client/delgateway";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestDelGateway(@Url String url,
                                           @Field("token") String token,
                                           @Field("fc") String fc,
                                           @Field("gateway") String deviceSn);

    String requestModGateway = BASE_URL + "/ctrl/client/modgateway";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestModGateway(@Url String url,
                                           @Field("token") String token,
                                           @Field("fc") String fc,
                                           @Field("gateway") String deviceSn,
                                           @Field("name") String name);

    String requestGatewayTest = BASE_URL + "/ctrl/client/gatewayTest";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestGatewayTest(@Url String url,
                                            @Field("token") String token,
                                            @Field("fc") String fc,
                                            @Field("gateway") String deviceSn,
                                            @Field("status") int status);

    //(todo)------------------------------- 2018.1.5增加 ---------------------------------

    String requestMainDeviceList = BASE_URL + "/client/info/mainDeviceList";

    @FormUrlEncoded
    @POST
    Observable<MainDeviceListBean> requestMainDeviceList(@Url String url,
                                                         @Field("token") String token,
                                                         @Field("type") String type,
                                                         @Field("cmntDir") String cmntDir,
                                                         @Field("roomDir") String roomDir,
                                                         @Field("page") int page,
                                                         @Field("pageSize") int pageSize);

//    @FormUrlEncoded
//    @POST
//    Observable<MainDeviceListBean> delcamera (@Url String url,
//                                                         @Field("token") String token,
//                                                         @Field("type") String type,
//                                                         @Field("roomDir") String roomDir,
//                                                         @Field("page") int page,
//                                                         @Field("pageSize") int pageSize);

    String requestNewMainDevice = BASE_URL + "/ctrl/client/newMainDevice";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestNewMainDevice(@Url String url,
                                              @Field("token") String token,
                                              @Field("type") String type,
                                              @Field("serialNO") String serialNO,
                                              @Field("name") String name,
                                              @Field("roomDir") String roomDir,
                                              @Field("houseInfo") String houseInfo);

    String requestModMainDevice = BASE_URL + "/ctrl/client/modMainDevice";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestModMainDevice(@Url String url,
                                              @Field("token") String token,
                                              @Field("type") String type,
                                              @Field("serialNO") String serialNO,
                                              @Field("name") String name,
                                              @Field("password") String password);

    String requestDelMainDevice = BASE_URL + "/ctrl/client/delMainDevice";

    @FormUrlEncoded
    @POST
    Observable<BaseBean> requestDelMainDevice(@Url String url,
                                              @Field("token") String token,
                                              @Field("type") String type,
                                              @Field("serialNO") String serialNO);

}
