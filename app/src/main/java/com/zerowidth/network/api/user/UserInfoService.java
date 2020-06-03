package com.zerowidth.network.api.user;

import com.zerowidth.network.api.beans.UserInfo;
import com.zerowidth.networklib.beans.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by meikai on 2020/05/16.
 */
public interface UserInfoService {

    @GET("users")
    Observable<BaseResponse<UserInfo>> getUserInfo(
            @Query(value = "userid", encoded = true) String userid,
            @Query(value = "name", encoded = true) String name,
            @Query(value = "sign", encoded = true) String sign,
            @Query(value = "design", encoded = true) String x);



    @GET("users")
    Observable<BaseResponse<UserInfo>> getUserInfoNox(
            @Query(value = "userid", encoded = true) String userid,
            @Query(value = "name", encoded = true) String name,
            @Query(value = "sign", encoded = true) String sign);


    @GET("users/{userid}")
    Observable<BaseResponse<UserInfo>> getUserInfo2(
            @Path("userid") String userid);


}
