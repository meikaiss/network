package com.zerowidth.networklib.base;

/**
 * Created by meikai on 2020/05/16.
 */
public interface INetWorkRequiredInfo {
    String getAppVersionName();
    String getAppVersionCode();
    boolean isDebug();

    boolean design();
}
