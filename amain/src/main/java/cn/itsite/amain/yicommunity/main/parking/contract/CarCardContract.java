package cn.itsite.amain.yicommunity.main.parking.contract;


import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.CarCardListBean;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 邻里模块所对应的各层对象应有的接口。
 */
public interface CarCardContract {

    interface View extends BaseContract.View {
        void responseCarCardList(List<CarCardListBean.DataBean.CardListBean> datas);

        void responseDeleteSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestCarCardList(Params params);

        void requestDeleteCarCard(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<CarCardListBean> requestCarCardList(Params params);

        Observable<BaseBean> requestDeleteCarCard(Params params);
    }
}