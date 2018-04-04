package cn.itsite.apayment.payment.network;

import com.itsite.abase.BuildConfig;

import java.io.IOException;
import java.util.Map;

import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.log.ALog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @version v0.0.0
 * @Author leguang
 * @E-mail langmanleguang@qq.com
 * @Blog https://github.com/leguang
 * @Time 2018/1/12 0012 11:17
 * @Description: okhttp3网络请求简单封装.
 */

public class OkHttpClient implements INetworkClient {

    @Override
    public void get(String url, Map<String, String> params, final CallBack callBack) {
        StringBuffer sbUrl = new StringBuffer(url).append("?");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sbUrl.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }

        sbUrl.deleteCharAt(sbUrl.length() - 1);
        ALog.e("sbUrl.toString()-->" + sbUrl.toString());

        okhttp3.OkHttpClient mOkHttpClient = new okhttp3.OkHttpClient();
        Request request = new Request.Builder().url(sbUrl.toString()).build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body().string());
                } else {
                    callBack.onFailure();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callBack.onFailure();
            }
        });
    }

    @Override
    public void post(String url, Map<String, String> params, final CallBack callBack) {
        okhttp3.OkHttpClient mOkHttpClient = new okhttp3.OkHttpClient();
        RequestBody requestBody = null;
        if (url.equals(PayService.requestGoodsPayResult)) {
            ALog.e(params.get("params"));
            requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , params.get("params"));
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
                ALog.e("----------->" + entry.getKey() + "<--->" + entry.getValue());
            }
            requestBody = builder.build();
        }

        Request request = new Request.Builder()
                .url(url)
                .header("token", UserHelper.token)
                .header("fromPoint", BuildConfig.fromPoint)
                .header("Content-Type", "application/json;charset=UTF-8")
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body().string());
                } else {
                    callBack.onFailure();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callBack.onFailure();
            }
        });
    }
}
