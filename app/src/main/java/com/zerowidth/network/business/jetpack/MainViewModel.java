package com.zerowidth.network.business.jetpack;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.zerowidth.network.api.beans.UserInfo;
import com.zerowidth.network.api.user.UserInfoNetWorkApi;
import com.zerowidth.network.api.user.UserInfoService;
import com.zerowidth.networklib.base.ApiUtil;
import com.zerowidth.networklib.beans.BaseResponse;
import com.zerowidth.networklib.observer.BaseObserver;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by meikai on 2020/05/18.
 */
public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<UserInfo> userInfoLiveData = new MutableLiveData<>();

    private List<Disposable> disposableList = new ArrayList<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void getUserInfo() {


    }


    @Override
    protected void onCleared() {
        super.onCleared();

        for (int i = 0; i < disposableList.size(); i++) {
            disposableList.get(i).dispose();
        }
    }

}
