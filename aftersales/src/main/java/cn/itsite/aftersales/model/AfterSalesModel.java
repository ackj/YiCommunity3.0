package cn.itsite.aftersales.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.OperateBean;
import cn.itsite.aftersales.contract.AfterSalesContract;
import cn.itsite.aftersales.contract.AfterSalesService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/17 0017 17:43
 */

public class AfterSalesModel extends BaseModel implements AfterSalesContract.Model {
    @Override
    public Observable<BaseResponse> postApply(PostApplyBean applyBean) {
        BaseRequest body = new BaseRequest();
        List<PostApplyBean> list = new ArrayList<>();
        list.add(applyBean);
        body.setData(list);
        body.setMessage("修改这几个订单的状态");
        return HttpHelper
                .getService(AfterSalesService.class)
                .postApply(body)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponse<List<ReasonTypeBean>>> getReasonType() {
        return HttpHelper
                .getService(AfterSalesService.class)
                .getReasonType()
                .subscribeOn(Schedulers.io());
    }

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
        return HttpHelper.getService(AfterSalesService.class).postPictures(builder.build())
                .subscribeOn(Schedulers.io());
    }
}
