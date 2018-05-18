package cn.itsite.amain.yicommunity.main.mine.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.bean.UserInfoBean;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.main.mine.contract.MineContract;
import cn.itsite.amain.yicommunity.main.mine.model.MineModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块Presenter层内容。
 */

public class MinePresenter extends BasePresenter<MineContract.View, MineContract.Model> implements MineContract.Presenter {
    private final String TAG = MinePresenter.class.getSimpleName();

    public MinePresenter(MineContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected MineContract.Model createModel() {
        return new MineModel();
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

    @Override
    public void requestCache() {
        mRxManager.add(mModel.requestCache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (isViewAttached()) {
                        getView().responseCache(s);
                    }
                }, this::error)
        );
    }

    @Override
    public void requestClearCache() {
        mRxManager.add(mModel.requestClearCache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (isViewAttached()) {
                        getView().responseCache(s);
                    }

                }, this::error)
        );
    }

    @Override
    public void requestUnreadMark(Params params) {
        mRxManager.add(mModel.requestUnreadMark(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseUnreadMark(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error)
        );
    }

    @Override
    public void requestInfo(String token) {
        mRxManager.add(mModel.requestInfo(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseOldSubscriber<BaseOldResponse<UserInfoBean.MemberInfoBean>>() {
                    @Override
                    public void onSuccess(BaseOldResponse<UserInfoBean.MemberInfoBean> response) {
                        getView().responseInfo(response);
                    }
                }));
    }
}