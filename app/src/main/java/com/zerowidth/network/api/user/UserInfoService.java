package com.zerowidth.network.api.user;

import com.zerowidth.network.api.beans.UserInfo;
import com.zerowidth.networklib.beans.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by meikai on 2020/05/16.
 */
public interface UserInfoService {


    @GET("weather/citys")
    Observable<BaseResponse<UserInfo>> getWeather(@Query("key") String key);


}
