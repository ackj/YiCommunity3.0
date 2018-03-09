package cn.itsite.amain.s1.security.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.SubDeviceDetBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/7/4 0004 15:04.
 * Email: liujia95me@126.com
 */

public interface DetectorPropertyContract {

    interface View extends BaseContract.View {
        void responseNodifSuccess(BaseBean baseBean);

        void responseDelSuccess(BaseBean baseBean);

        void responseSubDeviceDet(SubDeviceDetBean bean);

        void responseModsensor(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestNotifProperty(Params params);

        void requestDelsensor(Params params);

        void requestSubDeviceDet(Params params);

        void requestModsensor(Params params);

    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestNotifProperty(Params params);

        Observable<BaseBean> requestDelsensor(Params params);

        Observable<SubDeviceDetBean> requestSubDeviceDet(Params params);

        Observable<BaseBean> requestModsensor(Params params);
    }
}
