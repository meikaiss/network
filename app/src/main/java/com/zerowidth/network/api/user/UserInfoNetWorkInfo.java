package com.zerowidth.network.api.user;

import com.zerowidth.network.BuildConfig;
import com.zerowidth.networklib.base.INetWorkRequiredInfo;

/**
 * Created by meikai on 2020/05/16.
 */
public class UserInfoNetWorkInfo implements INetWorkRequiredInfo {
    @Override
    public String getAppVersionName() {
        return "1.0";
    }

    @Override
    public String getAppVersionCode() {
        return "1";
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
