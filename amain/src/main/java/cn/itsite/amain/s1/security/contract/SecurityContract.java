package cn.itsite.amain.s1.security.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.GatewaysBean;
import cn.itsite.amain.s1.entity.bean.SecurityBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/7/4 0004 09:17.
 * Email: liujia95me@126.com
 */

public interface SecurityContract {

    interface View extends BaseContract.View {

        void responseSecurity(SecurityBean securityBean);

        void responseGateways(GatewaysBean gatewaysBean);

        void responseSwichGateway(BaseBean baseBean);

        void responseSwichState(BaseBean baseBean);

        void responseLeaveMassge(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {

        void requestSecurity(Params params);

        void requestGateways(Params params);

        void requestSwichGateway(Params params);

        void requestSwichState(Params params);

        void requestLeaveMassge(Params params);

    }

    interface Model extends BaseContract.Model {

        Observable<SecurityBean> requestSecurity(Params params);

        Observable<GatewaysBean> requestGateways(Params params);

        Observable<BaseBean> requestSwichGateway(Params params);

        Observable<BaseBean> requestSwichState(Params params);

        Observable<BaseBean> requestLeaveMassge(Params params);

    }
}
