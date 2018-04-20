package cn.itsite.order.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.OperateBean;
import cn.itsite.order.contract.InputCommentContract;
import cn.itsite.order.contract.OrderService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 2018/4/20.
 */

public class InputCommentModel extends BaseModel implements InputCommentContract.Model {

    @Override
    public Observable<BaseResponse<List<OperateBean>>> postPictures(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        ALog.d("PublishNeighbourModel", "上传=============");
        if (files != null && files.size() > 0) {
            for (File f : files) {
                ALog.d("PublishNeighbourModel", "上传图片：" + f.getAbsolutePath());
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), f);
                builder.addFormDataPart("files", f.getName(), requestBody);
            }
        }
        return HttpHelper.getService(OrderService.class).postPictures(builder.build())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse> postComments(PostCommentBean commentBean) {
        BaseRequest body = new BaseRequest();
        List<PostCommentBean> list = new ArrayList<>();
        list.add(commentBean);
        body.setData(list);
        body.setMessage("修改这几个订单的状态");
        return HttpHelper.getService(OrderService.class).postEvaluate(body)
                .subscribeOn(Schedulers.io());
    }
}
