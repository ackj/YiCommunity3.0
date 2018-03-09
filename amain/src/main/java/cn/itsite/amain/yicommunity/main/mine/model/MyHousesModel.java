package cn.itsite.amain.yicommunity.main.mine.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.MyHousesBean;
import cn.itsite.amain.yicommunity.main.mine.contract.MyHousesContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/5/17 0017 16:05.
 * Email: liujia95me@126.com
 */

public class MyHousesModel extends BaseModel implements MyHousesContract.Model {
    private static final String TAG = MyHousesModel.class.getSimpleName();

    @Override
    public Observable<MyHousesBean> requsetMyHouse(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestMyhouses(ApiService.requestMyhouses,
                        params.token)
                .subscribeOn(Schedulers.io());
    }
}
