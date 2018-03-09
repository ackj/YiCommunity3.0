package cn.itsite.amain.s1.security.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.GatewaysBean;
import cn.itsite.amain.s1.security.contract.SecurityContract;
import cn.itsite.amain.s1.security.model.SecurityModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/7/4 0004 09:36.
 * Email: liujia95me@126.com
 */

public class SecurityPresenter extends BasePresenter<SecurityContract.View, SecurityContract.Model> implements SecurityContract.Presenter {
    public static final String TAG = SecurityPresenter.class.getSimpleName();

    public SecurityPresenter(SecurityContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected SecurityContract.Model createModel() {
        return new SecurityModel();
    }

    @Override
    public void requestSecurity(Params params) {
        mRxManager.add(mModel.requestSecurity(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(securityBean -> {
                    if (securityBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseSecurity(securityBean);
                    } else {
                        getView().error(securityBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestGateways(Params params) {
//        mRxManager.add(mModel.requestGateways(params)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(gatewaysBean -> {
//                    if (gatewaysBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
//                        getView().responseGateways(gatewaysBean);
//                    } else {
//                        getView().error(gatewaysBean.getOther().getMessage());
//                    }
//                }, this::error/*, this::complete, disposable -> start(null)*/));

        mRxManager.add(mModel.requestGateways(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<GatewaysBean>() {
                    @Override
                    public void _onNext(GatewaysBean gatewaysBean) {
                        if (gatewaysBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseGateways(gatewaysBean);
                        } else {
                            getView().error(gatewaysBean.getOther().getMessage());
                        }
                    }
                }));
    }

    @Override
    public void requestSwichGateway(Params params) {
//        mRxManager.add(mModel.requestSwichGateway(params)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(baseBean -> {
//                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
//                        getView().responseSwichGateway(baseBean);
//                    } else {
//                        getView().error(baseBean.getOther().getMessage());
//                    }
//                }, this::error/*, this::complete, disposable -> start(null)*/)
//        );

        mRxManager.add(mModel.requestSwichGateway(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseBean>() {
                    @Override
                    public void _onNext(BaseBean baseBean) {
                        if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseSwichGateway(baseBean);
                        } else {
                            getView().error(baseBean.getOther().getMessage());
                        }
                    }
                }));
    }

    @Override
    public void requestSwichState(Params params) {
//        mRxManager.add(mModel.requestSwichState(params)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(baseBean -> {
//                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
//                        getView().responseSwichState(baseBean);
//                    } else {
//                        getView().error(baseBean.getOther().getMessage());
//                    }
//                }, this::error/*, this::complete, disposable -> start(null)*/)
//        );

        mRxManager.add(mModel.requestSwichState(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseBean>() {
                    @Override
                    public void _onNext(BaseBean baseBean) {
                        if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseSwichState(baseBean);
                        } else {
                            getView().error(baseBean.getOther().getMessage());
                        }
                    }
                }));
    }

    @Override
    public void requestLeaveMassge(Params params) {
//        mRxManager.add(mModel.requestLeaveMassge(params)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(baseBean -> {
//                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
//                        getView().responseSwichState(baseBean);
//                    } else {
//                        getView().error(baseBean.getOther().getMessage());
//                    }
//                }, this::error/*, this::complete, disposable -> start(null)*/)
//        );

        mRxManager.add(mModel.requestLeaveMassge(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseBean>() {
                    @Override
                    public void _onNext(BaseBean baseBean) {
                        if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseSwichState(baseBean);
                        } else {
                            getView().error(baseBean.getOther().getMessage());
                        }
                    }
                }));
    }
}
