package com.zerowidth.network.api;

import com.zerowidth.network.api.user.UserInfoNetWorkApi;
import com.zerowidth.network.api.user.UserInfoNetWorkInfo;

/**
 * Created by meikai on 2020/05/16.
 */
public class ApiInitialize {

    public static void init() {

        UserInfoNetWorkApi.getInstance().init(new UserInfoNetWorkInfo());

    }

}
