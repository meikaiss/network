package com.zerowidth.networklib.errorhandler;

/**
 * Created by meikai on 2020/05/16.
 */
public class ResponseThrowable extends Throwable {

    public Throwable throwable;
    public int error;
    public String message;

    public ResponseThrowable(Throwable throwable, int error) {
        this.throwable = throwable;
        this.error = error;
    }
}
