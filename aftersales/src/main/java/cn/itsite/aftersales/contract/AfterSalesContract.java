package cn.itsite.aftersales.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.GoodsParams;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/17 0017 17:31
 */

public interface AfterSalesContract {

    interface View extends BaseContract.View{
        void responsePostSuccess(BaseResponse response);
    }

    interface Presenter extends BaseContract.Presenter {
        void postApply(GoodsParams goodsParams);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseResponse> postApply(GoodsParams goodsParams);
    }
}
