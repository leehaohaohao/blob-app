package com.lihao.blob.data.network.service;

import android.os.IInterface;

import com.lihao.blob.base.ResponsePack;
import com.lihao.blob.data.model.ArticleDto;
import com.lihao.blob.data.model.UserInfoDto;
import com.lihao.blob.data.response.ArticleResponse;
import com.lihao.blob.data.response.ArticlesResponse;


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
    /**
     * 获取相关标签的文章
     * @param tagFuzzy 模糊标签
     * @param pageNum 页码
     * @param pageSize 一页显示数量
     * @return
     */
    @FormUrlEncoded
    @POST("forum/tag/post")
    Call<ArticleResponse> getArticles(
            @Field("tagFuzzy") String tagFuzzy,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    /**
     * 获取指定文章
     * @param postId 文章id
     * @return
     */
    @FormUrlEncoded
    @POST("forum/id/post")
    Call<ResponsePack<ArticleDto>> getArticleDetail(@Field("postId")String postId);

    /**
     * 文章发布接口
     * @param postContent 内容
     * @param tag 标签
     * @param title 标题
     * @param file 封面
     * @return
     */
    @Multipart
    @POST("forum/post")
    Call<ResponsePack<UserInfoDto>> publish(
            @Part("post_content") RequestBody postContent,
            @Part("tags") RequestBody tag,
            @Part("title") RequestBody title,
            @Part MultipartBody.Part file
    );

    /**
     * 点赞
     * @param postId 文章id
     * @param status 状态
     * @param type 类型
     * @return
     */
    @FormUrlEncoded
    @POST("forum/love/collect")
    Call<ResponsePack<String>> love(@Field("postId")String postId, @Field("status")Integer status, @Field("type")Integer type);

    /**
     * 获取我的文章
     * @param pageNum 页码
     * @param pageSize 一页显示数量
     * @param sort 排序
     * @return
     */
    @FormUrlEncoded
    @POST("forum/user/unapproval/post")
    Call<ArticlesResponse> getMyPost(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("sort") int sort
    );

    /**
     * 获取我的喜欢
     * @param pageNum 页码
     * @param pageSize 一页显示数量
     * @param status 状态
     * @return
     */
    @FormUrlEncoded
    @POST("forum/my/like/collect/post")
    Call<ArticlesResponse> getMyLike(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("status") int status
    );
}

