package cn.itsite.amain.yicommunity.main.about.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.main.about.contract.FeedbackContract;
import cn.itsite.amain.yicommunity.main.about.model.FeedbackModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 反馈模块Presenter层内容。
 */

public class FeedbackPresenter extends BasePresenter<FeedbackContract.View, FeedbackContract.Model> implements FeedbackContract.Presenter {
    private final String TAG = FeedbackPresenter.class.getSimpleName();

    public FeedbackPresenter(FeedbackContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected FeedbackContract.Model createModel() {
        return new FeedbackModel();
    }

    @Override
    public void start(Object request) {
        mRxManager.add(mModel.requestSubmitFeedback((Params) request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == 200) {
                        getView().start(baseBean.getOther().getMessage());
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error)
        );
    }
}