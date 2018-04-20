package cn.itsite.amain.yicommunity.main.steward.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.main.steward.contract.StewardContract;
import cn.itsite.amain.yicommunity.main.steward.model.StewardModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责管家模块Presenter层内容。
 */

public class StewardPresenter extends BasePresenter<StewardContract.View, StewardContract.Model> implements StewardContract.Presenter {
    private final String TAG = StewardPresenter.class.getSimpleName();

    public StewardPresenter(StewardContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected StewardContract.Model createModel() {
        return new StewardModel();
    }

    @Override
    public void start(Object request) {
        mRxManager.add(mModel.requestHouses((Params) request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseHouses(bean.getData().getAuthBuildings());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error)
        );
    }

    @Override
    public void requestContact(Params params) {
        mRxManager.add(mModel.requestContact(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contactBean -> {
                    if (contactBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        String[] arrayPhones;
                        if (contactBean.getData() != null) {
                            arrayPhones = new String[2];
                            arrayPhones[0] = "座机：" + contactBean.getData().getTelephoneNo();
                            arrayPhones[1] = "手机：" + contactBean.getData().getMobileNo();
                        } else {
                            arrayPhones = new String[1];
                        }

                        getView().responseContact(arrayPhones);
                    } else {
                        getView().error(contactBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestDoors(Params params) {
        mRxManager.add(mModel.requestDoors(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDoors(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestCheckPermission(Params params) {
        mRxManager.add(mModel.requestCheckPermission(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseCheckPermission(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestHouseInfoList(Params params) {
        mRxManager.add(mModel.requestHouseInfoList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseHouseInfoList(bean.getData());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }
}