package cn.itsite.amain.s1.room.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.room.contract.DeviceOnOffContract;
import cn.itsite.amain.s1.room.model.DeviceOnOffModel;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/8/30 0030 10:11.
 * Email: liujia95me@126.com
 */

public class DeviceOnOffPresenter extends BasePresenter<DeviceOnOffContract.View, DeviceOnOffContract.Model> implements DeviceOnOffContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public DeviceOnOffPresenter(DeviceOnOffContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected DeviceOnOffContract.Model createModel() {
        return new DeviceOnOffModel();
    }

    @Override
    public void requestDeviceCtrl(Params params) {
        mRxManager.add(mModel.requestDeviceCtrl(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseOnOffSuccess(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }
}
