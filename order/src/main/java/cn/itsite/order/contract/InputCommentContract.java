package cn.itsite.order.contract;

import java.io.File;
import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.bean.OperateBean;
import cn.itsite.order.model.PostCommentBean;
import rx.Observable;

/**
 * Created by liujia on 2018/4/20.
 */

public interface InputCommentContract {

    interface View extends BaseContract.View {

        void responsePostPicture(BaseResponse<List<OperateBean>> response, int position);

        void responsePostCommentsSuccess(BaseResponse response);
    }

    interface Presenter extends BaseContract.Presenter {

        void postPicture(List<File> pictures,int position);

        void postComments(PostCommentBean commentBean);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseResponse<List<OperateBean>>> postPictures(List<File> files);
        Observable<BaseResponse> postComments(PostCommentBean commentBean);
    }

}
