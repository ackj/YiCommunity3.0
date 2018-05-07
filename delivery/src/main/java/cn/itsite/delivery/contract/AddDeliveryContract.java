package cn.itsite.delivery.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.bean.DeliveryBean;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/15 0015 10:21
 */

public interface AddDeliveryContract {

    interface View extends BaseContract.View{
        void responsePostAddressSuccess(BaseResponse response);
        void responsePutAddressSuccess(BaseResponse response);
    }

    interface Presenter extends BaseContract.Presenter {
        void postAddress(DeliveryBean bean);
        void putAddress(DeliveryBean bean);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseResponse> postAddress(DeliveryBean bean);
        Observable<BaseResponse> putAddress( DeliveryBean bean);
    }
}
