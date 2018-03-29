package cn.itsite.goodshome.contract;

import java.util.List;

import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.goodshome.model.HomePojo;
import cn.itsite.goodshome.model.ShopBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/20 0020 10:10
 */

public interface HomeService {


    @GET("v1/home")
    Observable<BaseResponse<HomePojo>> getHome(@Query("params")String params);

    @GET("v1/shops")
    Observable<BaseResponse<List<ShopBean>>> getShops(@Query("params")String params);
}
