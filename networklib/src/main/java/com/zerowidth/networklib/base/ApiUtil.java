package com.zerowidth.networklib.base;

import com.zerowidth.networklib.beans.BaseResponse;
import com.zerowidth.networklib.errorhandler.ExceptionHandler;
import com.zerowidth.networklib.errorhandler.HttpErrorHandlerFunc;
import com.zerowidth.networklib.observer.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by meikai on 2020/06/03.
 */
public class ApiUtil {

    public static <T> void request(Observable<T> observable, BaseObserver<T> baseObserver) {
        observable
                .compose(applySchedulers(baseObserver))
                .subscribe(baseObserver);
    }


    private static <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstreamObservable) {
                Observable<T> downstream = upstreamObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ApiUtil.<T>getAppErrorHandler()) // getAppErrorHandler前面的<T>表示，此方法的T与  applySchedulers 的T相同
                        .onErrorResumeNext(new HttpErrorHandlerFunc<T>());

                //downstream.subscribe(observer);

                return downstream;
            }
        };
    }

    public static <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T t) throws Exception {

                if (t instanceof BaseResponse && ((BaseResponse) t).code != 0) {
                    ExceptionHandler.ServerException serverException = new ExceptionHandler.ServerException();
                    serverException.code = ((BaseResponse) t).code;
                    serverException.message = ((BaseResponse) t).message;

                    throw serverException;
                }

                return t;
            }
        };
    }


}
