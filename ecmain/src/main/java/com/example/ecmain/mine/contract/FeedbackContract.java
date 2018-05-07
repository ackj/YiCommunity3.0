package com.example.ecmain.mine.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseOldResponse;
import rx.Observable;

/**
 * Created by liujia on 07/05/2018.
 */

public interface FeedbackContract {


    interface View extends BaseContract.View{
        void responseFeedback(BaseOldResponse response);
    }
    interface Presenter extends BaseContract.Presenter {
        void requestFeedback(String content);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseOldResponse> requestFeedback(String content);
    }

}
