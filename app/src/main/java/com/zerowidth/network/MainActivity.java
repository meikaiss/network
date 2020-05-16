package com.zerowidth.network;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.zerowidth.network.api.beans.UserInfo;
import com.zerowidth.network.api.user.UserInfoService;
import com.zerowidth.networklib.observer.BaseObserver;
import com.zerowidth.networklib.beans.BaseResponse;
import com.zerowidth.networklib.base.NetWorkApi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRequest();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void doRequest() {

        NetWorkApi.getService(UserInfoService.class)
                .getWeather("***a7558b2e0bedaa19673f74a6809ce")
                .compose(NetWorkApi.applySchedulers(new BaseObserver<BaseResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserInfo> result) {
                        Toast.makeText(MainActivity.this, new Gson().toJson(result), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Toast.makeText(MainActivity.this, new Gson().toJson(e), Toast.LENGTH_SHORT).show();
                    }
                }));

    }

}
