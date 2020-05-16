package com.zerowidth.network;

import android.app.Application;

import com.zerowidth.network.api.ApiInitialize;

/**
 * Created by meikai on 2020/05/16.
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ApiInitialize.init();

    }
}
