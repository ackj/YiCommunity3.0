package cn.itsite.amain.yicommunity.main.services.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.ServiceDetailBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/6/30 0030 17:22.
 * Email: liujia95me@126.com
 */

public interface ServicesDetailContract {

    interface View extends BaseContract.View {
        void responseServiceDetail(ServiceDetailBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestServiceDetail(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<ServiceDetailBean> requestServiceDetail(Params params);
    }

}
