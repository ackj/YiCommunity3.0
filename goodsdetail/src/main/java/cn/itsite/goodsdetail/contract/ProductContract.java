package cn.itsite.goodsdetail.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.goodsdetail.ProductDetailBean;
import cn.itsite.acommon.model.ProductsBean;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/21 0021 17:38
 */

public interface ProductContract {
    interface View extends BaseContract.View{
        void responseGetProduct(ProductDetailBean bean);
        void responsePostSuccess(BaseResponse response);
    }

    interface Presenter extends BaseContract.Presenter {
        void getProduct(String uid);
        void postProduct(String cartUid, ProductsBean bean);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseResponse<ProductDetailBean>> getProduct(String uid);
        Observable<BaseResponse> postProducts(String cartUid,ProductsBean bean);
    }
}
