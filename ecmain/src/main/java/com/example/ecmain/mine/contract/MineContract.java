package com.example.ecmain.mine.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.login.model.UserInfoBean;
import cn.itsite.order.model.CategoryBean;
import rx.Observable;

/**
 * Created by liujia on 2018/5/14.
 */

public interface MineContract {

    interface View extends BaseContract.View{
        void responseInfo(BaseOldResponse<UserInfoBean.MemberInfoBean> response);
        void responseGetCategories(List<CategoryBean> data);
    }
    interface Presenter extends BaseContract.Presenter {
        void requestInfo(String token);
        void getCategories(GoodsParams goodsParams);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseOldResponse<UserInfoBean.MemberInfoBean>> requestInfo(String token);
        Observable<BaseResponse<List<CategoryBean>>> getCategories(GoodsParams goodsParams);
    }
}
