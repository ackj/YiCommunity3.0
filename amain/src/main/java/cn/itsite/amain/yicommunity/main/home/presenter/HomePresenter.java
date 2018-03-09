package cn.itsite.amain.yicommunity.main.home.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.common.UserHelper;
import cn.itsite.amain.yicommunity.main.home.contract.HomeContract;
import cn.itsite.amain.yicommunity.main.home.model.HomeModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责管家模块Presenter层内容。
 */

public class HomePresenter extends BasePresenter<HomeContract.View, HomeContract.Model> implements HomeContract.Presenter {
    private final String TAG = HomePresenter.class.getSimpleName();

    public HomePresenter(HomeContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected HomeContract.Model createModel() {
        return new HomeModel();
    }

    @Override
    public void requestBanners(Params params) {
        mRxManager.add(mModel.requestBanners(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bannerBean -> {
                    if (bannerBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseBanners(bannerBean.getData().getAdvs());
                    } else {
                        getView().error(bannerBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestHomeNotices(Params params) {
        mRxManager.add(mModel.requestHomeNotices(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseHomeNotices(bean.getData().getNoticeList());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestOpenDoor() {
        Params params = Params.getInstance();
        params.dir = UserHelper.dir;
        mRxManager.add(mModel.requestOpenDoor(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseOpenDoor();
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestServiceTypes(Params params) {
        mRxManager.add(mModel.requestServiceTypes(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(classifyListBean -> {
                    if (classifyListBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseServiceClassifyList(classifyListBean.getData().getClassifyList());
                    } else {
                        getView().error(classifyListBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestOneKeyOpenDoorDeviceList(Params params) {
        mRxManager.add(mModel.requestOneKeyOpenDoorDeviceList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(oneKeyDoorBean -> {
                    if (oneKeyDoorBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseOneKeyOpenDoorDeviceList(oneKeyDoorBean.getData().getItemList());
                    } else {
                        getView().error(oneKeyDoorBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestFirstLevel(Params params) {
        mRxManager.add(mModel.requestFirstLevel(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(firstLevelBean -> {
                    if (firstLevelBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseFirstLevel(firstLevelBean.getData());
                    } else {
                        getView().error(firstLevelBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestSubCategoryList(Params params) {
        mRxManager.add(mModel.requestSubCategoryList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryBean -> {
                    if (categoryBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseSubCategoryList(categoryBean.getData());
                    } else {
                        getView().error(categoryBean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestCommEquipmentList(Params params) {
        mRxManager.add(mModel.requestCommEquipmentList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseCommEquipmentList(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestScanOpenDoor(Params params) {
        mRxManager.add(mModel.requestScanOpenDoor(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseScanOpenDoor(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }
}