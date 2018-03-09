package cn.itsite.amain.yicommunity.main.mine.model;

import cn.itsite.abase.cache.CacheManager;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.UnreadMessageBean;
import cn.itsite.amain.yicommunity.main.mine.contract.MineContract;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块的Model层内容。
 */

public class MineModel extends BaseModel implements MineContract.Model {

    private final String TAG = MineModel.class.getSimpleName();

    @Override
    public Observable<BaseBean> requestLogout(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestLogout(ApiService.requestLogout, params.token)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<String> requestCache() {
        return Observable.create((Observable.OnSubscribe<String>) s ->
                s.onNext(CacheManager.getTotalCacheSize(App.mContext))
        ).subscribeOn(Schedulers.computation());
    }

    @Override
    public Observable<String> requestClearCache() {
        return Observable.create((Observable.OnSubscribe<String>) s -> {
                    CacheManager.clearAllCache(App.mContext);
                    s.onNext(CacheManager.getTotalCacheSize(App.mContext));
                }
        ).subscribeOn(Schedulers.computation());
    }

    @Override
    public Observable<UnreadMessageBean> requestUnreadMark(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestUnreadMark(ApiService.requestUnreadMark, params.token)
                .subscribeOn(Schedulers.io());
    }
}