package cn.itsite.amain.s1.security.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.DevicesBean;
import rx.Observable;


public interface AddDetectorContract {

    interface View extends BaseContract.View {
        void responseDetectorList(List<DevicesBean.DataBean.DeviceTypeListBean> bean);

        void responseAddDetector(BaseBean baseBean);

        void responseCancellationOfSensorLearning(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestDetectorList(Params params);

        void requestAddDetector(Params params);

        void reqeuestCancellationOfSensorLearning(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<DevicesBean> requestDetectorList(Params params);

        Observable<BaseBean> requestAddDetector(Params params);

        Observable<BaseBean> reqeuestCancellationOfSensorLearning(Params params);
    }
}