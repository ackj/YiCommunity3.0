package cn.itsite.order.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.bean.OperateBean;
import cn.itsite.acommon.model.OrderDetailBean;
import cn.itsite.order.contract.OrderDetailContract;
import cn.itsite.order.model.OrderDetailModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/20 0020 16:46
 */

public class OrderDetailPresenter extends BasePresenter<OrderDetailContract.View, OrderDetailContract.Model> implements OrderDetailContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public OrderDetailPresenter(OrderDetailContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected OrderDetailContract.Model createModel() {
        return new OrderDetailModel();
    }

    @Override
    public void getOrderDetail(String uid) {
        mRxManager.add(mModel.getOrderDetail(uid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<OrderDetailBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<OrderDetailBean> listBaseResponse) {
                        getView().responseOrderDetail(listBaseResponse.getData());
                    }
                }));
    }

    @Override
    public void deleteOrders(List<OperateBean> orders) {
        mRxManager.add(mModel.deleteOrders(orders)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        getView().responseDeleteSuccess(response);
                    }
                }));
    }

    @Override
    public void putOrders(List<OperateBean> orders) {
        mRxManager.add(mModel.putOrders(orders)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        getView().responsePutSuccess(response);
                    }
                }));
    }

    @Override
    public void checkOrderStatus(String orderUid) {
        mRxManager.add(mModel.checkOrderStatus(orderUid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<OperateBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<OperateBean> operateBeanBaseResponse) {
                        getView().responseCheckOrderStatus(operateBeanBaseResponse.getData().getStatus());
                    }
                }));
    }

    @Override
    public void requestWalletPay(BaseRequest request) {
        mRxManager.add(mModel.requestWalletPay(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse>() {
                    @Override
                    public void onSuccess(BaseOldResponse response) {
                        getView().responseWalletPay(response);
                    }
                }));
    }
}
