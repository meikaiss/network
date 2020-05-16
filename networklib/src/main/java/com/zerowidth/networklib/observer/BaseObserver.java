package com.zerowidth.networklib.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by meikai on 2020/05/16.
 */
public abstract class BaseObserver<T> implements Observer<T> {

    public abstract void onSuccess(T t);
    public abstract void onFailure(Throwable e);


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {

    }

}
