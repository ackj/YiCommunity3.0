package cn.itsite.amain.yicommunity.main.house.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.HouseRightsBean;
import cn.itsite.amain.yicommunity.main.house.contract.MemberPermissionContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责房屋模块的Model层内容。
 */

public class MemberPermissionModel extends BaseModel implements MemberPermissionContract.Model {
    private final String TAG = MemberPermissionModel.class.getSimpleName();


    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<HouseRightsBean> requestRights(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestPermission(ApiService.requestPermission
                        , params.token
                        , params.fid)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestUpdateRights(Params params) {

        return HttpHelper.getService(ApiService.class)
                .requestUpdatePermission(params.url
                        , params.token
                        , params.mfid
                        , params.rfid
                        , params.picode
                        , params.status)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDeleteMember(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDeleteMember(ApiService.requestDeleteMember
                        , params.token
                        , params.fid
                        , params.identityType
                        , params.mfid)
                .subscribeOn(Schedulers.io());
    }
}