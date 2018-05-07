package cn.itsite.login.model;

import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.contract.LoginContract;
import cn.itsite.login.contract.LoginService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 03/05/2018.
 */

public class LoginModel extends BaseModel implements LoginContract.Model{

    private static final String TAG = LoginModel.class.getSimpleName();

    @Override
    public Observable<BaseOldResponse<UserInfoBean>> requestLogin(UserParams params) {
        return HttpHelper.getService(LoginService.class)
                .requestLogin(LoginService.requestLogin,
                        params.username,
                        params.pwd)
                .subscribeOn(Schedulers.io());
    }

        @Override
        public void registerPush() {
            HttpHelper.getService(LoginService.class)
                    .registerDevice(LoginService.registerDevice, UserHelper.token, UserHelper.getDeviceID(), UserHelper.account, "userType")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseBean -> ALog.e(TAG, baseBean.getOther().getMessage()));
        }


}
