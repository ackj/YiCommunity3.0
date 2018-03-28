package cn.itsite.amain.yicommunity.main.house.contract;


import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.BuildingBean;
import cn.itsite.amain.yicommunity.entity.bean.CommunitySelectBean;
import cn.itsite.amain.yicommunity.entity.bean.FloorBean;
import cn.itsite.amain.yicommunity.entity.bean.RoomBean;
import cn.itsite.amain.yicommunity.entity.bean.UnitBean;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 房屋模块所对应的各层对象应有的接口。
 */
public interface AddHouseContract {

    interface View extends BaseContract.View {
        //刷新界面，响应社区返回的数据
        void responseCommunitys(List<CommunitySelectBean.DataBean.CommunitiesBean> communities);

        //刷新界面，响应楼栋返回的数据
        void responseBuildings(List<BuildingBean.DataBean.BuildingsBean> buildings);

        //刷新界面，响应单元返回的数据
        void responseUnits(List<UnitBean.DataBean.BuildingUnitsBean> units);

        //刷新界面，响应楼层返回的数据
        void responseFloors(List<FloorBean.DataBean.FloorsBean> floors);

        //刷新界面，响应房间返回的数据
        void responseRooms(List<RoomBean.DataBean.HousesBean> rooms);


        //刷新界面，响应房间返回的数据
        void responseApply(String message);
    }

    interface Presenter extends BaseContract.Presenter {
        //请求社区列表数据
        void requestCommunitys(Params params);

        //请求楼栋列表数据
        void requestBuildings(Params params);

        //请求楼栋列表数据
        void requestUnits(Params params);

        //请求楼层列表数据
        void requestFloors(Params params);

        //请求房间列表数据
        void requestRooms(Params params);

        //请求房间列表数据
        void requestApply(Params params);
    }

    interface Model extends BaseContract.Model {
        //请求社区列表数据
        Observable<CommunitySelectBean> requestCommunitys(Params params);

        //请求楼栋列表数据
        Observable<BuildingBean> requestBuildings(Params params);

        //请求楼栋列表数据
        Observable<UnitBean> requestUnits(Params params);

        //请求楼层列表数据
        Observable<FloorBean> requestFloors(Params params);

        //请求房间列表数据
        Observable<RoomBean> requestRooms(Params params);

        //请求房间列表数据
        Observable<BaseBean> requestApply(Params params);
    }
}