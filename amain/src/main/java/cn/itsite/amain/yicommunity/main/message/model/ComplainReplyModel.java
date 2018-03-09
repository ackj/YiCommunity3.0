package cn.itsite.amain.yicommunity.main.message.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.ComplainReplyBean;
import cn.itsite.amain.yicommunity.main.message.contract.ComplainReplyContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/18 0018 18:03.
 * Email: liujia95me@126.com
 */

public class ComplainReplyModel extends BaseModel implements ComplainReplyContract.Model {

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<ComplainReplyBean> requestComplainReplies(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestComplainReplies(ApiService.requestComplainReplies,
                        params.token,
                        params.complaintFid)
                .subscribeOn(Schedulers.io());
    }
}
