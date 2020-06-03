package com.zerowidth.networklib.base;

import android.util.Log;

import com.zerowidth.networklib.interceptor.HttpLogInterceptor;
import com.zerowidth.networklib.interceptor.HttpRequestInterceptor;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by meikai on 2020/05/16.
 */
public abstract class NetWorkApi {

    protected INetWorkRequiredInfo iNetWorkRequiredInfo;
    private static HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();

    protected abstract String getBaseUrl();

    protected abstract Interceptor getInterceptor();

    public void init(INetWorkRequiredInfo netWorkRequiredInfo) {
        iNetWorkRequiredInfo = netWorkRequiredInfo;
    }

    public <T> T getService(Class<T> service) {
        return getRetrofit(service).create(service);
    }

    public Retrofit getRetrofit(Class service) {
        Retrofit retrofit = retrofitHashMap.get(service.getName());
        if (retrofit != null) {
            return retrofit;
        }

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(getBaseUrl());
        retrofitBuilder.client(getOkHttpClient());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        retrofit = retrofitBuilder.build();
        retrofitHashMap.put(service.getName(), retrofit);
        return retrofit;
    }

    protected ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("self retrofit thread");
                return thread;
            }
        });
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        Dispatcher dispatcher = new Dispatcher(newCachedThreadPool());
        okHttpClientBuilder.dispatcher(dispatcher);

        okHttpClientBuilder.callTimeout(0, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.connectTimeout(10_000, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.writeTimeout(10_000, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.readTimeout(10_000, TimeUnit.MILLISECONDS);

        // 增加 通用请求头
//        Interceptor interceptor = getInterceptor();
//        if (interceptor != null) {
//            okHttpClientBuilder.addInterceptor(interceptor);
//        }
//
//        // 增加 自定义的请求日志
//        if (iNetWorkRequiredInfo != null && iNetWorkRequiredInfo.isDebug()) {
//            okHttpClientBuilder.addInterceptor(new HttpLogInterceptor(iNetWorkRequiredInfo));
//        }

        // 增加 官方请求日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
                Log.e("okhttp_logIntercept", s);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(loggingInterceptor);

        return okHttpClientBuilder.build();
    }


}
