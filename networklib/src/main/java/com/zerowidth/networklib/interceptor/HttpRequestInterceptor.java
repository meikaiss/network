package com.zerowidth.networklib.interceptor;

import com.zerowidth.networklib.base.INetWorkRequiredInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by meikai on 2020/05/16.
 */
public class HttpRequestInterceptor implements Interceptor {

    private INetWorkRequiredInfo iNetWorkRequiredInfo;

    public HttpRequestInterceptor(INetWorkRequiredInfo iNetWorkRequiredInfo) {
        this.iNetWorkRequiredInfo = iNetWorkRequiredInfo;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersion", iNetWorkRequiredInfo.getAppVersionName());
        builder.addHeader("source", "source");
        builder.addHeader("channel", "huawei");
        builder.addHeader("Date", Calendar.getInstance().getTime().toString());

        Response response = chain.proceed(builder.build());

        return response;
    }


}
