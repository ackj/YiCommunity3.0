package cn.itsite.amain.s1.host.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.host.contract.HostSettingsContract;
import cn.itsite.amain.s1.host.model.HostSettingsModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责添加主机模块Presenter层内容。
 */

public class HostSettingsPresenter extends BasePresenter<HostSettingsContract.View, HostSettingsContract.Model> implements HostSettingsContract.Presenter {
    private final String TAG = HostSettingsPresenter.class.getSimpleName();

    public HostSettingsPresenter(HostSettingsContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected HostSettingsContract.Model createModel() {
        return new HostSettingsModel();
    }

    @Override
    public void requestSetHost(Params params) {
        mRxManager.add(mModel.requestSetHost(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseSetHost(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/));
    }

    @Override
    public void requestHostSettings(Params params) {
        mRxManager.add(mModel.requestHostSettings(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseHostSettings(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/));
    }

    @Override
    public void requestGatewayTest(Params params) {
        mRxManager.add(mModel.requestGatewayTest(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseGatewayTest(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/));
    }
}