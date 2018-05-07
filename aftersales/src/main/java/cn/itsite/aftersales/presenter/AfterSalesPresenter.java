package cn.itsite.aftersales.presenter;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.List;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.common.luban.Luban;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.bean.OperateBean;
import cn.itsite.aftersales.contract.AfterSalesContract;
import cn.itsite.aftersales.model.AfterSalesModel;
import cn.itsite.aftersales.model.PostApplyBean;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/17 0017 17:47
 */

public class AfterSalesPresenter extends BasePresenter<AfterSalesContract.View,AfterSalesContract.Model> implements AfterSalesContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public AfterSalesPresenter(AfterSalesContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected AfterSalesContract.Model createModel() {
        return new AfterSalesModel();
    }

    @Override
    public void postApply(PostApplyBean applyBean) {
        mRxManager.add(mModel.postApply(applyBean)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>(){
                    @Override
                    public void onSuccess(BaseResponse response) {
                        getView().responsePostSuccess(response);
                    }
                }));
    }

    @Override
    public void getReasontType() {
        mRxManager.add(mModel.getReasonType()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>(){
                    @Override
                    public void onSuccess(BaseResponse response) {
                        getView().responseReasonType(response);
                    }
                }));
    }

    @Override
    public void postPicture(List<File> pictures) {
        Luban.get(BaseApp.mContext)
                .load(pictures)
                .putGear(Luban.THIRD_GEAR)
                .asList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        files -> {
                            ALog.e(Thread.currentThread().getName());
                            mRxManager.add(mModel.postPictures(files)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseSubscriber<BaseResponse<List<OperateBean>>>() {
                                        @Override
                                        public void onSuccess(BaseResponse<List<OperateBean>> response) {
                                            getView().responsePostPicture(response);
                                        }
                                    }));
                        });


    }
}
