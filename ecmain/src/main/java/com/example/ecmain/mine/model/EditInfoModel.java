package com.example.ecmain.mine.model;


import com.example.ecmain.mine.contract.EditInfoContract;

import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.contract.LoginService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 04/05/2018.
 */

public class EditInfoModel extends BaseModel implements EditInfoContract.Model {

    @Override
    public Observable<BaseOldResponse<String>> requestUpdateAvator(UserParams params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), params.file);
        builder.addFormDataPart("file", params.file.getName(), requestBody);
        return HttpHelper.getService(LoginService.class)
                .requestUploadAvator(LoginService.requestUploadAvator,
                        UserHelper.token, builder.build())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseOldResponse> requestNickname(UserParams params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", UserHelper.token);
        builder.addFormDataPart("field", "nickName");
        builder.addFormDataPart("val", params.username);
        return HttpHelper.getService(LoginService.class)
                .requsetUpdateUserData(LoginService.requsetUpdateUserData, builder.build())
                .subscribeOn(Schedulers.io());

    }


}
