package com.example.ecmain.mine.presenter;

import android.support.annotation.NonNull;

import com.example.ecmain.mine.contract.MineContract;
import com.example.ecmain.mine.model.MineModel;

import java.util.List;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.login.model.UserInfoBean;
import cn.itsite.order.model.CategoryBean;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liujia on 2018/5/14.
 */

public class MinePresenter extends BasePresenter<MineContract.View,MineContract.Model> implements MineContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public MinePresenter(MineContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected MineContract.Model createModel() {
        return new MineModel();
    }

    @Override
    public void requestInfo(String token) {
        mRxManager.add(mModel.requestInfo(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse<UserInfoBean.MemberInfoBean>>() {
                    @Override
                    public void onSuccess(BaseOldResponse<UserInfoBean.MemberInfoBean> response) {
                        getView().responseInfo(response);
                    }
                }));
    }

    @Override
    public void getCategories(GoodsParams goodsParams) {
        mRxManager.add(mModel.getCategories(goodsParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<CategoryBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<CategoryBean>> listBaseResponse) {
                        getView().responseGetCategories(listBaseResponse.getData());
                    }
                }));
    }
}
