package cn.itsite.amain.yicommunity.main.steward.model;


import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.acommon.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.ContactBean;
import cn.itsite.amain.yicommunity.entity.bean.DoorListBean;
import cn.itsite.amain.yicommunity.entity.bean.HouseInfoBean;
import cn.itsite.amain.yicommunity.entity.bean.MyHousesBean;
import cn.itsite.amain.yicommunity.main.steward.contract.StewardContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责管家模块的Model层内容。
 */

public class StewardModel extends BaseModel implements StewardContract.Model {
    private final String TAG = StewardModel.class.getSimpleName();

    @Override
    public void start(Object request) {
        ALog.e("start");
    }

    @Override
    public Observable<ContactBean> requestContact(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestContact(ApiService.CONTACT,
                        params.token,
                        params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MyHousesBean> requestHouses(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestMyhouses(ApiService.requestMyhouses,
                        params.token,
                        params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<DoorListBean> requestDoors(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDoors(ApiService.requestDoors,
                        params.token,
                        params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestCheckPermission(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestCheckPermission(
                        ApiService.requestCheckPermission,
                        params.token,
                        params.dir,
                        params.powerCode)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HouseInfoBean> requestHouseInfoList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestHouseInfoList(
                        ApiService.requestHouseInfoList,
                        params.token,
                        params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }
}