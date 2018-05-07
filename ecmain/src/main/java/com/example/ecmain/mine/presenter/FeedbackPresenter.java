package com.example.ecmain.mine.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseOldResponse;
import com.example.ecmain.mine.contract.FeedbackContract;
import com.example.ecmain.mine.model.FeedbackModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liujia on 07/05/2018.
 */

public class FeedbackPresenter extends BasePresenter<FeedbackContract.View,FeedbackContract.Model> implements FeedbackContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public FeedbackPresenter(FeedbackContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected FeedbackContract.Model createModel() {
        return new FeedbackModel();
    }

    @Override
    public void requestFeedback(String content) {
        mRxManager.add(mModel.requestFeedback(content)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse>() {
                    @Override
                    public void onSuccess(BaseOldResponse response) {
                        getView().responseFeedback(response);
                    }
                }));
    }
}
