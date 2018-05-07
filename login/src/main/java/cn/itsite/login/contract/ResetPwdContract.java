package cn.itsite.login.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.model.VerifyCodeBean;
import rx.Observable;

/**
 * Created by liujia on 04/05/2018.
 */

public interface ResetPwdContract {

    interface View extends BaseContract.View{
        void responseResetPwd(BaseOldResponse response);
        void responseVerifyCode(VerifyCodeBean bean);
    }
    interface Presenter extends BaseContract.Presenter {
        void requestResetPwd(UserParams params);
        void requestVerifyCode(UserParams params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseOldResponse> requestResetPwd(UserParams params);
        Observable<BaseOldResponse<VerifyCodeBean>> requestVerifyCode(UserParams params);
    }
}
