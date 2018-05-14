package cn.itsite.amain.yicommunity.main.publish.model;

import java.io.File;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.main.publish.contract.PublishContract;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/12 0012 14:57.
 * Email: liujia95me@126.com
 */

public class PublishCarpoolModel extends BaseModel implements PublishContract.Model {
    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<BaseBean> requestSubmit(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", params.token);
        builder.addFormDataPart("cmnt_c", params.cmnt_c);
        builder.addFormDataPart("carpoolType", params.carpoolType + "");
        builder.addFormDataPart("startPlace", params.startPlace);
        builder.addFormDataPart("endPlace", params.endPlace);
        builder.addFormDataPart("setOutTime", params.outTime);
        builder.addFormDataPart("content", params.content);
        builder.addFormDataPart("publishPositionLat", params.currentPositionLat);
        builder.addFormDataPart("publishPositionLng", params.currentPositionLng);
        builder.addFormDataPart("publishPositionAddress", params.positionAddress);
        builder.addFormDataPart("positionType", params.positionType + "");
        builder.addFormDataPart("type", params.type + "");

        ALog.d("PublishNeighbourModel", "上传=============");

        if (params.files != null && params.files.size() > 0) {
            for (File f : params.files) {
                ALog.d("PublishNeighbourModel", "上传图片：" + f.getAbsolutePath());
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), f);
                builder.addFormDataPart("file", f.getName(), requestBody);
            }
        }
        return HttpHelper.getService(ApiService.class)
                .requestSubmitCarpool(ApiService.requestSubmitCarpool,
                        builder.build())
                .subscribeOn(Schedulers.io());
    }
}
