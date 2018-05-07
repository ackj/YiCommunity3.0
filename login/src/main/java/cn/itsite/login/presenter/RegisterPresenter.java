package cn.itsite.login.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.contract.RegisterContract;
import cn.itsite.login.model.RegisterModel;
import cn.itsite.login.model.VerifyCodeBean;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liujia on 04/05/2018.
 */

public class RegisterPresenter  extends BasePresenter<RegisterContract.View,RegisterContract.Model> implements RegisterContract.Presenter{

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public RegisterPresenter(RegisterContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected RegisterContract.Model createModel() {
        return new RegisterModel();
    }

    @Override
    public void requestRegister(UserParams params) {
        mRxManager.add(mModel.requestRegister(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse>() {
                    @Override
                    public void onSuccess(BaseOldResponse response) {
                        getView().responseRegister(response);
                    }
                }));
    }

    @Override
    public void requestVerifyCode(UserParams params) {
        mRxManager.add(mModel.requestVerifyCode(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse<VerifyCodeBean>>() {
                    @Override
                    public void onSuccess(BaseOldResponse<VerifyCodeBean> response) {
                        getView().responseVerifyCode(response.getData());
                    }
                }));
    }
}
