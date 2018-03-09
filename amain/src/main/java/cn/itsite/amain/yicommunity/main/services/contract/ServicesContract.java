package cn.itsite.amain.yicommunity.main.services.contract;


import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.ServicesListBean;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 社区服务所对应的各层对象应有的接口。
 */
public interface ServicesContract {

    interface View extends BaseContract.View {
        void responseServiceCommodityList(List<ServicesListBean.DataBean.DataListBean> datas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestServiceCommodityList(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<ServicesListBean> requestServiceCommodityList(Params params);
    }
}