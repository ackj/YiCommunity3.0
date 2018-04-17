package cn.itsite.aftersales.model;

import java.io.File;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.GoodsParams;
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
    public Observable<BaseResponse> postApply(GoodsParams params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.addFormDataPart("type", params.type + "");
        ALog.d("PublishNeighbourModel", "上传=============");
        if (params.files != null && params.files.size() > 0) {
            for (File f : params.files) {
                ALog.d("PublishNeighbourModel", "上传图片：" + f.getAbsolutePath());
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), f);
                builder.addFormDataPart("file", f.getName(), requestBody);
            }
        }
        return HttpHelper
                .getService(AfterSalesService.class)
                .postApply(builder.build())
                .subscribeOn(Schedulers.io());
    }
}
