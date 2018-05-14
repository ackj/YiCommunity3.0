package cn.itsite.amain.yicommunity.login.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.login.contract.RegisterContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/9 0009 19:37.
 * Email: liujia95me@126.com
 */

public class RegisterModel extends BaseModel implements RegisterContract.Model {
    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<BaseBean> register(Params params) {
        return HttpHelper.getService(ApiService.class).requestRegister(ApiService.requestRegister, params.sc, params.account, params.verifyCode, params.password1, params.password2)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestVerifyCode(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestVerifyCode(ApiService.requestVerifyCode, params.sc, params.phoneNo, params.verifyType)
                .subscribeOn(Schedulers.io());
    }
}
