package cn.itsite.amain.yicommunity.main.services.model;


import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.ServicesListBean;
import cn.itsite.amain.yicommunity.main.services.contract.ServicesContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 社区服务模块的Model层内容。
 */

public class ServicesModel extends BaseModel implements ServicesContract.Model {
    private final String TAG = ServicesModel.class.getSimpleName();

    @Override
    public void start(Object request) {
    }

    @Override
    public Observable<ServicesListBean> requestServiceCommodityList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestServiceCommodityList(ApiService.requestServiceCommodityList,
                        params.page,
                        params.pageSize,
                        params.fid)
                .subscribeOn(Schedulers.io());
    }
}