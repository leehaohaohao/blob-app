package com.lihao.blob.data.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.lihao.blob.data.model.ArticleCover;
import com.lihao.blob.data.network.ApiManager;
import com.lihao.blob.data.network.service.ForumService;
import com.lihao.blob.data.response.ArticleResponse;

import java.util.List;

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
    }

    public void fetchArticles(String tagFuzzy, int pageNum, int pageSize, ArticlesCallback callback) {
        forumService.getArticles(tagFuzzy, pageNum, pageSize).enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 获取返回的 ArticleResponse
                    ArticleResponse articleResponse = response.body();
                    if (articleResponse.getData() != null && articleResponse.getData().getList() != null) {
                        List<ArticleCover> articleCovers = articleResponse.getData().getList();
                        if (articleCovers != null && !articleCovers.isEmpty()) {
                            // 请求成功，获取文章列表
                            callback.onArticlesFetched(articleCovers);
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
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}

