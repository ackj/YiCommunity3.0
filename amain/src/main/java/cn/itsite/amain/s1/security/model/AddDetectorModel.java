package cn.itsite.amain.s1.security.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.s1.common.ApiService;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.DevicesBean;
import cn.itsite.amain.s1.security.contract.AddDetectorContract;
import okhttp3.MultipartBody;
import rx.Observable;
import rx.schedulers.Schedulers;

public class AddDetectorModel extends BaseModel implements AddDetectorContract.Model {
    private static final String TAG = AddDetectorModel.class.getSimpleName();

    @Override
    public Observable<DevicesBean> requestDetectorList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSensorTypeList(ApiService.requestSensorTypeList,
                        params.token,
                        Constants.FC,
                        params.page,
                        params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestAddDetector(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", params.token);
        builder.addFormDataPart("fc", Constants.FC);
        builder.addFormDataPart("sensorType", params.sensorType);
        builder.addFormDataPart("name", params.name);
        builder.addFormDataPart("gateway", params.deviceSn);
        return HttpHelper.getService(ApiService.class)
                .reqeuestNewsensor(ApiService.reqeuestNewsensor,
                        builder.build())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> reqeuestCancellationOfSensorLearning(Params params) {
        return HttpHelper.getService(ApiService.class)
                .reqeuestCancellationOfSensorLearning(ApiService.reqeuestCancellationOfSensorLearning,
                        params.token, Constants.FC, params.deviceSn)
                .subscribeOn(Schedulers.io());
    }
}