package cn.itsite.amain.yicommunity.main.door.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.DoorListBean;
import cn.itsite.amain.yicommunity.entity.bean.PasswordBean;
import rx.Observable;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 门禁模块所对应的各层对象应有的接口。
 */
public interface PasswordOpenDoorContract {

    interface View extends BaseContract.View {
        //刷新界面，响应社区返回的数据
        void responseDoors(DoorListBean mDoorListBean);

        void responsePassword(PasswordBean mPasswordBean);

    }

    interface Presenter extends BaseContract.Presenter {
        //请求社区列表数据
        void requestDoors(Params params);

        void requestPassword(Params params);
    }

    interface Model extends BaseContract.Model {

        Observable<DoorListBean> requestDoors(Params params);

        Observable<PasswordBean> getPassword(Params params);
    }
}