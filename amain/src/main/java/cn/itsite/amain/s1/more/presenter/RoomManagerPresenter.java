package cn.itsite.amain.s1.more.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.more.contract.RoomManagerContract;
import cn.itsite.amain.s1.more.model.RoomManagerModel;
import rx.android.schedulers.AndroidSchedulers;


public class RoomManagerPresenter extends BasePresenter<RoomManagerContract.View, RoomManagerContract.Model> implements RoomManagerContract.Presenter {
    private final String TAG = RoomManagerPresenter.class.getSimpleName();

    public RoomManagerPresenter(RoomManagerContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected RoomManagerContract.Model createModel() {
        return new RoomManagerModel();
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

    @Override
    public void requestRoomTypeList(Params params) {
        mRxManager.add(mModel.requestRoomTypeList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseRoomTypeList(bean.getData());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/));
    }

    @Override
    public void requestDelroom(Params params) {
        mRxManager.add(mModel.requestDelroom(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDelroom(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/));
    }

    @Override
    public void requestAddHouse(Params params) {
        mRxManager.add(mModel.requestAddHouse(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseAddHouse(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/));
    }

}