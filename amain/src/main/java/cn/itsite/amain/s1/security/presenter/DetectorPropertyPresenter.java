package cn.itsite.amain.s1.security.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.security.contract.DetectorPropertyContract;
import cn.itsite.amain.s1.security.model.DetectorPropertyModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/7/4 0004 15:12.
 * Email: liujia95me@126.com
 */

public class DetectorPropertyPresenter extends BasePresenter<DetectorPropertyContract.View, DetectorPropertyContract.Model> implements DetectorPropertyContract.Presenter {
    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public DetectorPropertyPresenter(DetectorPropertyContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected DetectorPropertyContract.Model createModel() {
        return new DetectorPropertyModel();
    }

    @Override
    public void requestNotifProperty(Params params) {
        mRxManager.add(mModel.requestNotifProperty(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseNodifSuccess(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error)
        );
    }

    @Override
    public void requestDelsensor(Params params) {
        mRxManager.add(mModel.requestDelsensor(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDelSuccess(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error)
        );
    }

    @Override
    public void requestSubDeviceDet(Params params) {
        mRxManager.add(mModel.requestSubDeviceDet(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subDeviceDetBean -> {
                    if (subDeviceDetBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseSubDeviceDet(subDeviceDetBean);
                    } else {
                        getView().error(subDeviceDetBean.getOther().getMessage());
                    }
                }, this::error)
        );
    }

    @Override
    public void requestModsensor(Params params) {
        mRxManager.add(mModel.requestModsensor(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseModsensor(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error)
        );
    }
}
