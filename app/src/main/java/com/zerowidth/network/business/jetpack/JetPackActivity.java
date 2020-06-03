package com.zerowidth.network.business.jetpack;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.zerowidth.network.DemoApplication;
import com.zerowidth.network.R;
import com.zerowidth.network.api.beans.UserInfo;

public class JetPackActivity extends AppCompatActivity {

    private MainViewModel viewModel;

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

        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(DemoApplication.application);
        viewModel = new ViewModelProvider(getViewModelStore(), factory).get(MainViewModel.class);

        initLiveDataObserver();
    }

    private void initLiveDataObserver() {
        viewModel.userInfoLiveData.observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                Toast.makeText(getApplication(), "成功：" + new Gson().toJson(userInfo), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void doRequest() {
        viewModel.getUserInfo();
    }

}
