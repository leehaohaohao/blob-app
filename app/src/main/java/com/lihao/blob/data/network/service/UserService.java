package com.lihao.blob.data.network.service;

import com.lihao.blob.base.ResponsePack;
import com.lihao.blob.data.model.UserInfoDto;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    /**
     * 获取用户信息
     * @return
     */
    @GET("user/info")
    Call<ResponsePack<UserInfoDto>> getUserInfo();

    /**
     * 更新用户信息
     * @param name 名字
     * @param gender 性别
     * @param telephone 电话
     * @param file 头像
     * @return
     */
    @Multipart
    @POST("user/updateInfo")
    Call<ResponsePack<String>> updateUserInfo(
            @Part("name") RequestBody name,
            @Part("gender")RequestBody gender,
            @Part("telephone")RequestBody telephone,
            @Part MultipartBody.Part file);
}