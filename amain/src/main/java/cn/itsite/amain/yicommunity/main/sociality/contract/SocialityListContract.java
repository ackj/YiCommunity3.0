package cn.itsite.amain.yicommunity.main.sociality.contract;


import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.SocialityListBean;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 邻里模块所对应的各层对象应有的接口。
 */
public interface SocialityListContract {

    interface View extends BaseContract.View {
        void responseSuccess(List<SocialityListBean.DataBean.MomentsListBean> datas);

        void removeSuccess(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestNeighbourList(Params params);

        void requestExchangeList(Params params);

        void requestCarpoolList(Params params);

        void requestMyNeihbourList(Params params);

        void requestMyExchangeList(Params params);

        void requestMyCarpoolList(Params params);

        void requestRemoveMyCarpool(Params params);

        void requestRemoveMyExchange(Params params);

        void requestRemoveMyNeighbour(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<SocialityListBean> requestNeighbourList(Params params);

        Observable<SocialityListBean> requestExchangeList(Params params);

        Observable<SocialityListBean> requestCarpoolList(Params params);

        Observable<SocialityListBean> requestMyNeihbourList(Params params);

        Observable<SocialityListBean> requestMyExchangeList(Params params);

        Observable<SocialityListBean> requestMyCarpoolList(Params params);

        Observable<BaseBean> requestRemoveMyCarpool(Params params);

        Observable<BaseBean> requestRemoveMyExchange(Params params);

        Observable<BaseBean> requestRemoveMyNeighbour(Params params);

    }
}