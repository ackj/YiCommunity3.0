package com.example.ecmain.mine.presenter;

import android.support.annotation.NonNull;

import com.example.ecmain.App;
import com.example.ecmain.mine.contract.EditInfoContract;
import com.example.ecmain.mine.model.EditInfoModel;

import cn.itsite.abase.common.luban.Luban;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.UserParams;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 04/05/2018.
 */

public class EditInfoPresenter extends BasePresenter<EditInfoContract.View,EditInfoContract.Model> implements EditInfoContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public EditInfoPresenter(EditInfoContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected EditInfoContract.Model createModel() {
        return new EditInfoModel();
    }

    @Override
    public void requestUpdateAvator(UserParams params) {
        Luban.get(App.mContext)
                .load(params.file)
                .putGear(Luban.THIRD_GEAR)
                .asObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    params.file = file;
                    mRxManager.add(mModel.requestUpdateAvator(params)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseOldSubscriber<BaseOldResponse<String>>() {
                                @Override
                                public void onSuccess(BaseOldResponse<String> response) {
                                    getView().responseUpdateAvator(response);
                                }
                            }));
                });

    }

    @Override
    public void requestNickname(UserParams params) {
        mRxManager.add(mModel.requestNickname(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse>() {
                    @Override
                    public void onSuccess(BaseOldResponse response) {
                        getView().responseNickname(response);
                    }
                }));
    }
}
