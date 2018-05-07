package cn.itsite.login.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.contract.LoginService;
import cn.itsite.login.contract.ResetPwdContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 04/05/2018.
 */

public class ResetPwdModel extends BaseModel implements ResetPwdContract.Model {

    @Override
    public Observable<BaseOldResponse> requestResetPwd(UserParams params) {
        return HttpHelper.getService(LoginService.class)
                .requestResetPwd(LoginService.requestResetPwd,
                        params.username,
                        params.code,
                        params.pwd,
                        params.pwd)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseOldResponse<VerifyCodeBean>> requestVerifyCode(UserParams params) {
        return HttpHelper.getService(LoginService.class)
                .requestVerifyCode(LoginService.requestVerifyCode,
                        params.phone,
                        LoginService.TYPE_RESET_PWD)
                .subscribeOn(Schedulers.io());
    }
}
