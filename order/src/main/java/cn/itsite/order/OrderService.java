package cn.itsite.order;

import java.util.List;

import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.DeliveryBean;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/15 0015 11:56
 */

public interface OrderService {

    //获取订单分类字符串
    @GET("v1/categories")
    Observable<BaseResponse<List<CategoryBean>>> getCategories(@Query("params") String params);

    //获取订单列表
    @GET("v1/orders")
    Observable<BaseResponse<List<OrderBean>>> getOrder(@Query("params") String params);

    //获取订单详情
    @GET("v1/orders/{uid}")
    Observable<BaseResponse<OrderDetailBean>> getOrderDetail(@Query("params") String params);

    //获取地址列表
    @GET("v1/deliveries")
    Observable<BaseResponse<List<DeliveryBean>>> getAddress();

    //新增订单
    @POST("v1/orders")
    Observable<BaseResponse> postOrders(@Body BaseRequest request);

    //删除订单
    @HTTP(method = "delete", path = "v1/orders", hasBody = true)
    Observable<BaseResponse> deleteOrders(@Body BaseRequest request);

    //修改订单
    @PUT("v1/orders")
    Observable<BaseResponse> putOrders(@Body BaseRequest request);
}
