package cn.itsite.amain.s1.history.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.DeviceLogBean;
import rx.Observable;


public interface DeviceLogsContract {

    interface View extends BaseContract.View {
        void responseDeviceLogs(List<DeviceLogBean.DataBean.LogsBean> data);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestDeviceLogs(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<DeviceLogBean> requestDeviceLogs(Params params);
    }
}