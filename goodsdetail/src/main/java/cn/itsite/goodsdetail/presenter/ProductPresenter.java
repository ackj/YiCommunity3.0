package cn.itsite.goodsdetail.presenter;

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.event.RefreshCartEvent;
import cn.itsite.goodsdetail.ProductDetailBean;
import cn.itsite.goodsdetail.contract.ProductContract;
import cn.itsite.goodsdetail.model.ProductModel;
import cn.itsite.acommon.model.ProductsBean;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/21 0021 17:38
 */

public class ProductPresenter extends BasePresenter<ProductContract.View, ProductContract.Model> implements ProductContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public ProductPresenter(ProductContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected ProductContract.Model createModel() {
        return new ProductModel();
    }

    @Override
    public void getProduct(String uid) {
        mRxManager.add(mModel.getProduct(uid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ProductDetailBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ProductDetailBean> response) {
                        getView().responseGetProduct(response.getData());
                    }
                }));
    }

    @Override
    public void postProduct(String cartUid, ProductsBean bean) {
        mRxManager.add(mModel.postProducts(cartUid, bean)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        EventBus.getDefault().post(new RefreshCartEvent());
                        getView().responsePostSuccess(response);
                    }
                }));
    }
}
