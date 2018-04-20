package cn.itsite.amain.yicommunity.main.parking.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.CarportBeam;
import cn.itsite.amain.yicommunity.main.parking.contract.CarportContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车位模块Model。
 */
public class CarportModel extends BaseModel implements CarportContract.Model {
    public static final String TAG = CarportModel.class.getSimpleName();

    @Override
    public Observable<CarportBeam> requestCarports(Params params) {
        return HttpHelper.getService(ApiService.class).
                requestCarports(ApiService.requestCarports,
                        params.parkPlaceFid)
                .subscribeOn(Schedulers.io());
    }
}