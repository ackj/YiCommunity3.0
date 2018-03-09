package cn.itsite.amain.yicommunity.main.services.presenter;


import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.main.services.contract.RemarkContract;
import cn.itsite.amain.yicommunity.main.services.model.RemarkModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 社区服务Presenter层内容。
 */

public class RemarkPresenter extends BasePresenter<RemarkContract.View, RemarkContract.Model> implements RemarkContract.Presenter {
    private final String TAG = RemarkPresenter.class.getSimpleName();

    public RemarkPresenter(RemarkContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected RemarkContract.Model createModel() {
        return new RemarkModel();
    }

    @Override
    public void start(Object request) {
        mRxManager.add(mModel.requestRemarkList((Params) request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(remarkListBean -> {
                    if (remarkListBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().start(remarkListBean);
                    } else {
                        getView().error(remarkListBean.getOther().getMessage());
                    }
                }, this::error));
    }
}