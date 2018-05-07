package com.example.ecmain.mine.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.UserParams;
import rx.Observable;

/**
 * Created by liujia on 04/05/2018.
 */

public interface EditInfoContract {

    interface View extends BaseContract.View{
        void responseUpdateAvator(BaseOldResponse<String> response);
        void responseNickname(BaseOldResponse response);
    }

    interface Model extends BaseContract.Model{
       Observable<BaseOldResponse<String>> requestUpdateAvator(UserParams params);
       Observable<BaseOldResponse>         requestNickname(UserParams params);
    }

    interface Presenter extends BaseContract.Presenter{
        void requestUpdateAvator(UserParams params);
        void requestNickname(UserParams params);
    }

}
