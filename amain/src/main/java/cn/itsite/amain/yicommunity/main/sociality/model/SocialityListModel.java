package cn.itsite.amain.yicommunity.main.sociality.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.SocialityListBean;
import cn.itsite.amain.yicommunity.main.sociality.contract.SocialityListContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块的Model层内容。
 */

public class SocialityListModel extends BaseModel implements SocialityListContract.Model {
    private final String TAG = SocialityListModel.class.getSimpleName();

    @Override
    public Observable<SocialityListBean> requestNeighbourList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNeighbourList(ApiService.requestNeighbourList,
                        params.token,
                        params.cmnt_c_en,
                        params.page,
                        params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SocialityListBean> requestExchangeList(Params params) {
        return HttpHelper.getService(ApiService.class).requestExchangeList(ApiService.requestExchangeList, params.page, params.pageSize, params.cmnt_c_en)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SocialityListBean> requestCarpoolList(Params params) {
        return HttpHelper.getService(ApiService.class).requestCarpoolList(ApiService.requestCarpoolList + params.carpoolType,
                params.token, params.cmnt_c, params.currentPositionLat, params.currentPositionLng, params.page, params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SocialityListBean> requestMyNeihbourList(Params params) {
        return HttpHelper.getService(ApiService.class).requestMyNeighbourList(ApiService.requestMyNeighbourList, params.token, params.page, params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SocialityListBean> requestMyExchangeList(Params params) {
        return HttpHelper.getService(ApiService.class).requestMyExchangeList(ApiService.requestMyExchangeList, params.token, params.page, params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SocialityListBean> requestMyCarpoolList(Params params) {
        return HttpHelper.getService(ApiService.class).requestMyCarpoolList(ApiService.requestMyCarpoolList, params.token, params.page, params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestRemoveMyCarpool(Params params) {
        return HttpHelper.getService(ApiService.class).requestRemoveCarpool(ApiService.requestRemoveCarpool, params.token, params.fid)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestRemoveMyExchange(Params params) {
        return HttpHelper.getService(ApiService.class).requestRemoveExchange(ApiService.requestRemoveExchange, params.token, params.fid)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestRemoveMyNeighbour(Params params) {
        return HttpHelper.getService(ApiService.class).requestRemoveNeighbour(ApiService.requestRemoveNeighbour, params.token, params.fid)
                .subscribeOn(Schedulers.io());
    }


}