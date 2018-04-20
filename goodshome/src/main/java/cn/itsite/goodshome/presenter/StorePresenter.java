package cn.itsite.goodshome.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.goodshome.contract.StoreContract;
import cn.itsite.goodshome.model.ShopBean;
import cn.itsite.goodshome.model.StoreModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/28 0028 15:32
 */

public class StorePresenter extends BasePresenter<StoreContract.View,StoreContract.Model> implements StoreContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public StorePresenter(StoreContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected StoreContract.Model createModel() {
        return new StoreModel();
    }

    @Override
    public void getStore(GoodsParams goodsParams) {
        mRxManager.add(mModel.getStore(goodsParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<ShopBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<ShopBean>> listBaseResponse) {
                        getView().responseGetStore(listBaseResponse.getData());
                    }
                }));
    }
}
