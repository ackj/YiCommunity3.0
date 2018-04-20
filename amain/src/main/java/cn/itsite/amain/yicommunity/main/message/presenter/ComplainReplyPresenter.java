package cn.itsite.amain.yicommunity.main.message.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.main.message.contract.ComplainReplyContract;
import cn.itsite.amain.yicommunity.main.message.model.ComplainReplyModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/5/18 0018 18:04.
 * Email: liujia95me@126.com
 */

public class ComplainReplyPresenter extends BasePresenter<ComplainReplyContract.View, ComplainReplyContract.Model> implements ComplainReplyContract.Presenter {

    public ComplainReplyPresenter(ComplainReplyContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected ComplainReplyContract.Model createModel() {
        return new ComplainReplyModel();
    }

    @Override
    public void start(Object request) {
        mRxManager.add(mModel.requestComplainReplies((Params) request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().start(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }
}
