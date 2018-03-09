package cn.itsite.amain.s1.host.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.host.contract.HostListContract;
import cn.itsite.amain.s1.host.model.HostListModel;
import rx.android.schedulers.AndroidSchedulers;


public class HostListPresenter extends BasePresenter<HostListContract.View, HostListContract.Model> implements HostListContract.Presenter {
    private final String TAG = HostListPresenter.class.getSimpleName();

    public HostListPresenter(HostListContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected HostListContract.Model createModel() {
        return new HostListModel();
    }

    @Override
    public void requestGateways(Params params) {
        mRxManager.add(mModel.requestGateways(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseGateways(bean.getData());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }
}