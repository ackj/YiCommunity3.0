package cn.itsite.amain.yicommunity.login.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import rx.Observable;

/**
 * Author: LiuJia on 2017/6/23 0023 19:18.
 * Email: liujia95me@126.com
 */

public interface ResetPasswordContract {

    interface View extends BaseContract.View {
        void reponseResetSuccess(BaseBean baseBean);

        void responseVerfyCodeSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestReset(Params params);

        void requestVerifyCode(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestReset(Params params);

        Observable<BaseBean> requestVerifyCode(Params params);
    }

}
