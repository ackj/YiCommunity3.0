package cn.itsite.order.model;

import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.DeliveryBean;
import cn.itsite.acommon.OperateBean;
import cn.itsite.order.contract.OrderService;
import cn.itsite.order.contract.SubmitOrderContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/2 0002 15:25
 */

public class SubmitOrderModel extends BaseModel implements SubmitOrderContract.Model {

    private static final String TAG = SubmitOrderModel.class.getSimpleName();

    @Override
    public Observable<BaseResponse<List<DeliveryBean>>> getAddress() {
        return HttpHelper.getService(OrderService.class)
                .getAddress()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse<List<OperateBean>>> postOrders(List<OperateBean> data) {
        BaseRequest request = new BaseRequest();
        request.message = "上传这几个订单";
        request.data = data;
        return HttpHelper.getService(OrderService.class)
                .postOrders(request)
                .subscribeOn(Schedulers.io());
    }
}
