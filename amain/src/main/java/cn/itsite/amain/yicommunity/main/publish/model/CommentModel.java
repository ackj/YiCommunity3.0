package cn.itsite.amain.yicommunity.main.publish.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.acommon.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.CommentListBean;
import cn.itsite.amain.yicommunity.main.publish.contract.CommentContract;
import okhttp3.MultipartBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/11 0011 16:36.
 * Email: liujia95me@126.com
 */

public class CommentModel extends BaseModel implements CommentContract.Model {

    @Override
    public Observable<CommentListBean> requestExchangeCommentList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .getExchangeComments(ApiService.getExchangeComments, params.fid, params.page, params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<CommentListBean> requestCarpoolCommentList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestCarpoolComments(ApiService.requestCarpoolComments, params.fid, params.page, params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<CommentListBean> requestNeighbourCommentList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNeighbourComments(ApiService.requestNeighbourComments,
                        params.fid,
                        params.page,
                        params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<CommentListBean> requestRemarkReplyList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestRemarkReplyList(ApiService.requestRemarkReplyList,
                        params.fid,
                        params.page,
                        params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestSubmitExchangeComment(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", params.token);
        builder.addFormDataPart("exchangeFid", params.fid);
        builder.addFormDataPart("content", params.content);

        return HttpHelper.getService(ApiService.class)
                .requestSubmitExchangeComment(ApiService.requestSubmitExchangeComment, builder.build())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestSubmitCarpoolComment(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", params.token);
        builder.addFormDataPart("carpoolFid", params.fid);
        builder.addFormDataPart("content", params.content);
        return HttpHelper.getService(ApiService.class)
                .requestSubmitCarpoolComment(ApiService.requestSubmitCarpoolComment, builder.build())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestSubmitNeighbourComment(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", params.token);
        builder.addFormDataPart("momentsFid", params.fid);
        builder.addFormDataPart("content", params.content);
        return HttpHelper.getService(ApiService.class)
                .requestSubmitNeighbourComment(ApiService.requestSubmitNeighbourComment, builder.build())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestSubmitRemark(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSubmitRemark(ApiService.requestSubmitRemark,
                        params.token,
                        params.fid,
                        params.content)
                .subscribeOn(Schedulers.io());
    }
}
