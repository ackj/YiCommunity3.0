package cn.itsite.goodshome.contract;

import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.goodshome.model.HomePojo;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/28 0028 11:33
 */

public interface StoreContract {

    @GET("v1/home")
    Observable<BaseResponse<HomePojo>> getHome(@Query("params")String params);


}
