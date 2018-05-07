package com.example.ecmain.mine.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.model.PushEnableBean;
import rx.Observable;

/**
 * Created by liujia on 04/05/2018.
 */

public interface SettingContract {

    interface View extends BaseContract.View{
        void responsePushConfig(BaseOldResponse response);
        void responseLogout(BaseOldResponse response);
        void requestMemberConfigInfo(PushEnableBean pushEnable);
        void responseCache(String message);
    }

    interface Model extends BaseContract.Model{
        Observable<BaseOldResponse> requestPushConfig(UserParams params);
        Observable<BaseOldResponse> requestLogout(UserParams params);
        Observable<BaseOldResponse<PushEnableBean>> requestMemberConfigInfo();
        Observable<String> requestCache();
        Observable<String> requestClearCache();
    }

    interface Presenter extends BaseContract.Presenter{
        void requestPushConfig(UserParams params);
        void requestLogout(UserParams params);
        void requestMemberConfigInfo();
        void requestCache();
        void requestClearCache();
    }

}
