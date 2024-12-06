package com.lihao.blob.data.network.service;

import com.lihao.blob.base.ResponsePack;
import com.lihao.blob.data.model.UserInfoDto;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 用户接口
 *
 * @author lihao
 * &#064;date  2024/12/6--15:22
 * @since 1.0
 */
public interface UserService {
    @GET("user/info")
    Call<ResponsePack<UserInfoDto>> getUserInfo();
    @Multipart
    @POST("user/updateinfo")
    Call<ResponsePack<String>> updateUserInfo(
            @Part("name")String name,
            @Part("gender")Integer gender,
            @Part("telephone")String telephone,
            @Part("file") MultipartBody.Part file);
}