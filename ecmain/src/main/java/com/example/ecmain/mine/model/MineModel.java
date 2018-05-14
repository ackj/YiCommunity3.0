package com.example.ecmain.mine.model;

import com.example.ecmain.mine.contract.MineContract;

import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.login.contract.LoginService;
import cn.itsite.login.model.UserInfoBean;
import cn.itsite.order.contract.OrderService;
import cn.itsite.order.model.CategoryBean;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 2018/5/14.
 */

public class MineModel extends BaseModel implements MineContract.Model {
    @Override
    public Observable<BaseOldResponse<UserInfoBean.MemberInfoBean>> requestInfo(String token) {
        return HttpHelper.getService(LoginService.class)
                .requestInfo(LoginService.requestInfo, token)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse<List<CategoryBean>>> getCategories(GoodsParams goodsParams) {
        return HttpHelper.getService(OrderService.class)
                .getCategories(goodsParams.toString())
                .subscribeOn(Schedulers.io());
    }
}
