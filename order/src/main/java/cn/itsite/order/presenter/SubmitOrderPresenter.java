package cn.itsite.order.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.DeliveryBean;
import cn.itsite.acommon.OperatorBean;
import cn.itsite.order.contract.SubmitOrderContract;
import cn.itsite.order.model.SubmitOrderModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/2 0002 15:30
 */

public class SubmitOrderPresenter extends BasePresenter<SubmitOrderContract.View,SubmitOrderContract.Model> implements SubmitOrderContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public SubmitOrderPresenter(SubmitOrderContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected SubmitOrderContract.Model createModel() {
        return new SubmitOrderModel();
    }

    @Override
    public void getAddress() {
        mRxManager.add(mModel.getAddress()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<DeliveryBean>>>(){
                    @Override
                    public void onSuccess(BaseResponse<List<DeliveryBean>> listBaseResponse) {
                        getView().responseGetAddress(listBaseResponse.getData());
                    }
                }));
    }

    @Override
    public void postOrders(List<OperatorBean> data) {
        mRxManager.add(mModel.postOrders(data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>(){
                    @Override
                    public void onSuccess(BaseResponse response) {
                        getView().responsePostOrdersSuccess(response);
                    }
                }));
    }
}