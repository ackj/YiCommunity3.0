package cn.itsite.shoppingcart.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.acommon.OperatorBean;
import cn.itsite.acommon.model.ProductsBean;
import cn.itsite.shoppingcart.RecommendGoodsBean;
import cn.itsite.shoppingcart.StoreBean;
import cn.itsite.shoppingcart.StorePojo;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/12 0012 15:07
 */

public interface CartContract {

    interface View extends BaseContract.View{
        void responseDeleteSuccess(BaseResponse response);
        void responsePostSuccess(  BaseResponse response);
        void responsePutSuccess(   BaseResponse response);
        void responseGetCartsSuccess(List<StoreBean> data);
        void responseRecommendGoodsSuccess(List<StoreBean> data);
    }

    interface Presenter extends BaseContract.Presenter {
        void deleteProduct(String cartsUID,List<OperatorBean> list);
        void postProduct(String cartsUID,String productUID);
        void putProduct(String cartsUID, ProductsBean bean);
        void getCarts(String cartsUID);
        void getRecommendGoods(GoodsParams goodsParams);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseResponse> deleteProduct(String shopUID, List<OperatorBean> list);
        Observable<BaseResponse> postProduct(String shopUID, String productUID);
        Observable<BaseResponse> putProduct(String shopUID, ProductsBean bean);
        Observable<BaseResponse<List<StorePojo>>> getCarts(String shopUID);
        Observable<BaseResponse<List<RecommendGoodsBean>>> getRecommendGoods(GoodsParams goodsParams);
    }
}
