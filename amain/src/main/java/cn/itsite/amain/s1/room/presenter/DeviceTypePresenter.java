package cn.itsite.amain.s1.room.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.room.contract.DeviceTypeContract;
import cn.itsite.amain.s1.room.model.DeviceTypeModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/8/30 0030 20:07.
 * Email: liujia95me@126.com
 */

public class DeviceTypePresenter extends BasePresenter<DeviceTypeContract.View, DeviceTypeContract.Model> implements DeviceTypeContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public DeviceTypePresenter(DeviceTypeContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected DeviceTypeContract.Model createModel() {
        return new DeviceTypeModel();
    }

    @Override
    public void requestDeviceType(Params params) {
        mRxManager.add(mModel.requestDeviceType(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDeviceType(bean.getData().getDeviceTypeList());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestAddDevice(Params params) {
        mRxManager.add(mModel.requestAddDevice(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseAddDevice(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestAddCamera(Params params) {
        mRxManager.add(mModel.requestAddCamera(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseAddDevice(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }
}
