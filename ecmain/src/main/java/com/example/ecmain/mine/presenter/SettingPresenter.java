package com.example.ecmain.mine.presenter;

import android.support.annotation.NonNull;

import com.example.ecmain.mine.contract.SettingContract;
import com.example.ecmain.mine.model.SettingModel;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.model.PushEnableBean;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liujia on 04/05/2018.
 */

public class SettingPresenter extends BasePresenter<SettingContract.View,SettingContract.Model> implements SettingContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public SettingPresenter(SettingContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected SettingContract.Model createModel() {
        return new SettingModel();
    }

    @Override
    public void requestPushConfig(UserParams params) {
        mRxManager.add(mModel.requestPushConfig(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse>() {
                    @Override
                    public void onSuccess(BaseOldResponse response) {
                        getView().responsePushConfig(response);
                    }
                }));
    }

    @Override
    public void requestLogout(UserParams params) {
        mRxManager.add(mModel.requestLogout(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse>() {
                    @Override
                    public void onSuccess(BaseOldResponse response) {
                        getView().responseLogout(response);
                    }
                }));
    }

    @Override
    public void requestMemberConfigInfo() {
        mRxManager.add(mModel.requestMemberConfigInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse<PushEnableBean>>() {
                    @Override
                    public void onSuccess(BaseOldResponse<PushEnableBean> response) {
                        getView().requestMemberConfigInfo(response.getData());
                    }
                }));
    }

    @Override
    public void requestCache() {
        mRxManager.add(mModel.requestCache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (isViewAttached()) {
                        getView().responseCache(s);
                    }
                }, this::error)
        );
    }

    @Override
    public void requestClearCache() {
        mRxManager.add(mModel.requestClearCache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (isViewAttached()) {
                        getView().responseCache(s);
                    }
                }, this::error)
        );
    }
}
