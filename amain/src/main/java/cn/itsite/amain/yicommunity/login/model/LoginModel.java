package cn.itsite.amain.yicommunity.login.model;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.amain.yicommunity.entity.bean.SipBean;
import cn.itsite.abase.common.UserBean;
import cn.itsite.amain.yicommunity.login.contract.LoginContract;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 */

public class LoginModel extends BaseModel implements LoginContract.Model {
    private static final String TAG = LoginModel.class.getSimpleName();

    @Override
    public Observable<UserBean> requestLogin(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestLogin(ApiService.requestLogin,
                        params.sc,
                        params.fc,
                        BaseApp.PUSH_TYPE,
                        params.user,
                        params.pwd)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SipBean> requestSip(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSip(ApiService.requestSip, params.token)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void registerPush() {
        HttpHelper.getService(ApiService.class)
                .registerDevice(ApiService.registerDevice, UserHelper.token, UserHelper.getDeviceID(), UserHelper.account, "userType")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> ALog.e(TAG, baseBean.getOther().getMessage()));
    }
}