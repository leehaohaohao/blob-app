package com.lihao.blob.data.network.service;

import com.lihao.blob.base.ResponsePack;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 用户api接口
 *
 * @author lihao
 * &#064;date  2024/11/27--9:48
 * @since 1.0
 */
public interface LogService {
    @POST("login")
    @FormUrlEncoded
    Call<ResponsePack<String>> login(@Field("email") String email, @Field("password") String password);
    @POST("email/code")
    @FormUrlEncoded
    Call<ResponsePack<Void>> code(@Field("email") String email);
    @POST("register")
    @FormUrlEncoded
    Call<ResponsePack<String>> register(@Field("email") String email,@Field("password") String password,@Field("code")String code);
    @POST("forget")
    @FormUrlEncoded
    Call<ResponsePack<Void>> resetPassword(@Field("email") String email,@Field("password") String password,@Field("code")String code);
}
