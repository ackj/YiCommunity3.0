package cn.itsite.amain.yicommunity.main.parking.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import rx.Observable;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车位模块接口。
 */
public interface ApplyContract {

    interface View extends BaseContract.View {
        void responseApplyCard(BaseBean data);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestApplyCard(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestApplyCard(Params params);
    }
}