package cn.itsite.amain.s1.host.model;


import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.s1.common.ApiService;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.HostSettingsBean;
import cn.itsite.amain.s1.host.contract.HostSettingsContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责设置主机模块的Model层内容。
 */

public class HostSettingsModel extends BaseModel implements HostSettingsContract.Model {
    private final String TAG = HostSettingsModel.class.getSimpleName();

    @Override
    public Observable<BaseBean> requestSetHost(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestHostConfig(ApiService.requestHostConfig,
                        params.deviceSn,
                        params.token,
                        Constants.FC,
                        params.type,
                        params.subType,
                        params.val)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HostSettingsBean> requestHostSettings(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestHostSettings(ApiService.requestHostSettings,
                        params.deviceSn,
                        params.token,
                        Constants.FC,
                        params.type)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestGatewayTest(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestGatewayTest(ApiService.requestGatewayTest,
                        params.token,
                        Constants.FC,
                        params.deviceSn,
                        params.status)
                .subscribeOn(Schedulers.io());
    }
}