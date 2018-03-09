package cn.itsite.amain.yicommunity.main.services.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.RemarkListBean;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 社区服务所对应的各层对象应有的接口。
 */
public interface RemarkContract {

    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter {
    }

    interface Model extends BaseContract.Model {
        Observable<RemarkListBean> requestRemarkList(Params params);
    }
}