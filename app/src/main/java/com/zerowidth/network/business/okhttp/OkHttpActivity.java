package com.zerowidth.network.business.okhttp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.zerowidth.network.R;
import com.zerowidth.network.api.beans.UserInfo;
import com.zerowidth.network.api.user.UserInfoNetWorkApi;
import com.zerowidth.network.api.user.UserInfoService;
import com.zerowidth.networklib.beans.BaseResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by meikai on 2020/05/23.
 */
public class OkHttpActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        tvResult = findViewById(R.id.tv_result);

        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGet();
            }
        });


        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPost();
            }
        });

    }

    private void doGet() {

        UserInfoNetWorkApi.getInstance().getService(UserInfoService.class)
                .sendAuthCodeSMS("18062627592", false)
                //.getUserInfo("123", "meikai")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Void>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<Void> userInfoBaseResponse) {
                        Toast.makeText(getApplication(), "成功：" + new Gson().toJson(userInfoBaseResponse), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("mk", e.toString());
                        Toast.makeText(getApplication(), "失败：" + new Gson().toJson(e), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void doGet2() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //clientBuilder.dispatcher(new Dispatcher());
        // 增加 官方请求日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
                Log.e("abc", "执行 networkInterceptors 的 intercept");
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addNetworkInterceptor(loggingInterceptor);

        clientBuilder.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Log.e("abc2", "执行 networkInterceptors 的 intercept");
                return chain.proceed(chain.request());
            }
        });


        OkHttpClient okHttpClient = clientBuilder.build();

        Request.Builder builder = new Request.Builder();
        builder.header("device", "android");
        builder.addHeader("os", "android 10.0");
        builder.get();
        builder.url("http://www.baidu.com");
        builder.get(); //等价于  builder.method("GET", null);
        Request request = builder.build();

        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //同步
//                    Response response = call.execute();
//                    showResult(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        //call.cancel();


        //异步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                showResult(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                showResult(response.body().string());
            }
        });

    }


    private void doPost() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();


        RequestBody formBody = new FormBody.Builder()
                .add("size", "10")
                .addEncoded("nameEncoded", "valueEncoded")
                .build();

        Request.Builder builder = new Request.Builder();
        builder.post(formBody);
        builder.url("http://www.baidu.com");
        Request request = builder.build();

        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //同步post
                    Response response = call.execute();
                    showResult(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //异步post
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                showResult(e.getMessage());
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                showResult(response.body().string());
//            }
//        });
    }

    private void showResult(String result) {
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                tvResult.setText(result);
            }
        });
    }
}
