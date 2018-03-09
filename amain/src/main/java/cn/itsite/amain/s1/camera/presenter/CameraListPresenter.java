package cn.itsite.amain.s1.camera.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;

import cn.itsite.amain.s1.camera.contract.CameraListContract;
import cn.itsite.amain.s1.camera.model.CameraListModel;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;

import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/9/15 0015 18:20.
 * Email: liujia95me@126.com
 */

public class CameraListPresenter extends BasePresenter<CameraListContract.View,CameraListContract.Model> implements CameraListContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public CameraListPresenter(CameraListContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected CameraListContract.Model createModel() {
        return new CameraListModel();
    }

    @Override
    public void requestCameraList(Params params) {
        mRxManager.add(mModel.requestCameraList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseCameraList(bean.getData());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestNewCamera(Params params) {
        mRxManager.add(mModel.requestNewCamera(params)
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
    public void requestDelCamera(Params params) {
        mRxManager.add(mModel.requestDelCamera(params)
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
    public void requestModCamera(Params params) {
        mRxManager.add(mModel.requestModCamera(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseModSuccess(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }
}
