package cn.itsite.amain.s1.scene.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.s1.common.ApiService;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.scene.contract.AddSceneContract;
import rx.Observable;
import rx.schedulers.Schedulers;


public class AddSceneModel extends BaseModel implements AddSceneContract.Model {


    @Override
    public Observable<BaseBean> requestAddScene(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestAddScene(ApiService.requestAddScene,
                        params.token,
                        Constants.FC,
                        params.name,
                        params.paramJson,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }
}