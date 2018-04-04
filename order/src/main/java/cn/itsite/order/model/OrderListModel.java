package cn.itsite.order.model;

import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.acommon.OperateBean;
import cn.itsite.order.contract.OrderService;
import cn.itsite.order.contract.OrderListContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/19 0019 16:42
 */

public class OrderListModel extends BaseModel implements OrderListContract.Model {
    @Override
    public Observable<BaseResponse<List<OrderBean>>> getOrders(GoodsParams goodsParams) {
        return HttpHelper.getService(OrderService.class)
                .getOrder(goodsParams.toString())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse> deleteOrders(List<OperateBean> orders) {
        BaseRequest request = new BaseRequest();
        request.message = "居然被你删除成功了";
        request.data = orders;
        return HttpHelper.getService(OrderService.class)
                .deleteOrders(request)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse> putOrders(List<OperateBean> orders) {
        BaseRequest request = new BaseRequest();
        request.message = "居然被你修改成功了";
        request.data = orders;
        return HttpHelper.getService(OrderService.class)
                .putOrders(request)
                .subscribeOn(Schedulers.io());
    }
}
