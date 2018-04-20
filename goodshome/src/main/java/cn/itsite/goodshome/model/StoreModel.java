package cn.itsite.goodshome.model;

import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.goodshome.contract.HomeService;
import cn.itsite.goodshome.contract.StoreContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/28 0028 15:25
 */

public class StoreModel extends BaseModel implements StoreContract.Model {

    @Override
    public Observable<BaseResponse<List<ShopBean>>> getStore(GoodsParams goodsParams) {
        return HttpHelper.getService(HomeService.class)
                .getShops(goodsParams.toString())
                .subscribeOn(Schedulers.io());
    }
}
