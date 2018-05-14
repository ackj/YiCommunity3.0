package cn.itsite.amain.s1.host.model;


import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.s1.common.ApiService;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.host.contract.AddHostContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责添加主机模块的Model层内容。
 */

public class AddHostModel extends BaseModel implements AddHostContract.Model {

    private final String TAG = AddHostModel.class.getSimpleName();

    @Override
    public Observable<BaseBean> requestAddHost(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNewMainDevice(ApiService.requestNewMainDevice,
                        params.token,
                        Constants.SMART_GATEWAY,
                        params.deviceSn,
                        params.name,
                        params.roomDir,
                        "")
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestModGateway(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestModMainDevice(ApiService.requestAddHost,
                        params.token,
                        Constants.SMART_GATEWAY,
                        params.deviceSn,
                        params.name,
                        "")
                .subscribeOn(Schedulers.io());
    }

}