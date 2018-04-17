package cn.itsite.aftersales.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.aftersales.contract.AfterSalesContract;
import cn.itsite.aftersales.model.AfterSalesModel;
import rx.android.schedulers.AndroidSchedulers;

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
    public void postApply(GoodsParams goodsParams) {
        mRxManager.add(mModel.postApply(goodsParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>(){
                    @Override
                    public void onSuccess(BaseResponse response) {
                        getView().responsePostSuccess(response);
                    }
                }));
    }
}
