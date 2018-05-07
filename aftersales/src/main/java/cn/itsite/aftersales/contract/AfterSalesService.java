package cn.itsite.aftersales.contract;

import java.util.List;

import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.bean.OperateBean;
import cn.itsite.aftersales.model.ReasonTypeBean;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/17 0017 17:34
 */

public interface AfterSalesService {

    //提交申请
    @POST("v1/saleAfterApply")
    Observable<BaseResponse> postApply(@Body BaseRequest body);

    //售后原因类型接口
    @GET("v1/saleAfterApplyReasonTypes")
    Observable<BaseResponse<List<ReasonTypeBean>>> getReasonType();

    //图片上传
    @POST("v1/saleAfterApplyPictures")
    Observable<BaseResponse<List<OperateBean>>> postPictures(@Body MultipartBody body);

}
