package com.zerowidth.network.api.user;

import com.zerowidth.network.api.beans.UserInfo;
import com.zerowidth.networklib.beans.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by meikai on 2020/05/16.
 */
public interface UserInfoService {

    @GET("sendAuthCodeSMS/{phone}")
    Observable<BaseResponse<Void>> sendAuthCodeSMS(
            @Path("phone") String phone,
            @Query("send") boolean send);


    @GET
    Observable<BaseResponse<UserInfo>> executeGet(
            @Url String url,
            @QueryMap Map<String, String> maps
    );


    @GET("users")
    Observable<BaseResponse<UserInfo>> getUserInfoNox(
            @Query(value = "userid", encoded = true) String userid,
            @Query(value = "name", encoded = true) String name,
            @Query(value = "sign", encoded = true) String sign);


    @GET("users/{userid}")
    Observable<BaseResponse<UserInfo>> getUserInfo2(
            @Path("userid") String userid);


}
