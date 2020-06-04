package com.zerowidth.networklib.interceptor;

import android.net.Uri;

import com.zerowidth.networklib.base.INetWorkRequiredInfo;
import com.zerowidth.networklib.utils.SignUtils;

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
        Uri originalUri = Uri.parse(chain.request().url().toString());
        Uri.Builder uriBuilder = originalUri.buildUpon();

        String signValue = SignUtils.getSign(chain.request(), uriBuilder);
        uriBuilder.appendQueryParameter("sign", signValue);
        if (iNetWorkRequiredInfo.design()) {
            uriBuilder.appendQueryParameter("x", "575757");
        }
        Uri uri = uriBuilder.build();

        Request.Builder builder = chain.request().newBuilder();
        builder.url(uri.toString());

        builder.header("os", "android");
        builder.header("appVersion", iNetWorkRequiredInfo.getAppVersionName());
        builder.header("source", "source");
        builder.header("channel", "huawei");
        builder.header("Date", Calendar.getInstance().getTime().toString());

        Request request = builder.build();

        Response response = chain.proceed(request);

        return response;
    }


}
