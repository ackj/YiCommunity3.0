package cn.itsite.amain.yicommunity.main.smarthome.model;


import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.FirstLevelBean;
import cn.itsite.amain.yicommunity.entity.bean.GoodsBean;
import cn.itsite.amain.yicommunity.entity.bean.SubCategoryBean;
import cn.itsite.amain.yicommunity.main.smarthome.contract.SmartHomeMallContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/5/22 0022 09:07.
 * Email: liujia95me@126.com
 */

public class SmartHomeMallModel extends BaseModel implements SmartHomeMallContract.Model {
    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<SubCategoryBean> requestSubCategoryList(Params params) {
        return HttpHelper.getService(ApiService.class).requestSubCategoryLevel(
                ApiService.requestSubCategoryLevel, params.token, params.appType, params.id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<GoodsBean> requestGoodsList(Params params) {
        return HttpHelper.getService(ApiService.class).requestGoodsList(
                ApiService.requestGoodsList, params.token, params.appType, params.secondCategoryId, Constants.payFrom, Constants.fromPoint)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<FirstLevelBean> requestFirstLevel(Params params) {
        return HttpHelper.getService(ApiService.class).requestFirstLevel(ApiService.requestFirstLevel, params.keywords, Constants.payFrom, Constants.fromPoint)
                .subscribeOn(Schedulers.io());
    }
}
