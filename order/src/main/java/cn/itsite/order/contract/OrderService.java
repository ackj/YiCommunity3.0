package cn.itsite.order.contract;

import java.util.List;

import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.bean.DeliveryBean;
import cn.itsite.acommon.data.bean.OperateBean;
import cn.itsite.acommon.model.OrderDetailBean;
import cn.itsite.order.model.CategoryBean;
import cn.itsite.order.model.OrderBean;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
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

    //图片上传
    @POST("v1/saleAfterApplyPictures")
    Observable<BaseResponse<List<OperateBean>>> postPictures(@Body MultipartBody body);

    //提交商品评论
    @POST("v1/orderProductsEvaluate")
    Observable<BaseResponse> postEvaluate(@Body BaseRequest body);

    //钱包支付
    @POST
    Observable<BaseOldResponse> requestWalletPay(@Url String url, @Body BaseRequest request);

    String requestWalletPay = "http://www.aglhz.com/mall/ec/v1/payParam";

}
