package com.lihao.blob.data.network.service;

import com.lihao.blob.data.response.ArticleResponse;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
}

