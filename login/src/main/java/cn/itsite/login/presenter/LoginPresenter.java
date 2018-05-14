package cn.itsite.login.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.contract.LoginContract;
import cn.itsite.login.model.LoginModel;
import cn.itsite.login.model.UserInfoBean;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liujia on 03/05/2018.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View,LoginContract.Model> implements LoginContract.Presenter {

    private static final String TAG= LoginPresenter.class.getSimpleName();

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public LoginPresenter(LoginContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected LoginContract.Model createModel() {
        return new LoginModel();
    }

    @Override
    public void requestLogin(UserParams params) {
        mRxManager.add(mModel.requestLogin(params)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseOldSubscriber<BaseOldResponse<UserInfoBean>>() {
                            @Override
                            public void onSuccess(BaseOldResponse<UserInfoBean> response) {
                                ALog.e(TAG,"onSuccess");
                                UserHelper.setAccount(params.username,params.pwd);
                                UserHelper.setAvator(response.getData().getMemberInfo().getFace());
                                UserHelper.setMobile(response.getData().getMemberInfo().getMobile());
                                UserHelper.setNickname(response.getData().getMemberInfo().getNickName());
                                UserHelper.setToken(response.getData().getMemberInfo().getToken());
                                UserHelper.setMoney(response.getData().getMemberInfo().getMoney());
                                mModel.registerPush();

                                getView().responseLogin(response.getData());

                            }
                        }));
    }
}
