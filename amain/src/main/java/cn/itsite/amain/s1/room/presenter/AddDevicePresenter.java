package cn.itsite.amain.s1.room.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.room.contract.AddDeviceContract;
import cn.itsite.amain.s1.room.model.AddDeviceModel;
import rx.android.schedulers.AndroidSchedulers;


public class AddDevicePresenter extends BasePresenter<AddDeviceContract.View, AddDeviceContract.Model> implements AddDeviceContract.Presenter {
    private final String TAG = AddDevicePresenter.class.getSimpleName();

    public AddDevicePresenter(AddDeviceContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected AddDeviceContract.Model createModel() {
        return new AddDeviceModel();
    }

    @Override
    public void requestnewDevice(Params params) {
        mRxManager.add(mModel.requestnewDevice(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseSuccess(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestModDevice(Params params) {
        mRxManager.add(mModel.requestModDevice(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseSuccess(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestDelDevice(Params params) {
        mRxManager.add(mModel.requestDelDevice(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseSuccess(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestHouseList(Params params) {
        mRxManager.add(mModel.requestHouseList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseHouseList(bean.getData().getRoomList());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

}