package com.example.ecmain.mine.model;

import com.example.ecmain.App;
import com.example.ecmain.mine.contract.SettingContract;

import cn.itsite.abase.cache.CacheManager;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.contract.LoginService;
import cn.itsite.login.model.PushEnableBean;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 04/05/2018.
 */

public class SettingModel extends BaseModel implements SettingContract.Model {

    @Override
    public Observable<BaseOldResponse> requestPushConfig(UserParams params) {
        return HttpHelper.getService(LoginService.class)
                .requestPushConfig(LoginService.requestPushConfig, UserHelper.token,params.pushEnable)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseOldResponse> requestLogout(UserParams params) {
        return HttpHelper.getService(LoginService.class)
                .requestLogout(LoginService.requestLogout,UserHelper.token)
                .subscribeOn(Schedulers.io());

    }

    @Override
    public Observable<BaseOldResponse<PushEnableBean>> requestMemberConfigInfo() {
        return HttpHelper.getService(LoginService.class)
                .requsetMemberConfigInfo(LoginService.requsetMemberConfigInfo,UserHelper.token)
                .subscribeOn(Schedulers.io());
    }


    @Override
    public Observable<String> requestCache() {
        return Observable.create((Observable.OnSubscribe<String>) s ->
                s.onNext(CacheManager.getTotalCacheSize(App.mContext))
        ).subscribeOn(Schedulers.computation());
    }

    @Override
    public Observable<String> requestClearCache() {
        return Observable.create((Observable.OnSubscribe<String>) s -> {
                    CacheManager.clearAllCache(App.mContext);
                    s.onNext(CacheManager.getTotalCacheSize(App.mContext));
                }
        ).subscribeOn(Schedulers.computation());
    }
}
