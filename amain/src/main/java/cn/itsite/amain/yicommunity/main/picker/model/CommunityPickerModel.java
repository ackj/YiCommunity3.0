package cn.itsite.amain.yicommunity.main.picker.model;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.CommunitySelectBean;
import cn.itsite.amain.yicommunity.main.picker.contract.CommunityPickerContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/5/8 0008 11:35.
 * Email: liujia95me@126.com
 */

public class CommunityPickerModel extends BaseModel implements CommunityPickerContract.Model {

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<CommunitySelectBean> requestCommunitys(Params params) {
        ALog.e(params.sc);
        ALog.e(params.page + "");
        ALog.e(params.pageSize + "");
        ALog.e(params.province);
        ALog.e(params.county);
        ALog.e(params.city);

        return HttpHelper.getService(ApiService.class)
                .requestCommunitys(ApiService.requestCommunitys
                        , params.sc
                        , params.page + ""
                        , params.pageSize + ""
                        , params.province
                        , params.city
                        , params.county)
                .subscribeOn(Schedulers.io());
    }
}
