package cn.itsite.delivery.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.delivery.contract.DeliveryService;
import cn.itsite.delivery.contract.AddDeliveryContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/15 0015 10:24
 */

public class AddDeliveryModel extends BaseModel implements AddDeliveryContract.Model {

    @Override
    public Observable<BaseResponse> postAddress(DeliveryBean bean) {
        List<DeliveryBean> list = new ArrayList<>();
        list.add(bean);
        BaseRequest<List<DeliveryBean>> request = new BaseRequest();
        request.data = list;
        request.message = "修改这几个递送";
        return HttpHelper.getService(DeliveryService.class)
                .postAddress(new Gson().toJson(request))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse> putAddress(DeliveryBean bean) {
        List<DeliveryBean> list = new ArrayList<>();
        list.add(bean);
        BaseRequest<List<DeliveryBean>> request = new BaseRequest();
        request.data = list;
        request.message = "修改这几个递送";
        return HttpHelper.getService(DeliveryService.class)
                .putAddress(new Gson().toJson(request))
                .subscribeOn(Schedulers.io());
    }
}
