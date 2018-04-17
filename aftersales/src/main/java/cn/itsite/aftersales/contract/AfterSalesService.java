package cn.itsite.aftersales.contract;

import cn.itsite.abase.network.http.BaseResponse;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/17 0017 17:34
 */

public interface AfterSalesService {

    @POST("v1/saleAfterApply")
    Observable<BaseResponse> postApply(@Body MultipartBody body);

}
