package cn.itsite.amain.s1.room.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.s1.common.ApiService;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.DevicesBean;
import cn.itsite.amain.s1.room.contract.DeviceTypeContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/8/30 0030 20:06.
 * Email: liujia95me@126.com
 */

public class DeviceTypeModel extends BaseModel implements DeviceTypeContract.Model {

    @Override
    public Observable<DevicesBean> requestDeviceType(Params params) {
        return HttpHelper.getService(ApiService.class).requestCtrlSDeviceTypeList(ApiService.requestCtrlSDeviceTypeList, Constants.FC)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestAddDevice(Params params) {
        return HttpHelper.getService(ApiService.class).requestNewDevice(ApiService.requestNewDevice
                , params.token, Constants.FC, params.deviceType, params.name, params.roomFid, params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestAddCamera(Params params) {
        return HttpHelper.getService(ApiService.class).requestNewCamera(ApiService.requestNewDevice
                , params.token, Constants.FC, params.deviceType, params.name, params.roomFid, params.deviceId, params.devicePassword)
                .subscribeOn(Schedulers.io());
    }
}
