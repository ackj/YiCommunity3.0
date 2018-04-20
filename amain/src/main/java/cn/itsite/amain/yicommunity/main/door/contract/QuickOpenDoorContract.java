package cn.itsite.amain.yicommunity.main.door.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.DoorListBean;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 门禁模块所对应的各层对象应有的接口。
 */
public interface QuickOpenDoorContract {

    interface View extends BaseContract.View {
        //刷新界面，响应社区返回的数据
        void responseDoors(DoorListBean mDoorListBean);

        //刷新界面，响应楼栋返回的数据
        void responseQuickOpenDoor(BaseBean mBaseBean);

        void responseResetOneKeyOpenDoor(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {
        //请求社区列表数据
        void requestDoors(Params params);

        //请求楼栋列表数据
        void requestQuickOpenDoor(Params params);

        void requestResetOneKeyOpenDoor(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<DoorListBean> requestDoors(Params params);

        Observable<BaseBean> requestSetQuickOpenDoor(Params params);

        Observable<BaseBean> requestResetOneKeyOpenDoor(Params params);
    }
}