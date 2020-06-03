package com.zerowidth.networklib.interceptor;

import android.util.Log;

import com.zerowidth.networklib.base.INetWorkRequiredInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by meikai on 2020/05/16.
 */
public class HttpLogInterceptor implements Interceptor {

    private static final String TAG = "HttpLogInterceptor";

    private INetWorkRequiredInfo iNetWorkRequiredInfo;

    public HttpLogInterceptor(INetWorkRequiredInfo iNetWorkRequiredInfo) {
        this.iNetWorkRequiredInfo = iNetWorkRequiredInfo;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Log.e(TAG, "地址-->" + chain.request().method() + "  " + chain.request().url());
        Log.e(TAG, "请求头-->" + chain.request().headers().toString());

        Request.Builder builder = chain.request().newBuilder();

        long start = System.currentTimeMillis();
        Response response = chain.proceed(builder.build());
        long end = System.currentTimeMillis();
        Log.e(TAG, "耗时-->" + (end - start) + "ms");


        //Log.e(TAG, "结果-->" + response.body().string());

        return response;
    }


}
