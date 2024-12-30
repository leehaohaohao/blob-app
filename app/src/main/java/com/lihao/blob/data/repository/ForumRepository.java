package com.lihao.blob.data.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.lihao.blob.base.ResponsePack;
import com.lihao.blob.data.model.ArticleCoverDto;
import com.lihao.blob.data.model.ArticleDto;
import com.lihao.blob.data.model.UserInfoDto;
import com.lihao.blob.data.network.ApiManager;
import com.lihao.blob.data.network.service.ForumService;
import com.lihao.blob.data.repository.CallBack.ArticlesCallback;
import com.lihao.blob.data.response.ArticleResponse;
import com.lihao.blob.data.response.ArticlesResponse;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 论坛接口
 *
 * @author lihao
 * &#064;date  2024/12/1--18:34
 * @since 1.0
 */
public class ForumRepository {
    private ForumService forumService;
    private Context context;

    public ForumRepository(Context context) {
        forumService = ApiManager.getForumService();
        this.context=context;
    }
    /**
     * 获取文章接口封装
     * @param tagFuzzy 模糊标签
     * @param pageNum 页码
     * @param pageSize 一页显示数量
     * @param callback 回调
     */
    public void fetchArticles(String tagFuzzy, int pageNum, int pageSize, ArticlesCallback callback) {
        forumService.getArticles(tagFuzzy, pageNum, pageSize).enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 获取返回的 ArticleResponse
                    ArticleResponse articleResponse = response.body();
                    if (articleResponse.getData() != null && articleResponse.getData().getList() != null) {
                        List<ArticleCoverDto> articleCoverDtos = articleResponse.getData().getList();
                        if (articleCoverDtos != null && !articleCoverDtos.isEmpty()) {
                            // 请求成功，获取文章列表
                            callback.onArticlesFetched(articleCoverDtos);
                        } else {
                            // 文章列表为空的处理
                            Log.w("ForumRepository", "没有可以展示的文章");
                            callback.onFailure(new Exception("没有可以展示的文章"));
                        }
                    } else {
                        // 处理返回数据为空的情况
                        Log.e("ForumRepository", "文章列表为空");
                        callback.onFailure(new Exception("文章列表为空"));
                    }
                } else {
                    // 错误处理
                    Log.e("ArticleRepository", "未能获取文章");
                    callback.onFailure(new Exception("网络错误"));
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                // 失败处理，显示悬浮窗提示
                Log.e("ForumRepository", "请求失败！", t);
                showToast("请求失败！");
                callback.onFailure(t);
            }
        });
    }
    /**
     * 获取文章详情接口封装
     * @param postId 文章id
     * @param callback 回调
     */
    public void fetchArticleDetail(String postId,ArticlesCallback callback){
        forumService.getArticleDetail(postId).enqueue(new Callback<ResponsePack<ArticleDto>>() {
            @Override
            public void onResponse(Call<ResponsePack<ArticleDto>> call, Response<ResponsePack<ArticleDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponsePack<ArticleDto> responsePack = response.body();
                    if(responsePack.getData()!=null){
                        callback.onArticleFetched(responsePack.getData());
                    }else {
                        // 文章为空的处理
                        Log.w("ForumRepository", "找不到该文章");
                        callback.onFailure(new Exception("没有可以展示的文章"));
                    }
                }else{
                    // 错误处理
                    Log.e("ForumRepository", "未能获取文章");
                    callback.onFailure(new Exception("网络错误"));
                }
            }

            @Override
            public void onFailure(Call<ResponsePack<ArticleDto>> call, Throwable t) {
                // 失败处理，显示悬浮窗提示
                Log.e("ForumRepository", "请求失败！", t);
                showToast("请求失败！");
                callback.onFailure(t);
            }
        });
    }
    /**
     * 文章发布接口封装
     * @param content 内容
     * @param tag 标签
     * @param title 标题
     * @param file 封面
     * @param callback 回调
     */
    public void fetchPublish(String content, String tag, String title, File file,ArticlesCallback callback){
        // 构建 RequestBody
        RequestBody requestContent = RequestBody.create(MediaType.parse("text/plain"), content);
        RequestBody requestTag = RequestBody.create(MediaType.parse("text/plain"), tag);
        RequestBody requestTitle = RequestBody.create(MediaType.parse("text/plain"), title);
        // 仅当封面文件不为 null 时，才创建封面图片的 RequestBody
        MultipartBody.Part body = null;
        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }
        forumService.publish(requestContent,requestTag,requestTitle,body).enqueue(new Callback<ResponsePack<UserInfoDto>>() {
            @Override
            public void onResponse(Call<ResponsePack<UserInfoDto>> call, Response<ResponsePack<UserInfoDto>> response) {
                if(response.isSuccessful() && response.body() != null){
                    ResponsePack<UserInfoDto> responsePack = response.body();
                    if(responsePack.getSuccess()){
                        callback.onSuccess();
                    }else{
                        Log.e("ForumRepository", "发布失败");
                        callback.onFailure(new Exception("发布失败！"));
                    }
                }else{
                    // 错误处理
                    Log.e("ForumRepository", "发布失败");
                    callback.onFailure(new Exception("网络错误"));
                }
            }

            @Override
            public void onFailure(Call<ResponsePack<UserInfoDto>> call, Throwable t) {

            }
        });
    }
    /**
     * 点赞接口封装
     * @param postId 文章id
     * @param type 类型
     * @param status 状态
     * @param callback 回调
     */
    public void fetchLove(String postId, Integer status, Integer type, ArticlesCallback callback) {
        // 调用 love 接口
        forumService.love(postId, status, type).enqueue(new Callback<ResponsePack<String>>() {
            @Override
            public void onResponse(Call<ResponsePack<String>> call, Response<ResponsePack<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponsePack<String> responsePack = response.body();
                    if (responsePack.getSuccess()) {
                        // 成功回调
                        callback.onSuccess();
                    } else {
                        // 失败处理
                        Log.e("ForumRepository", "点赞/收藏失败: " + responsePack.getMessage());
                        callback.onFailure(new Exception("点赞/收藏失败"));
                    }
                } else {
                    // 错误处理
                    Log.e("ForumRepository", "网络请求失败");
                    callback.onFailure(new Exception("网络错误"));
                }
            }

            @Override
            public void onFailure(Call<ResponsePack<String>> call, Throwable t) {
                // 网络请求失败
                Log.e("ForumRepository", "请求失败: " + t.getMessage());
                callback.onFailure(new Exception("请求失败: " + t.getMessage()));
            }
        });
    }
    /**
     * 我的文章接口封装
     * @param pageNum 页码
     * @param pageSize 一页显示数量
     * @param sort 类型
     * @param callback 回调
     */
    public void fetchMyPost(Integer pageNum,Integer pageSize,Integer sort,ArticlesCallback callback){
        forumService.getMyPost(pageNum,pageSize,sort).enqueue(new Callback<ArticlesResponse>() {
            @Override
            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                if(response.isSuccessful()){
                    ArticlesResponse articlesResponse = response.body();
                    if(articlesResponse!=null && articlesResponse.getData() != null){
                        List<ArticleCoverDto> list = articlesResponse.getData();
                        callback.onArticlesFetched(list);
                    }else{
                        showToast("文章列表为空！");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArticlesResponse> call, Throwable t) {

            }
        });
    }

    /**
     * 我的喜欢接口封装
     * @param pageNum 页码
     * @param pageSize 一页显示数量
     * @param status 类型
     * @param callback 回调
     */
    public void fetchMyLike(Integer pageNum,Integer pageSize,Integer status,ArticlesCallback callback){
        forumService.getMyLike(pageNum,pageSize,status).enqueue(new Callback<ArticlesResponse>() {
            @Override
            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                if(response.isSuccessful()){
                    ArticlesResponse articlesResponse = response.body();
                    if(articlesResponse!=null && articlesResponse.getData() != null){
                        List<ArticleCoverDto> list = articlesResponse.getData();
                        callback.onArticlesFetched(list);
                    }else{
                        showToast("文章列表为空！");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArticlesResponse> call, Throwable t) {

            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}

