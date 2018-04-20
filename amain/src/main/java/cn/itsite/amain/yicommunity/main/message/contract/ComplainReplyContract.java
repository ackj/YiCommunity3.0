package cn.itsite.amain.yicommunity.main.message.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.ComplainReplyBean;
import rx.Observable;

/**
 * Author: LiuJia on 2017/5/18 0018 18:00.
 * Email: liujia95me@126.com
 */

public interface ComplainReplyContract {

    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter {
    }

    interface Model extends BaseContract.Model {
        Observable<ComplainReplyBean> requestComplainReplies(Params params);
    }
}
