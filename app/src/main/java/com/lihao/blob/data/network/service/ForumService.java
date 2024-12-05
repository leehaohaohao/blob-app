package com.lihao.blob.data.network.service;

import android.os.IInterface;

import com.lihao.blob.base.ResponsePack;
import com.lihao.blob.data.model.ArticleDto;
import com.lihao.blob.data.model.UserInfoDto;
import com.lihao.blob.data.response.ArticleResponse;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 论坛接口
 *
 * @author lihao
 * &#064;date  2024/12/1--18:25
 * @since 1.0
 */
public interface ForumService {
    @FormUrlEncoded
    @POST("forum/tag/post")
    Call<ArticleResponse> getArticles(
            @Field("tagFuzzy") String tagFuzzy,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );
    @FormUrlEncoded
    @POST("forum/id/post")
    Call<ResponsePack<ArticleDto>> getArticleDetail(@Field("postId")String postId);
    @Multipart
    @POST("forum/post")
    Call<ResponsePack<UserInfoDto>> publish(
            @Part("post_content") RequestBody postContent,
            @Part("tags") RequestBody tag,
            @Part("title") RequestBody title,
            @Part MultipartBody.Part file
    );
    @FormUrlEncoded
    @POST("forum/love/collect")
    Call<ResponsePack<String>> love(@Field("postId")String postId, @Field("status")Integer status, @Field("type")Integer type);
}

