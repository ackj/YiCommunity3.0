package cn.itsite.amain.s1.host.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.host.contract.AddHostContract;
import cn.itsite.amain.s1.host.model.AddHostModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责添加主机模块Presenter层内容。
 */

public class AddHostPresenter extends BasePresenter<AddHostContract.View, AddHostContract.Model> implements AddHostContract.Presenter {
    private final String TAG = AddHostPresenter.class.getSimpleName();

    public AddHostPresenter(AddHostContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected AddHostContract.Model createModel() {
        return new AddHostModel();
    }

    @Override
    public void requestAddHost(Params params) {
        mRxManager.add(mModel.requestAddHost(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == 200) {
                        getView().responseAddHost(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestModGateway(Params params) {
        mRxManager.add(mModel.requestModGateway(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == 200) {
                        getView().responseModSuccess(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error));
    }

}