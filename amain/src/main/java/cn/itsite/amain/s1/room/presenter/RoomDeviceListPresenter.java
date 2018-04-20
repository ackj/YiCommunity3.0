package cn.itsite.amain.s1.room.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.room.contract.RoomDeviceListContract;
import cn.itsite.amain.s1.room.model.RoomDeviceListModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/8/20 0020 18:26.
 * Email: liujia95me@126.com
 */

public class RoomDeviceListPresenter extends BasePresenter<RoomDeviceListContract.View, RoomDeviceListContract.Model> implements RoomDeviceListContract.Presenter {
    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public RoomDeviceListPresenter(RoomDeviceListContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected RoomDeviceListContract.Model createModel() {
        return new RoomDeviceListModel();
    }

    @Override
    public void requestDeviceList(Params params) {
        mRxManager.add(mModel.requestDeviceList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDeviceList(bean.getData().getSubDevices());
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
                }, this::error/*, this::complete, disposable -> start(null)*/));
    }

    @Override
    public void requestDevicectrl(Params params) {
        mRxManager.add(mModel.requestDevicectrl(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDevicectrl(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/));
    }

    @Override
    public void requestNewDeviceConfirm(Params params) {
        mRxManager.add(mModel.requestNewDeviceConfirm(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseNewDeviceConfirm(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/));
    }

    @Override
    public void requestNewDevice24(Params params) {
        mRxManager.add(mModel.requestNewDevice24(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseNewDevice24(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/));
    }

    @Override
    public void requestDelDevice(Params params) {
        mRxManager.add(mModel.requestDelDevice(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDelSuccess(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }
}
