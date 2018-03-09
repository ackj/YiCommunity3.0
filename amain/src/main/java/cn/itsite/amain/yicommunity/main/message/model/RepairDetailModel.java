package cn.itsite.amain.yicommunity.main.message.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.RepairDetailBean;
import cn.itsite.amain.yicommunity.main.message.contract.RepairDetailContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/19 0019 09:38.
 * Email: liujia95me@126.com
 */

public class RepairDetailModel extends BaseModel implements RepairDetailContract.Model {

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<RepairDetailBean> requestRepairDetail(Params params) {
        return HttpHelper.getService(ApiService.class).requestRepairDetail(ApiService.requestRepairDetail,
                params.token, params.fid)
                .subscribeOn(Schedulers.io());
    }
}
