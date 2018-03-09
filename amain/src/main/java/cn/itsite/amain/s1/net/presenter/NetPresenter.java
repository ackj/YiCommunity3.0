package cn.itsite.amain.s1.net.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;


import cn.itsite.amain.s1.net.contract.NetContract;
import cn.itsite.amain.s1.net.model.NetModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 */

public class NetPresenter extends BasePresenter<NetContract.View, NetContract.Model> implements NetContract.Presenter {
    private static final String TAG = NetPresenter.class.getSimpleName();

    public NetPresenter(NetContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected NetContract.Model createModel() {
        return new NetModel();
    }

    @Override
    public void command(int cmd, String params) {
        mModel.command(cmd, params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void _onNext(String s) {
                        getView().command(cmd, s);
                    }
                });
    }
}