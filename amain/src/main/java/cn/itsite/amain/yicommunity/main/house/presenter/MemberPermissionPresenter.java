package cn.itsite.amain.yicommunity.main.house.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.main.house.contract.MemberPermissionContract;
import cn.itsite.amain.yicommunity.main.house.model.MemberPermissionModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责房屋模块Presenter层内容。
 */

public class MemberPermissionPresenter extends BasePresenter<MemberPermissionContract.View, MemberPermissionContract.Model> implements MemberPermissionContract.Presenter {
    private final String TAG = MemberPermissionPresenter.class.getSimpleName();

    public MemberPermissionPresenter(MemberPermissionContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected MemberPermissionContract.Model createModel() {
        return new MemberPermissionModel();
    }

    @Override
    public void start(Object request) {

    }

    @Override
    public void requestRights(Params params) {
        mRxManager.add(mModel.requestRights(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseRights(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error)
        );
    }

    @Override
    public void requestUpdateRights(Params params) {
        mRxManager.add(mModel.requestUpdateRights(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseUpdateRights(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error)
        );
    }

    @Override
    public void requestDeleteMember(Params params) {
        mRxManager.add(mModel.requestDeleteMember(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDeleteMember(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error)
        );
    }
}