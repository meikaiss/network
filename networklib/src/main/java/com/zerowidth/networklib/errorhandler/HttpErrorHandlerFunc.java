package com.zerowidth.networklib.errorhandler;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 1、http请求相关的错误，如 404、403、socket timeout
 * 2、应用数据的错误会抛 RuntimeException，最后也会走到这个函数来统一处理
 * Created by meikai on 2020/05/16.
 */
public class HttpErrorHandlerFunc<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandler.handlerException(throwable));
    }

}
