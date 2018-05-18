package cn.itsite.amain.yicommunity.main.smarthome.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.FirstLevelBean;
import cn.itsite.amain.yicommunity.main.smarthome.contract.GoodsCategoryContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/5/22 0022 10:22.
 * Email: liujia95me@126.com
 */

public class GoodsCategoryModel extends BaseModel implements GoodsCategoryContract.Model {

    @Override
    public Observable<FirstLevelBean> requestFirstLevel(Params params) {
        return HttpHelper.getService(ApiService.class).requestFirstLevel(ApiService.requestFirstLevel, params.keywords,
                2,
                Constants.fromPoint)
                .subscribeOn(Schedulers.io());
    }
}
