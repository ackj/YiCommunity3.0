package cn.itsite.amain.yicommunity.main.publish.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.CommentBean;
import cn.itsite.amain.yicommunity.entity.bean.CommentListBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/5/11 0011 16:34.
 * Email: liujia95me@126.com
 */

public interface CommentContract {

    interface View extends BaseContract.View {
        void responseCommentList(List<CommentBean> datas);

        void responseCommentSuccess(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestExchangeCommentList(Params params);

        void requestCarpoolCommentList(Params params);

        void requestNeighbourCommentList(Params params);

        void requestRemarkReplyList(Params params);

        void requestSubmitExchangeComment(Params params);

        void requestSubmitCarpoolComment(Params params);

        void requestSubmitNeighbourComment(Params params);

        void requestSubmitRemark(Params params);

    }

    interface Model extends BaseContract.Model {
        Observable<CommentListBean> requestExchangeCommentList(Params params);

        Observable<CommentListBean> requestCarpoolCommentList(Params params);

        Observable<CommentListBean> requestNeighbourCommentList(Params params);

        Observable<CommentListBean> requestRemarkReplyList(Params params);


        Observable<BaseBean> requestSubmitExchangeComment(Params params);

        Observable<BaseBean> requestSubmitCarpoolComment(Params params);

        Observable<BaseBean> requestSubmitNeighbourComment(Params params);

        Observable<BaseBean> requestSubmitRemark(Params params);
    }

}
