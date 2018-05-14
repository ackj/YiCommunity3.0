package cn.itsite.amain.yicommunity.main.door.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.DoorListBean;
import cn.itsite.amain.yicommunity.entity.bean.PasswordBean;
import cn.itsite.amain.yicommunity.main.door.contract.PasswordOpenDoorContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责房屋模块的Model层内容。
 */

public class PasswordOpenDoorModel extends BaseModel implements PasswordOpenDoorContract.Model {
    public final String TAG = PasswordOpenDoorModel.class.getSimpleName();

    @Override
    public Observable<DoorListBean> requestDoors(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDoors(ApiService.requestDoors,
                        params.token,
                        params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<PasswordBean> getPassword(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestPassword(ApiService.requestPassword
                        , params.token
                        , params.dir
                        , params.timeset
                        , params.maxTimes)
                .subscribeOn(Schedulers.io());
    }
}