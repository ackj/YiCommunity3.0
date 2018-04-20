package cn.itsite.amain.yicommunity.main.mine.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.main.mine.contract.MyHousesContract;
import cn.itsite.amain.yicommunity.main.mine.model.MyHousesModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/5/17 0017 16:15.
 * Email: liujia95me@126.com
 */

public class MyHousesPresenter extends BasePresenter<MyHousesContract.View, MyHousesContract.Model> implements MyHousesContract.Presenter {
    private static final String TAG = MyHousesPresenter.class.getSimpleName();

    public MyHousesPresenter(MyHousesContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected MyHousesContract.Model createModel() {
        return new MyHousesModel();
    }

    @Override
    public void requsetMyHouse(Params params) {
        mRxManager.add(mModel.requsetMyHouse(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myHousesBean -> {
                    if (myHousesBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseHouses(myHousesBean.getData().getAuthBuildings());
                    } else {
                        getView().error(myHousesBean.getOther().getMessage());
                    }
                }, this::error)
        );
    }
}
