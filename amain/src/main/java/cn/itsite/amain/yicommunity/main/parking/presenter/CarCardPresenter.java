package cn.itsite.amain.yicommunity.main.parking.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.main.parking.contract.CarCardContract;
import cn.itsite.amain.yicommunity.main.parking.model.CarCardModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块Presenter层内容。
 */

public class CarCardPresenter extends BasePresenter<CarCardContract.View, CarCardContract.Model> implements CarCardContract.Presenter {
    private final String TAG = CarCardPresenter.class.getSimpleName();

    public CarCardPresenter(CarCardContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected CarCardContract.Model createModel() {
        return new CarCardModel();
    }

    @Override
    public void requestCarCardList(Params params) {
        mRxManager.add(mModel.requestCarCardList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(carCardListBean -> {
                    if (carCardListBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseCarCardList(carCardListBean.getData().getCardList());
                    } else {
                        getView().error(carCardListBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestDeleteCarCard(Params params) {
        mRxManager.add(mModel.requestDeleteCarCard(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDeleteSuccess(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error));
    }
}