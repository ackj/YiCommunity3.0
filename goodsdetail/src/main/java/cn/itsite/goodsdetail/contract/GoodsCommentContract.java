package cn.itsite.goodsdetail.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.goodsdetail.model.EvaluatesBean;
import rx.Observable;

/**
 * Created by liujia on 2018/4/22.
 */

public interface GoodsCommentContract  {

    interface View extends BaseContract.View{
        void responseGetComments(List<EvaluatesBean> data);
    }

    interface Presenter extends BaseContract.Presenter {
        void getComments(GoodsParams params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseResponse<List<EvaluatesBean>>> getComments(GoodsParams params);
    }
}
