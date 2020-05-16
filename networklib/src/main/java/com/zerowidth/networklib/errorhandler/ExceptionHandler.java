package com.zerowidth.networklib.errorhandler;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * Created by meikai on 2020/05/16.
 */
public class ExceptionHandler {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;

    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    public static ResponseThrowable handlerException(Throwable throwableInParam) {
        ResponseThrowable ex;

        if (throwableInParam instanceof HttpException) {
            HttpException httpException = (HttpException) throwableInParam;
            ex = new ResponseThrowable(throwableInParam, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";
                    break;
            }
        } else if (throwableInParam instanceof ServerException) {
            ServerException serverException = (ServerException) throwableInParam;
            ex = new ResponseThrowable(throwableInParam, serverException.code);
            ex.message = serverException.message;
        } else if (throwableInParam instanceof JsonParseException
                || throwableInParam instanceof JSONException
                || throwableInParam instanceof ParseException) {
            ex = new ResponseThrowable(throwableInParam, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
        } else if (throwableInParam instanceof ConnectException) {
            ex = new ResponseThrowable(throwableInParam, ERROR.NETWORK_ERROR);
            ex.message = "连接失败";
        } else if (throwableInParam instanceof SSLHandshakeException) {
            ex = new ResponseThrowable(throwableInParam, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
        } else if (throwableInParam instanceof ConnectTimeoutException
                || throwableInParam instanceof SocketTimeoutException) {
            ex = new ResponseThrowable(throwableInParam, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
        } else {
            ex = new ResponseThrowable(throwableInParam, ERROR.UNKNOWN);
            ex.message = "未知错误";
        }

        return ex;
    }

    public static class ServerException extends Exception {
        public int code;
        public String message;
    }

}
