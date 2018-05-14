package cn.itsite.shoppingcart.model;


import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.acommon.data.bean.OperateBean;
import cn.itsite.acommon.data.pojo.StorePojo;
import cn.itsite.acommon.model.ProductsBean;
import cn.itsite.shoppingcart.RecommendGoodsBean;
import cn.itsite.shoppingcart.RequestBean;
import cn.itsite.shoppingcart.contract.CartContract;
import cn.itsite.shoppingcart.contract.CartService;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/12 0012 17:40
 */

public class CartModel extends BaseModel implements CartContract.Model {

    @Override
    public Observable<BaseResponse> deleteProduct(String shopUID, List<OperateBean> list) {
        BaseRequest request = new BaseRequest();
        request.data = list;
        request.message = "删除这几个商品";
        return HttpHelper.getService(CartService.class)
                .deleteProduct(shopUID, request)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse> postProduct(String shopUID, String productUID) {
        RequestBean bean = new RequestBean();
        RequestBean.DataBean data = new RequestBean.DataBean();
        data.setUid("12312312");
        data.setAmount("12312312");
        bean.setData(data);
        bean.setMessage("hahahah");
        return HttpHelper.getService(CartService.class)
                .postProduct(shopUID, productUID, bean)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse> putProduct(String shopUID, ProductsBean bean) {
        BaseRequest request = new BaseRequest();
        List<ProductsBean> list = new ArrayList<>();
        list.add(bean);
        request.data = list;
        request.message = "修改这几个商品";
        return HttpHelper.getService(CartService.class)
                .putProduct(shopUID, request)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse<List<StorePojo>>> getCarts(String shopUID) {
        return HttpHelper.getService(CartService.class)
                .getCarts(shopUID)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse<List<RecommendGoodsBean>>> getRecommendGoods(GoodsParams goodsParams) {
        return HttpHelper.getService(CartService.class)
                .getRecommendGoods(goodsParams.toString())
                .subscribeOn(Schedulers.io());
    }
}
