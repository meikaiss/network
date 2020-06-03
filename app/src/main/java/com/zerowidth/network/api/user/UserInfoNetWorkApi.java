package com.zerowidth.network.api.user;

import com.zerowidth.networklib.base.INetWorkRequiredInfo;
import com.zerowidth.networklib.base.NetWorkApi;

import okhttp3.Interceptor;

/**
 * Created by meikai on 2020/05/16.
 */
public class UserInfoNetWorkApi extends NetWorkApi {

    private static UserInfoNetWorkApi instance;

    public static UserInfoNetWorkApi getInstance() {
        if (instance == null) {
            synchronized (UserInfoNetWorkApi.class) {
                if (instance == null) {
                    instance = new UserInfoNetWorkApi();
                }
            }
        }

        return instance;
    }

    @Override
    public void init(INetWorkRequiredInfo netWorkRequiredInfo) {
        super.init(netWorkRequiredInfo);
    }

    @Override
    protected String getBaseUrl() {

        return "http://192.168.0.106:8080/api/v1/";
        //return "http://121.196.125.228/albumServer/api/v1/";
    }

    @Override
    protected Interceptor getInterceptor() {
        return null;
        //return new HttpRequestInterceptor(iNetWorkRequiredInfo);
    }

}
