package cn.itsite.amain.s1.scene.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.s1.common.ApiService;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.SceneBean;
import cn.itsite.amain.s1.scene.contract.SceneListContract;
import rx.Observable;
import rx.schedulers.Schedulers;


public class SceneListModel extends BaseModel implements SceneListContract.Model {

    @Override
    public Observable<SceneBean> requestSceneList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSceneList(ApiService.requestSceneList,
                        params.pageSize,
                        params.page,
                        params.token,
                        Constants.FC,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestStartScene(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestStartScene(ApiService.requestStartScene,
                        params.token,
                        Constants.FC,
                        params.index,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDeleteScene(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDeleteScene(ApiService.requestDeleteScene,
                        params.token,
                        Constants.FC,
                        params.index,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }
}