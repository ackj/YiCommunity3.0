package cn.itsite.goodssearch.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.goodssearch.model.GoodsBean;
import cn.itsite.goodssearch.model.KeywordBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/5/11 0011 16:34.
 * Email: liujia95me@126.com
 */

public interface KeywordsContract {

    interface View extends BaseContract.View {
        void responseGetKeywords(List<KeywordBean> data);
        void responseGetHotKeywords(List<KeywordBean> data);
        void responseGetProducts(List<GoodsBean> data);
    }

    interface Presenter extends BaseContract.Presenter {
        void getKeywords(GoodsParams goodsParams);

        void getProducts(GoodsParams goodsParams);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseResponse<List<KeywordBean>>> getKeywords(GoodsParams goodsParams);

        Observable<BaseResponse<List<GoodsBean>>> getProducts(GoodsParams goodsParams);
    }
}
