package com.zerowidth.networklib.utils;

import android.net.Uri;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Request;

/**
 * Created by meikai on 2020/06/04.
 */
public class SignUtils {

    private static final String SIGN_SALT = "*#900507#*";

    private static final String KEY_TIME_STAMP = "timeStamp";

    public static String getSign(Request request, Uri.Builder uriBuilder) {

        SortedMap<String, String> sortedMap = new TreeMap<>();

        //url里的参数
        Map<String, String> urpParamsMap = getUrlParams(request);
        for (Map.Entry<String, String> entry : urpParamsMap.entrySet()) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        //body里的参数
        if (!"GET".equalsIgnoreCase(request.method())) {
            Map<String, String> bodyParamsMap = getBodyParams(request);
            for (Map.Entry<String, String> entry : bodyParamsMap.entrySet()) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
        }

        //时间戳
        String timeStamp = System.currentTimeMillis() + "";
        sortedMap.put(KEY_TIME_STAMP, timeStamp);
        uriBuilder.appendQueryParameter(KEY_TIME_STAMP, timeStamp);

        //host:port/path 部分（即问号左边的部分）
        String url = request.url().toString();
        int index = url.indexOf("?");
        if (index > 0) {
            url = url.substring(0, index);
        }
        String paramsJsonStr = JSONObject.toJSONString(sortedMap);

        return DigestUtils.md5(SIGN_SALT + url + paramsJsonStr).toLowerCase();
    }

    /**
     * 获取 body 中的参数
     */
    public static Map<String, String> getBodyParams(Request request) {
        Map<String, String> result = new HashMap<>();

        String body = request.body().toString();
        result = JSON.parseObject(body, result.getClass());

        return result;
    }

    /**
     * 获取url地址上的参数
     */
    public static Map<String, String> getUrlParams(Request request) {
        Map<String, String> result = new HashMap<>();

        Uri originalUri = Uri.parse(request.url().toString());
        Set<String> paramNames = originalUri.getQueryParameterNames();

        Iterator<String> iterator = paramNames.iterator();
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            String paramValue = originalUri.getQueryParameter(paramName);
            result.put(paramName, paramValue);
        }

        return result;
    }

}
