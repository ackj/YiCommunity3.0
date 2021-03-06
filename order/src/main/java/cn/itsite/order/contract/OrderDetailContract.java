package cn.itsite.order.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.bean.OperateBean;
import cn.itsite.acommon.model.OrderDetailBean;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/20 0020 16:43
 */

public interface OrderDetailContract {

    interface View extends BaseContract.View {
        void responseOrderDetail(OrderDetailBean orderDetailBean);

        void responseDeleteSuccess(BaseResponse response);

        void responsePutSuccess(BaseResponse response);

        void responseCheckOrderStatus(int status);

        void responseWalletPay(BaseOldResponse response);

    }

    interface Presenter extends BaseContract.Presenter {
        void getOrderDetail(String uid);

        void deleteOrders(List<OperateBean> orders);

        void putOrders(List<OperateBean> orders);

        void checkOrderStatus(String orderUid);

        void requestWalletPay(BaseRequest request);

    }

    interface Model extends BaseContract.Model {
        Observable<BaseResponse<OrderDetailBean>> getOrderDetail(String uid);

        Observable<BaseResponse> deleteOrders(List<OperateBean> orders);

        Observable<BaseResponse> putOrders(List<OperateBean> orders);

        Observable<BaseResponse<OperateBean>> checkOrderStatus(String orderUid);

        Observable<BaseOldResponse> requestWalletPay(BaseRequest request);

    }

}
