package cn.itsite.amain.s1.room.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.DeviceListBean;
import cn.itsite.amain.s1.entity.bean.RoomsBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/8/20 0020 18:10.
 * Email: liujia95me@126.com
 */

public interface RoomDeviceListContract {


    interface View extends BaseContract.View {
        void responseDeviceList(List<DeviceListBean.DataBean.SubDevicesBean> data);

        void responseHouseList(List<RoomsBean.DataBean.RoomListBean> data);

        void responseDevicectrl(BaseBean bean);

        void responseNewDeviceConfirm(BaseBean bean);

        void responseNewDevice24(BaseBean bean);

        void responseDelSuccess(BaseBean bean);

    }

    interface Presenter extends BaseContract.Presenter {
        void requestDeviceList(Params params);

        void requestHouseList(Params params);

        void requestDevicectrl(Params params);

        void requestNewDeviceConfirm(Params params);

        void requestNewDevice24(Params params);

        void requestDelDevice(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<DeviceListBean> requestDeviceList(Params params);

        Observable<RoomsBean> requestHouseList(Params params);

        Observable<BaseBean> requestDevicectrl(Params params);

        Observable<BaseBean> requestNewDeviceConfirm(Params params);

        Observable<BaseBean> requestNewDevice24(Params params);

        Observable<BaseBean> requestDelDevice(Params params);

    }
}
