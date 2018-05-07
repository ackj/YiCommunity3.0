package cn.itsite.goodsdetail.model;

import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.goodsdetail.contract.GoodsCommentContract;
import cn.itsite.goodsdetail.contract.ProductService;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 2018/4/22.
 */

public class GoodsCommentModel extends BaseModel implements GoodsCommentContract.Model {
    @Override
    public Observable<BaseResponse<List<EvaluatesBean>>> getComments(GoodsParams params) {
        return HttpHelper.getService(ProductService.class)
                .getEvaluates(params.toString())
                .subscribeOn(Schedulers.io());
    }
}
