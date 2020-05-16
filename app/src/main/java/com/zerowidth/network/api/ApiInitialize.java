package com.zerowidth.network.api;

import com.zerowidth.network.api.user.UserInfoNetWorkInfo;
import com.zerowidth.networklib.base.NetWorkApi;

/**
 * Created by meikai on 2020/05/16.
 */
public class ApiInitialize {

    public static void init(){
        NetWorkApi.init(new UserInfoNetWorkInfo());
    }
}
