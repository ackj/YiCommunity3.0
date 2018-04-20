package cn.itsite.amain.s1.scene.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.scene.contract.AddSceneContract;
import cn.itsite.amain.s1.scene.model.AddSceneModel;
import rx.android.schedulers.AndroidSchedulers;

public class AddScenePresenter extends BasePresenter<AddSceneContract.View, AddSceneContract.Model> implements AddSceneContract.Presenter {
    private final String TAG = AddScenePresenter.class.getSimpleName();

    public AddScenePresenter(AddSceneContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected AddSceneContract.Model createModel() {
        return new AddSceneModel();
    }

    @Override
    public void requestAddScene(Params params) {
        mRxManager.add(mModel.requestAddScene(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseAddScene(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error/*, this::complete, disposable -> start(null)*/)
        );
    }
}