package cn.itsite.amain.yicommunity.main.parking.model;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.ParkingChargeBean;
import cn.itsite.amain.yicommunity.entity.db.PlateHistoryData;
import cn.itsite.amain.yicommunity.main.parking.contract.TempParkContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块Model。
 */
public class TempParkModel extends BaseModel implements TempParkContract.Model {

    @Override
    public Observable<ParkingChargeBean> requestParkingCharge(Params params) {
        return HttpHelper.getService(ApiService.class).
                requestParkingCharge(ApiService.requestParkingCharge,
                        params.token,
                        params.parkPlaceFid,
                        params.carNo)
                .subscribeOn(Schedulers.io());
    }

//    @Override
//    public Observable<ResponseBody> requestTempParkBill(Params params) {
//        return HttpHelper.getService(ApiService.class)
//                .requestTempParkBill(ApiService.requestTempParkBill,
//                        params.parkPlaceFid,
//                        params.carNo,
//                        params.payMethod)
//                .subscribeOn(Schedulers.io());
//    }

    @Override
    public Observable<List<PlateHistoryData>> requestPlateHistory() {
        return Observable.create((Observable.OnSubscribe<List<PlateHistoryData>>) subscriber -> {
            subscriber.onStart();
            try {
                List<PlateHistoryData> plates = DataSupport.order("id desc").find(PlateHistoryData.class);
                subscriber.onNext(plates);
            } catch (Exception e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        }).observeOn(Schedulers.computation());
    }
}