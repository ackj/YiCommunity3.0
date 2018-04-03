package cn.itsite.goodssearch.model;

import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.goodssearch.contract.KeywordService;
import cn.itsite.goodssearch.contract.KeywordsContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/14 0014 16:52
 */

public class KeywordsModel extends BaseModel implements KeywordsContract.Model{

    @Override
    public Observable<BaseResponse<List<KeywordBean>>> getKeywords(GoodsParams goodsParams) {
        return HttpHelper.getService(KeywordService.class)
                .getKeywords(goodsParams.toString())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse<List<GoodsBean>>> getProducts(GoodsParams goodsParams) {
        return HttpHelper.getService(KeywordService.class)
                .getProducts(goodsParams.toString())
                .subscribeOn(Schedulers.io());
    }
}
