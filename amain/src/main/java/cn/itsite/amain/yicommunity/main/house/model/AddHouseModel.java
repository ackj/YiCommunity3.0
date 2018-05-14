package cn.itsite.amain.yicommunity.main.house.model;


import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.BuildingBean;
import cn.itsite.amain.yicommunity.entity.bean.CommunitySelectBean;
import cn.itsite.amain.yicommunity.entity.bean.FloorBean;
import cn.itsite.amain.yicommunity.entity.bean.RoomBean;
import cn.itsite.amain.yicommunity.entity.bean.UnitBean;
import cn.itsite.amain.yicommunity.main.house.contract.AddHouseContract;
import okhttp3.MultipartBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责房屋模块的Model层内容。
 */

public class AddHouseModel extends BaseModel implements AddHouseContract.Model {
    private final String TAG = AddHouseModel.class.getSimpleName();

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<CommunitySelectBean> requestCommunitys(Params params) {
        ALog.e(params.sc);
        ALog.e(params.page);
        ALog.e(params.pageSize);
        ALog.e(params.province);
        ALog.e(params.city);
        ALog.e(params.county);

        return HttpHelper.getService(ApiService.class).requestCommunitys(ApiService.requestCommunitys
                , params.sc
                , params.page + ""
                , params.pageSize + ""
                , params.province
                , params.city
                , params.county)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BuildingBean> requestBuildings(Params params) {
        return HttpHelper.getService(ApiService.class).requestBuildings(ApiService.requestBuildings
                , params.sc
                , params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<UnitBean> requestUnits(Params params) {
        return HttpHelper.getService(ApiService.class).requestUnits(ApiService.requestUnits
                , params.sc
                , params.cmnt_c
                , params.bdg_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<FloorBean> requestFloors(Params params) {
        return HttpHelper.getService(ApiService.class).requestFloors(ApiService.requestFloors
                , params.sc
                , params.cmnt_c
                , params.bdg_c
                , params.bdg_u_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<RoomBean> requestRooms(Params params) {
        ALog.e(params.sc);
        ALog.e(params.cmnt_c);
        ALog.e(params.bdg_c);
        ALog.e(params.bdg_u_c);
        ALog.e(params.bdg_f_c);

        return HttpHelper.getService(ApiService.class).requestRooms(ApiService.requestRooms
                , params.sc
                , params.cmnt_c
                , params.bdg_c
                , params.bdg_u_c
                , params.bdg_f_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestApply(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", params.token);
        builder.addFormDataPart("cmnt_c", params.cmnt_c);
        builder.addFormDataPart("bdg_c", params.bdg_c);
        builder.addFormDataPart("bdg_u_c", params.bdg_u_c);
        builder.addFormDataPart("bdg_f_c", params.bdg_f_c);
        builder.addFormDataPart("bdg_f_h_c", params.bdg_f_h_c);
        builder.addFormDataPart("applyName", params.name);
        builder.addFormDataPart("idNO", params.idCard);
        builder.addFormDataPart("certificateType", params.certificateType);
        return HttpHelper.getService(ApiService.class).requestApply(apply(params.residentType)
                , builder.build())
                .subscribeOn(Schedulers.io());
    }

    String apply(int type) {
        switch (type) {
            case 1:
                return ApiService.ownerApply;
            case 2:
                return ApiService.relativeApply;
            case 3:
                return ApiService.fmApply;
            default:
                return null;
        }
    }
}
