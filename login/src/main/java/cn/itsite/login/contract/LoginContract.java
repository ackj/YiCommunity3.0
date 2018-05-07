package cn.itsite.login.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.model.UserInfoBean;
import rx.Observable;

/**
 * Created by liujia on 03/05/2018.
 */

public interface LoginContract {

    interface View extends BaseContract.View{
        void responseLogin(UserInfoBean bean);
    }
    interface Presenter extends BaseContract.Presenter {
        void requestLogin(UserParams params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseOldResponse<UserInfoBean>> requestLogin(UserParams params);

        void registerPush();
    }
}
