package cn.itsite.order.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.acommon.OperateBean;
import cn.itsite.order.model.OrderBean;
import cn.itsite.order.contract.OrderListContract;
import cn.itsite.order.model.OrderListModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/19 0019 16:42
 */

public class OrderListPresenter extends BasePresenter<OrderListContract.View,OrderListContract.Model> implements OrderListContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public OrderListPresenter(OrderListContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected OrderListContract.Model createModel() {
        return new OrderListModel();
    }

    @Override
    public void getOrders(GoodsParams goodsParams) {
        mRxManager.add(mModel.getOrders(goodsParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<OrderBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<OrderBean>> listBaseResponse) {
                        getView().responseOrders(listBaseResponse.getData());
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
}
