package cn.itsite.order.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.order.contract.OrderService;
import cn.itsite.order.contract.OrderDetailContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/20 0020 16:46
 */

public class OrderDetailModel extends BaseModel implements OrderDetailContract.Model {

    @Override
    public Observable<BaseResponse<OrderDetailBean>> getOrderDetail(GoodsParams goodsParams) {
        return HttpHelper.getService(OrderService.class)
                .getOrderDetail(goodsParams.toString())
                .subscribeOn(Schedulers.io());
    }

}
