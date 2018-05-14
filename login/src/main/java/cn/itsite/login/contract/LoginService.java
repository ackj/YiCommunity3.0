package cn.itsite.login.contract;

import cn.itsite.abase.common.BaseBean;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.login.model.CheckTokenBean;
import cn.itsite.login.model.PushEnableBean;
import cn.itsite.login.model.UserInfoBean;
import cn.itsite.login.model.VerifyCodeBean;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by liujia on 03/05/2018.
 */

public interface LoginService {
    //----------------------- 延用老版宅易购的接口 ------------------------
    String TYPE_REGISTER = "v_regPhone";
    String TYPE_RESET_PWD = "v_rePwd";
    String TYPE_BOND_PHONE = "v_bondPhone";

    String BASE_URL = "http://www.aglhz.com/";

    String BASE_USER = "http://www.aglhz.com:8076/memberSYS-m/";

    /**
     * 获取验证码
     *
     * @param phone 电话
     * @param type  v_rePwd，账号重置密码；v_bondPhone，账号绑定手机；v_regPhone，手机注册会员；
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<BaseOldResponse<VerifyCodeBean>> requestVerifyCode(@Url String url, @Field("phone") String phone, @Field("type") String type);

    String requestVerifyCode = BASE_URL + "mall/client/validCode.do";

    /**
     * 注册会员
     *
     * @param account   账号
     * @param code      验证码
     * @param password1 密码
     * @param password2 确认密码
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<BaseOldResponse> requestRegister(@Url String url, @Field("account") String account, @Field("code") String code, @Field("Password1") String password1, @Field("Password2") String password2);

    String requestRegister = BASE_URL + "mall/client/register.do";

    /**
     * 重置密码
     *
     * @param account   账号
     * @param code      验证码
     * @param password1 密码
     * @param password2 确认密码
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<BaseOldResponse> requestResetPwd(@Url String url, @Field("account") String account, @Field("code") String code, @Field("Password1") String password1, @Field("Password2") String password2);

    String requestResetPwd = BASE_URL + "mall/client/reNewPWD.do";

    /**
     * 登录账号
     *
     * @param username
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<BaseOldResponse<UserInfoBean>> requestLogin(@Url String url,@Field("fc")String fc, @Field("user") String username, @Field("pwd") String pwd);

    String requestLogin = BASE_URL + "mall/client/login.do";

    /**
     * 用户注销
     *
     * @return
     */
    @POST
    Observable<BaseOldResponse> requestLogout(@Url String url, @Query("token") String token);

    String requestLogout = BASE_URL + "mall/client/logout.do";

    /**
     * 上传头像
     *
     * @return
     */
    @POST
    Observable<BaseOldResponse<String>> requestUploadAvator(@Url String url,
                                                            @Query("token") String token,
                                                            @Body MultipartBody file);

    String requestUploadAvator = BASE_URL + "mall/member/center/appUploadImageHeader.do";

    /**
     * 更新昵称
     *
     * @param nickName 昵称
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<BaseOldResponse> requestUpdateNickname(@Url String url, @Field("nickName") String nickName);

    String requestUpdateNickname = BASE_URL + "mall/member/center/updateNickName.do";


    /**
     * 修改用户信息
     */
    @POST
    Observable<BaseOldResponse> requsetUpdateUserData(@Url String url, @Body MultipartBody file);

    String requsetUpdateUserData = BASE_USER + "client/updateMemberFieldByToken.do";


    /**
     * 阿里云deviceID登记接口
     *
     * @param url
     * @param token
     * @param deviceToken
     * @param alias
     * @param aliasType
     * @return
     */
    @POST
    Observable<BaseBean> registerDevice(@Url String url,
                                        @Query("token") String token,
                                        @Query("deviceToken") String deviceToken,
                                        @Query("alias") String alias,
                                        @Query("aliasType") String aliasType);
    //http://www.aglhz.com:8076/memberSYS-m/client/logUMengParams.do
    String registerDevice = BASE_USER + "client/logUMengParams.do";

    /**
     * 会员推送配置信息
     *
     * @param url
     * @param token
     * @param pushEnable
     * @return
     */
    @POST
    Observable<BaseOldResponse> requestPushConfig(@Url String url,
                                                  @Query("token") String token,
                                                  @Query("pushEnable") boolean pushEnable);

    String requestPushConfig = BASE_URL + "mall/member/center/memberConfig.do";


    /**
     * 会员推送配置信息
     *
     * @param url
     * @param token
     * @return
     */
    @GET
    Observable<BaseOldResponse<PushEnableBean>> requsetMemberConfigInfo(@Url String url, @Query("token") String token);

    String requsetMemberConfigInfo = BASE_URL + "mall/member/center/memberConfigInfo.do";

    /**
     * 反馈
     * @param url
     * @param content 内容
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<BaseOldResponse> requestFeedback(@Url String url, @Field("content") String content);

    String requestFeedback = BASE_URL + "mall/member/center/sendFeedbackMessage.do";

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @POST
    Observable<BaseOldResponse<UserInfoBean.MemberInfoBean>> requestInfo(@Url String url,@Query("token") String token);
    String requestInfo = BASE_URL+"mall/member/center/info.do";

    //登录验证
    String requestCheckToken = BASE_URL + "mall/client/loginCheck.do";
    @POST
    Observable<CheckTokenBean> requestCheckToken(@Url String url, @Query("token") String token);

}
