package cn.itsite.amain.s1.more.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.more.contract.MoreContract;
import cn.itsite.amain.s1.more.model.MoreModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块Presenter层内容。
 */

public class MorePresenter extends BasePresenter<MoreContract.View, MoreContract.Model> implements MoreContract.Presenter {
    private final String TAG = MorePresenter.class.getSimpleName();

    public MorePresenter(MoreContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected MoreContract.Model createModel() {
        return new MoreModel();
    }

    @Override
    public void start(Object request) {
    }

    @Override
    public void requestLogout(Params params) {
        mRxManager.add(mModel.requestLogout(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseLogout(baseBean.getOther().getMessage());
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error)
        );
    }
}