package cn.itsite.amain.s1.history.model;

import cn.itsite.abase.mvp.model.base.BaseModel;

import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.common.ApiService;
import cn.itsite.amain.s1.entity.bean.DeviceLogBean;
import cn.itsite.amain.s1.history.contract.DeviceLogsContract;

import rx.Observable;
import rx.schedulers.Schedulers;


public class DeviceLogsModel extends BaseModel implements DeviceLogsContract.Model {
    @Override
    public void start(Object request) {

    }

	@Override
    public Observable<DeviceLogBean> requestDeviceLogs(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDeviceLogs(ApiService.requestDeviceLogs,params.token, Constants.FC,params.page,params.pageSize,params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

}