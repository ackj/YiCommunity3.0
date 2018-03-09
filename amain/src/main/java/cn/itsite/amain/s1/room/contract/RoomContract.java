package cn.itsite.amain.s1.room.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import rx.Observable;


public interface RoomContract {

    interface View extends BaseContract.View {
        void responseRoomInfo(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestRoomInfo(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestRoomInfo(Params params);
    }
}