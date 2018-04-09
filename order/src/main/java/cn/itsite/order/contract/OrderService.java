package cn.itsite.order.contract;

import java.util.List;

import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.DeliveryBean;
import cn.itsite.acommon.OperateBean;
import cn.itsite.order.model.CategoryBean;
import cn.itsite.order.model.OrderBean;
import cn.itsite.order.model.OrderDetailBean;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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
    Observable<BaseResponse<OrderDetailBean>> getOrderDetail(@Path("uid") String uid);

    //获取地址列表
    @GET("v1/deliveries")
    Observable<BaseResponse<List<DeliveryBean>>> getAddress();

    //新增订单
    @POST("v1/orders")
    Observable<BaseResponse<List<OperateBean>>> postOrders(@Body BaseRequest request);

    //删除订单
    @HTTP(method = "DELETE", path = "v1/orders", hasBody = true)
    Observable<BaseResponse> deleteOrders(@Body BaseRequest request);

    //修改订单
    @PUT("v1/orders")
    Observable<BaseResponse> putOrders(@Body BaseRequest request);

    //检查订单状态
    @GET("v1/checkPayOrderState/{orderUid}")
    Observable<BaseResponse<OperateBean>> checkOrderStatus(@Path("orderUid")String orderUid);
}
