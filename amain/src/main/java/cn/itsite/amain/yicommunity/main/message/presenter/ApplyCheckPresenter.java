package cn.itsite.amain.yicommunity.main.message.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.main.message.contract.ApplyCheckContract;
import cn.itsite.amain.yicommunity.main.message.model.ApplyCheckModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/5/26 0026 17:13.
 * Email: liujia95me@126.com
 */

public class ApplyCheckPresenter extends BasePresenter<ApplyCheckContract.View, ApplyCheckContract.Model> implements ApplyCheckContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public ApplyCheckPresenter(ApplyCheckContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected ApplyCheckContract.Model createModel() {
        return new ApplyCheckModel();
    }

    @Override
    public void start(Object request) {

    }

    @Override
    public void requestApplyCheck(Params params) {
        mRxManager.add(mModel.requestApplyCheck(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == 200) {
                        getView().responseApplySuccess(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error));
    }
}
