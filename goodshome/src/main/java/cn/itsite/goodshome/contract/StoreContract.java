package cn.itsite.goodshome.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.goodshome.model.ShopBean;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/28 0028 11:33
 */

public interface StoreContract {

    interface View extends BaseContract.View{
        void responseGetStore(List<ShopBean> list);
    }

    interface Presenter extends BaseContract.Presenter {
        void getStore(GoodsParams goodsParams);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseResponse<List<ShopBean>>> getStore(GoodsParams goodsParams);
    }
}
