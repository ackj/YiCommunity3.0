package cn.itsite.amain.yicommunity.main.propery.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.RepairApplyBean;
import cn.itsite.amain.yicommunity.main.propery.contract.RepairApplyContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/8 0008 21:31.
 * Email: liujia95me@126.com
 */

public class RepairApplyModel extends BaseModel implements RepairApplyContract.Model {
    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<RepairApplyBean> getRepairApply(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestRepairApply(ApiService.requestRepairApply,
                        params.token,
                        params.cmnt_c,
                        params.pageSize + "",
                        params.page + "")
                .subscribeOn(Schedulers.io());
    }
}
