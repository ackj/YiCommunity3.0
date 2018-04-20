package cn.itsite.amain.yicommunity.main.parking.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.CarportBeam;
import cn.itsite.amain.yicommunity.main.parking.contract.CarportContract;
import cn.itsite.amain.yicommunity.main.parking.model.CarportModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车位模块Presenter。
 */
public class CarportPresenter extends BasePresenter<CarportContract.View, CarportContract.Model> implements CarportContract.Presenter {
    public final String TAG = CarportPresenter.class.getSimpleName();

    public CarportPresenter(CarportContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected CarportContract.Model createModel() {
        return new CarportModel();
    }

    @Override
    public void requestCarports(Params params) {
        mRxManager.add(mModel.requestCarports(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<CarportBeam>() {
                    @Override
                    public void _onNext(CarportBeam bean) {
                        if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseCarports(bean);
                        } else {
                            getView().error(bean.getOther().getMessage());
                        }
                    }
                }));
    }
}