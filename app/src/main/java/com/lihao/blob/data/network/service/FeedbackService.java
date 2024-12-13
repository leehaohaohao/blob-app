package com.lihao.blob.data.network.service;

import com.lihao.blob.base.ResponsePack;
import com.lihao.blob.data.model.UserInfoDto;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 反馈接口
 *
 * @author lihao
 * &#064;date  2024/12/13--18:25
 * @since 1.0
 */
public interface FeedbackService {
    @Multipart
    @POST("error/publish")
    Call<ResponsePack<String>> publish(
            @Part("content") RequestBody content,
            @Part("status") RequestBody status,
            @Part MultipartBody.Part file
    );
}