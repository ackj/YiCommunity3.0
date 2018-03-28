package cn.itsite.amain.yicommunity.main.steward.contract;


import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.ContactBean;
import cn.itsite.amain.yicommunity.entity.bean.DoorListBean;
import cn.itsite.amain.yicommunity.entity.bean.HouseInfoBean;
import cn.itsite.amain.yicommunity.entity.bean.MyHousesBean;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 管家模块所对应的各层对象应有的接口。
 */
public interface StewardContract {

    interface View extends BaseContract.View {

        void responseHouses(List<MyHousesBean.DataBean.AuthBuildingsBean> listIcons);

        void responseContact(String[] arrayPhones);

        void responseDoors(DoorListBean mDoorListBean);

        void responseCheckPermission(BaseBean mBaseBean);

        void responseHouseInfoList(List<HouseInfoBean.DataBean> datas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestContact(Params params);

        void requestDoors(Params params);

        void requestCheckPermission(Params params);

        void requestHouseInfoList(Params params);

    }

    interface Model extends BaseContract.Model {
        Observable<ContactBean> requestContact(Params params);

        Observable<MyHousesBean> requestHouses(Params params);

        Observable<DoorListBean> requestDoors(Params params);

        Observable<BaseBean> requestCheckPermission(Params params);

        Observable<HouseInfoBean> requestHouseInfoList(Params params);

    }
}