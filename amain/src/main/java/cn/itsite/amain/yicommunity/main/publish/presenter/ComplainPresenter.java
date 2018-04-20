package cn.itsite.amain.yicommunity.main.publish.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.common.luban.Luban;
import cn.itsite.amain.yicommunity.main.publish.contract.PublishContract;
import cn.itsite.amain.yicommunity.main.publish.model.ComplainModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块Presenter层内容。
 */

public class ComplainPresenter extends BasePresenter<PublishContract.View, PublishContract.Model> implements PublishContract.Presenter {
    private final String TAG = ComplainPresenter.class.getSimpleName();

    public ComplainPresenter(PublishContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected PublishContract.Model createModel() {
        return new ComplainModel();
    }

    @Override
    public void requestSubmit(Params params) {
        if (params.type == 1) {
            compress(params);
        } else {
            beginPost(params);
        }
    }

    public void compress(Params params) {
        for (int i = 0; i < params.files.size(); i++) {
            ALog.d(TAG, params.files.get(i).getAbsoluteFile() + " --- length----" + params.files.get(i).length());
        }
        Luban.get(App.mContext)
                .load(params.files)
                .putGear(Luban.THIRD_GEAR)
                .asList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        files -> {
                            ALog.e(Thread.currentThread().getName());
                            for (int i = 0; i < files.size(); i++) {
                                ALog.d(TAG, files.get(i).getAbsoluteFile() + " --- length----" + files.get(i).length());
                            }
                            params.files = files;
                            beginPost(params);
                        });
    }

    private void beginPost(Params params) {
        mRxManager.add(mModel.requestSubmit(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == 200) {
                        getView().responseSuccess(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void start(Object request) {

    }
}