package cn.itsite.delivery.model;

import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.DeliveryBean;
import cn.itsite.delivery.contract.DeliveryService;
import cn.itsite.delivery.contract.DeliveryContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/14 0014 15:45
 */

public class DeliveryModel extends BaseModel implements DeliveryContract.Model{

    @Override
    public Observable<BaseResponse<List<DeliveryBean>>> getAddress() {
        return HttpHelper.getService(DeliveryService.class)
                .getAddress()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse> deleteAddress(String uid) {
        return HttpHelper.getService(DeliveryService.class)
                .deleteAddress(uid)
                .subscribeOn(Schedulers.io());
    }
}
