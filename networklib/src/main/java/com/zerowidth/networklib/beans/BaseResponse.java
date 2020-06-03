package com.zerowidth.networklib.beans;

/**
 * Created by meikai on 2020/05/16.
 */
public class BaseResponse<T> {

    public int code;

    public String message;

    public T data;

}
