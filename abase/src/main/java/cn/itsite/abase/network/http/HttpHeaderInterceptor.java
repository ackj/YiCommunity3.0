package cn.itsite.abase.network.http;

import com.itsite.abase.BuildConfig;

import java.io.IOException;

import cn.itsite.abase.common.UserHelper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @version v0.0.0
 * @Author leguang
 * @E-mail langmanleguang@qq.com
 * @Blog https://github.com/leguang
 * @Time 2018/3/8 0008 11:24
 * @Description
 */
public class HttpHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //  配置请求头
        Request request = chain.request().newBuilder()
                .header("token", UserHelper.token)
                .header("fromPoint", BuildConfig.fromPoint)
                .header("Content-Type", "application/json;charset=UTF-8")
                .build();

//        HttpUrl httpUrl = request.url()
//                .newBuilder()
//                .addEncodedQueryParameter("version", "VersionName")
//                .build();
//        request = request.newBuilder().url(httpUrl).build();

        return chain.proceed(request);
    }
}

