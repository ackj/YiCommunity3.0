package cn.itsite.amain.s1.linkage.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.linkage.contract.LinkageListContract;
import cn.itsite.amain.s1.linkage.model.LinkageListModel;
import rx.android.schedulers.AndroidSchedulers;


public class LinkageListPresenter extends BasePresenter<LinkageListContract.View, LinkageListContract.Model> implements LinkageListContract.Presenter {

    private final String TAG = LinkageListPresenter.class.getSimpleName();

    public LinkageListPresenter(LinkageListContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected LinkageListContract.Model createModel() {
        return new LinkageListModel();
    }

    @Override
    public void requestLinkageList(Params params) {
        mRxManager.add(mModel.requestLinkageList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseLinkageList(bean.getData());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestModLinkage(Params params) {
        mRxManager.add(mModel.requestModLinkage(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseModLinkage(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestDeleteLinkage(Params params) {
        mRxManager.add(mModel.requestDeleteLinkage(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDeleteLinkage(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }


}