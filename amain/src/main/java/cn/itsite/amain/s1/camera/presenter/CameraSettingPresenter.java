package cn.itsite.amain.s1.camera.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;

import cn.itsite.amain.s1.camera.contract.CameraSettingContract;
import cn.itsite.amain.s1.camera.model.CameraSettingModel;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;

import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/9/19 0019 08:51.
 * Email: liujia95me@126.com
 */

public class CameraSettingPresenter extends BasePresenter<CameraSettingContract.View,CameraSettingContract.Model> implements CameraSettingContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public CameraSettingPresenter(CameraSettingContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected CameraSettingContract.Model createModel() {
        return new CameraSettingModel();
    }

    @Override
    public void requestModCamera(Params params) {
        mRxManager.add(mModel.requestModCamera(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseSuccess(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }
}
