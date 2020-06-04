package com.zerowidth.networklib.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Request;
import okhttp3.internal.http.HttpMethod;

/**
 * Created by meikai on 2020/06/03.
 */
public class HttpUtils {

    public static SortedMap<String, String> getAllParams(Request request) throws IOException {
        SortedMap<String, String> result = new TreeMap<>();

        //获取URL上的参数
//        Map<String, String> urlParams = getUrlParams(request);
//        for (Map.Entry entry : urlParams.entrySet()) {
//            result.put((String) entry.getKey(), (String) entry.getValue());
//        }
//
//        //请求body体里的参数
//        Map<String, String> allRequestParam = new HashMap<>();
//        if (StringUtils.equals(HttpMethod.GET.name(), request.getMethod())) {
//            // get请求不需要拿body参数
//        } else {
//            allRequestParam = getAllRequestParam(request);
//        }
//
//        //将URL的参数和body参数进行合并
//        if (allRequestParam != null) {
//            for (Map.Entry<String, String> entry : allRequestParam.entrySet()) {
//                result.put(entry.getKey(), entry.getValue());
//            }
//        }
        return result;
    }

    /**
     * 将URL请求参数转换成Map
     *
     * @param request
     */
    public static Map<String, String> getUrlParams(Request request) {
        return null;
    }

}
