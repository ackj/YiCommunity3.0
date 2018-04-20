package cn.itsite.amain.s1.main.smarthome.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.MainDeviceListBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/9/26 0026 10:16.
 * Email: liujia95me@126.com
 */

public interface SmartHomeContract {

    interface View extends BaseContract.View {
        void responseEquipmentInfoList(MainDeviceListBean bean);

        void responseDelGatewaySuccess(BaseBean baseBean);

        void responseCameraList(MainDeviceListBean bean);

        void responseAddAndDelCameraSuccess(BaseBean baseBean);

    }

    interface Presenter extends BaseContract.Presenter {
        void requestEquipmentInfoList(Params params);

        void requestDelGateway(Params params);

        void requestCameraList(Params params);

        void requestNewCamera(Params params);

        void requestDelCamera(Params params);

    }

    interface Model extends BaseContract.Model {
        Observable<MainDeviceListBean> requestEquipmentInfoList(Params params);

        Observable<BaseBean> requestDelGateway(Params params);

        Observable<MainDeviceListBean> requestCameraList(Params params);

        Observable<BaseBean> requestNewCamera(Params params);

        Observable<BaseBean> requestDelCamera(Params params);
    }

}
