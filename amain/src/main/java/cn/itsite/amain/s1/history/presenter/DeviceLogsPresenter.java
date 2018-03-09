package cn.itsite.amain.s1.history.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.history.contract.DeviceLogsContract;
import cn.itsite.amain.s1.history.model.DeviceLogsModel;
import rx.android.schedulers.AndroidSchedulers;


public class DeviceLogsPresenter extends BasePresenter<DeviceLogsContract.View, DeviceLogsContract.Model> implements DeviceLogsContract.Presenter {
    private final String TAG = DeviceLogsPresenter.class.getSimpleName();

    public DeviceLogsPresenter(DeviceLogsContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected DeviceLogsContract.Model createModel() {
        return new DeviceLogsModel();
    }

    @Override
    public void requestDeviceLogs(Params params) {
        mRxManager.add(mModel.requestDeviceLogs(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == 200) {
                        getView().responseDeviceLogs(bean.getData().getLogs());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));

    }
}