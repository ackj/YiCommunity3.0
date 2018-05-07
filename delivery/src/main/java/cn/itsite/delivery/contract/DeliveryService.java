package cn.itsite.delivery.contract;

import java.util.List;

import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.bean.DeliveryBean;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/14 0014 15:53
 */

public interface DeliveryService {
    //获取地址列表
    @GET("v1/deliveries")
    Observable<BaseResponse<List<DeliveryBean>>> getAddress();

    //添加一个地址
    @FormUrlEncoded
    @POST("v1/deliveries")
    Observable<BaseResponse> postAddress(@Field("params") String params);

    //修改一个地址
    @PUT("v1/deliveries")
    Observable<BaseResponse> putAddress(@Body BaseRequest params);

    //删除一个地址
    @DELETE("v1/deliveries/{uid}")
    Observable<BaseResponse> deleteAddress(@Path("uid") String uid);

}
