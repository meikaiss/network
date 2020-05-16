package com.zerowidth.networklib.base;

import com.zerowidth.networklib.beans.BaseResponse;
import com.zerowidth.networklib.interceptor.HttpLogInterceptor;
import com.zerowidth.networklib.errorhandler.ExceptionHandler;
import com.zerowidth.networklib.errorhandler.HttpErrorHandlerFunc;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by meikai on 2020/05/16.
 */
public class NetWorkApi {
    private static final String BASE_URL = "http://v.juhe.cn/";

    private static INetWorkRequiredInfo iNetWorkRequiredInfo;
    private static HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();

    public static void init(INetWorkRequiredInfo netWorkRequiredInfo) {
        iNetWorkRequiredInfo = netWorkRequiredInfo;
    }

    public static <T> T getService(Class<T> service) {
        return getRetrofit(service).create(service);
    }

    private static Retrofit getRetrofit(Class service) {
        Retrofit retrofit = retrofitHashMap.get(service.getName());
        if (retrofit != null) {
            return retrofit;
        }

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(BASE_URL);
        retrofitBuilder.client(getOkHttpClient());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        retrofit = retrofitBuilder.build();
        retrofitHashMap.put(service.getName(), retrofit);
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (iNetWorkRequiredInfo != null && iNetWorkRequiredInfo.isDebug()) {
            okHttpClientBuilder.addInterceptor(new HttpLogInterceptor(iNetWorkRequiredInfo));
        }
        return okHttpClientBuilder.build();
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstreamObservable) {
                Observable<T> downstream = upstreamObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(NetWorkApi.<T>getAppErrorHandler()) // getAppErrorHandler前面的<T>表示，此方法的T与  applySchedulers 的T相同
                        .onErrorResumeNext(new HttpErrorHandlerFunc<T>());

                downstream.subscribe(observer);

                return downstream;
            }
        };
    }

    public static <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T t) throws Exception {

                if (t instanceof BaseResponse && ((BaseResponse) t).errorCode != 0) {
                    ExceptionHandler.ServerException serverException = new ExceptionHandler.ServerException();
                    serverException.code = ((BaseResponse) t).errorCode;
                    serverException.message = ((BaseResponse) t).reason;

                    throw serverException;
                }

                return t;
            }
        };
    }

}
