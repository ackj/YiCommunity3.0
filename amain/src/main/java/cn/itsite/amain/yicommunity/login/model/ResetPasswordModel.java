package cn.itsite.amain.yicommunity.login.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.BaseBean;
import cn.itsite.amain.yicommunity.login.contract.ResetPasswordContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/6/23 0023 19:21.
 * Email: liujia95me@126.com
 */

public class ResetPasswordModel extends BaseModel implements ResetPasswordContract.Model {

    @Override
    public Observable<BaseBean> requestReset(Params params) {
        return HttpHelper.getService(ApiService.class).requestResetPassword(ApiService.requestResetPassword, params.sc, params.account, params.verifyCode, params.password1, params.password2)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestVerifyCode(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestVerifyCode(ApiService.requestVerifyCode, params.sc, params.phoneNo, params.verifyType)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void start(Object request) {

    }
}
