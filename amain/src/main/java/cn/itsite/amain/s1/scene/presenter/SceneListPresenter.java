package cn.itsite.amain.s1.scene.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.scene.contract.SceneListContract;
import cn.itsite.amain.s1.scene.model.SceneListModel;
import rx.android.schedulers.AndroidSchedulers;


public class SceneListPresenter extends BasePresenter<SceneListContract.View, SceneListContract.Model> implements SceneListContract.Presenter {
    private final String TAG = SceneListPresenter.class.getSimpleName();

    public SceneListPresenter(SceneListContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected SceneListContract.Model createModel() {
        return new SceneListModel();
    }

    @Override
    public void requestSceneList(Params params) {
        mRxManager.add(mModel.requestSceneList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseSceneList(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestStartScene(Params params) {
        mRxManager.add(mModel.requestStartScene(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseStartScene(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/)
        );
    }

    @Override
    public void requestDeleteScene(Params params) {
        mRxManager.add(mModel.requestDeleteScene(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDeleteScene(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/)
        );
    }
}