package cn.itsite.amain.yicommunity.main.sociality.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.CommunityBean;
import cn.itsite.amain.yicommunity.main.sociality.contract.SocialityContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/9/5 0005 17:33.
 * Email: liujia95me@126.com
 */

public class SocialityModel extends BaseModel implements SocialityContract.Model {
    private final String TAG = SocialityModel.class.getSimpleName();

    @Override
    public Observable<CommunityBean> requestCommunitys(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestCommunityList(ApiService.requestCommunityList, params.token)
                .subscribeOn(Schedulers.io());
    }
}
