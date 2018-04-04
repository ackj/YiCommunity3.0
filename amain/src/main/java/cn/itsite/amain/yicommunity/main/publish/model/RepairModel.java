package cn.itsite.amain.yicommunity.main.publish.model;

import java.io.File;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.MyHousesBean;
import cn.itsite.amain.yicommunity.entity.bean.RepairTypesBean;
import cn.itsite.amain.yicommunity.main.publish.contract.PublishContract;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/9 0009 10:35.
 * Email: liujia95me@126.com
 */

public class RepairModel extends BaseModel implements PublishContract.Model {

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<BaseBean> requestSubmit(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", params.token);
        builder.addFormDataPart("cmnt_c", params.cmnt_c);
        builder.addFormDataPart("contact", params.contact);
        builder.addFormDataPart("des", params.des);
        builder.addFormDataPart("name", params.name);
        builder.addFormDataPart("single", params.single + "");
        builder.addFormDataPart("type", params.repairType);
        builder.addFormDataPart("ofid", params.ofid);

        ALog.d("PublishNeighbourModel", "上传=============");

        if (params.files != null && params.files.size() > 0) {
            for (File f : params.files) {
                ALog.d("PublishNeighbourModel", "上传图片：" + f.getAbsolutePath());
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), f);
                builder.addFormDataPart("file", f.getName(), requestBody);
            }
        }
        return HttpHelper.getService(ApiService.class).postRepair(ApiService.postRepair,
                builder.build())
                .subscribeOn(Schedulers.io());
    }

    public Observable<MyHousesBean> requestHouses(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestMyhouses(ApiService.requestMyhouses, params.token, params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }

//    //todo:S2写法
//    public Single<List<IconBean>> requestHouses(Params params) {
//        return HttpHelper.getService(ApiService.class)
//                .requestMyhouses(ApiService.requestMyhouses, params.token, params.cmnt_c)
//                .map(myHousesBean -> myHousesBean.getData().getAuthBuildings())
//                .flatMap(Flowable::fromIterable)
//                .map(OperateBean -> new IconBean(R.drawable.ic_my_house_red_140px, OperateBean.getAddress(), OperateBean.getFid()))
//                .toList()
//                .subscribeOn(Schedulers.io());
//    }

    public Observable<RepairTypesBean> requestRepairTypes(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestRepairTypes(ApiService.requestRepairTypes, params.type, params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }
}
