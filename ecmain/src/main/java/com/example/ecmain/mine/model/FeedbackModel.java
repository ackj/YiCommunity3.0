package com.example.ecmain.mine.model;

import com.example.ecmain.mine.contract.FeedbackContract;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.login.contract.LoginService;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 07/05/2018.
 */

public class FeedbackModel extends BaseModel implements FeedbackContract.Model{
    @Override
    public Observable<BaseOldResponse> requestFeedback(String content) {
        return HttpHelper.getService(LoginService.class)
                .requestFeedback(LoginService.requestFeedback,
                        content)
                .subscribeOn(Schedulers.io());
    }
}
