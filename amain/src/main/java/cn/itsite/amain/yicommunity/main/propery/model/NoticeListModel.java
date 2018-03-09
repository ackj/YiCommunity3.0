package cn.itsite.amain.yicommunity.main.propery.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.NoticeBean;
import cn.itsite.amain.yicommunity.main.propery.contract.NoticeListContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/5/9 0009 22:34.
 * Email: liujia95me@126.com
 * <p>
 * Modify by leguang in 2017.05.11
 */

public class NoticeListModel extends BaseModel implements NoticeListContract.Model {


    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<NoticeBean> requestNotices(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNotices(
                        ApiService.requestNotices,
                        params.token,
                        params.cmnt_c,
                        params.page + "",
                        params.pageSize + "",
                        params.summerable,
                        params.timeable)
                .subscribeOn(Schedulers.io());
    }
}
