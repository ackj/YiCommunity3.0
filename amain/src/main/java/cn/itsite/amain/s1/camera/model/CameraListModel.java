package cn.itsite.amain.s1.camera.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.s1.camera.contract.CameraListContract;
import cn.itsite.amain.s1.common.ApiService;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.CameraBean;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/9/15 0015 18:18.
 * Email: liujia95me@126.com
 */

public class CameraListModel extends BaseModel implements CameraListContract.Model {

    @Override
    public Observable<CameraBean> requestCameraList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestCameraList(ApiService.requestCameraList, params.token, Constants.FC, params.page, params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestNewCamera(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNewcamera(ApiService.requestNewcamera, params.token, Constants.FC, params.deviceId, params.deviceName, params.devicePassword)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDelCamera(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDelMainDevice(ApiService.requestDelMainDevice, params.token, Constants.SMART_CAMERA, params.deviceId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestModCamera(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestModCamera(ApiService.requestModCamera,
                        params.token, Constants.FC, params.fid, params.deviceType, params.deviceName, params.devicePassword)
                .subscribeOn(Schedulers.io());
    }
}
