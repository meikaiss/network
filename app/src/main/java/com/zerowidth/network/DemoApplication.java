package com.zerowidth.network;

import android.app.Application;
import android.content.Context;

import com.zerowidth.network.api.ApiInitialize;

/**
 * Created by meikai on 2020/05/16.
 */
public class DemoApplication extends Application {

    public static DemoApplication application = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ApiInitialize.init();

    }
}
