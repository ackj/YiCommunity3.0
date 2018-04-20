package cn.itsite.aftersales.contract;


import java.io.File;
import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.OperateBean;
import cn.itsite.aftersales.model.PostApplyBean;
import cn.itsite.aftersales.model.ReasonTypeBean;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/17 0017 17:31
 */

public interface AfterSalesContract {

    interface View extends BaseContract.View {
        void responsePostSuccess(BaseResponse response);

        void responseReasonType(BaseResponse<List<ReasonTypeBean>> reasonTypes);

        void responsePostPicture(BaseResponse<List<OperateBean>> response, int position);

    }

    interface Presenter extends BaseContract.Presenter {
        void postApply(PostApplyBean applyBean);

        void getReasontType();

        void postPicture(List<File> pictures, int position);

    }

    interface Model extends BaseContract.Model {
        Observable<BaseResponse> postApply(PostApplyBean applyBean);

        Observable<BaseResponse<List<ReasonTypeBean>>> getReasonType();

        Observable<BaseResponse<List<OperateBean>>> postPictures(List<File> files);

    }
}
