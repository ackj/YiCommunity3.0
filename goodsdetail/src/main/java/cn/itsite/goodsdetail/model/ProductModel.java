package cn.itsite.goodsdetail.model;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.model.ProductsBean;
import cn.itsite.goodsdetail.ProductDetailBean;
import cn.itsite.goodsdetail.contract.ProductContract;
import cn.itsite.goodsdetail.contract.ProductService;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/21 0021 17:38
 */

public class ProductModel extends BaseModel implements ProductContract.Model{

    @Override
    public Observable<BaseResponse<ProductDetailBean>> getProduct(String uid) {
        return HttpHelper.getService(ProductService.class)
                .getProduct(uid)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse> postProducts(String cartUid, ProductsBean bean) {
        BaseRequest request = new BaseRequest();
        request.message = "上传这几个商品到购物车";
        List<ProductsBean> list = new ArrayList<>();
        list.add(bean);
        request.data = list;
        return HttpHelper.getService(ProductService.class)
                .postProducts(cartUid,request)
                .subscribeOn(Schedulers.io());
    }

}
