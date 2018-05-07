package cn.itsite.login.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.contract.ResetPwdContract;
import cn.itsite.login.model.ResetPwdModel;
import cn.itsite.login.model.VerifyCodeBean;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liujia on 04/05/2018.
 */

public class ResetPwdPresenter extends BasePresenter<ResetPwdContract.View,ResetPwdContract.Model> implements ResetPwdContract.Presenter{

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public ResetPwdPresenter(ResetPwdContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected ResetPwdContract.Model createModel() {
        return new ResetPwdModel();
    }

    @Override
    public void requestResetPwd(UserParams params) {
        mRxManager.add(mModel.requestResetPwd(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse>() {
                    @Override
                    public void onSuccess(BaseOldResponse response) {
                        getView().responseResetPwd(response);
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
