package com.lihao.blob.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import com.lihao.blob.R;
import com.lihao.blob.data.model.ArticleCoverDto;
import com.lihao.blob.data.model.ArticleDto;
import com.lihao.blob.data.repository.CallBack.ArticlesCallback;
import com.lihao.blob.data.repository.ForumRepository;
import com.squareup.picasso.Picasso;

import io.noties.markwon.Markwon;
import java.util.List;

/**
 * 文章详情
 *
 * @author lihao
 * &#064;date  2024/12/3--13:43
 * @since 1.0
 */
public class ArticleDetailActivity extends AppCompatActivity {

    private static final String ARG_POST_ID = "post_id";
    private String postId;
    private TextView tvTitle, tvContent, tvLikeCount, tvCommentCount;
    private ImageView ivCover, ivLike, ivComment;
    private ForumRepository forumRepository;
    private ImageView ivBack;  // 引用返回按钮

    private Markwon markwon;  // Markwon 实例

    private ArticleDto articleDto; // 存储文章数据

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // 初始化视图
        tvTitle = findViewById(R.id.tvArticleTitle);
        tvContent = findViewById(R.id.tvArticleContent);
        ivCover = findViewById(R.id.ivArticleCover);
        ivLike = findViewById(R.id.ivLike);
        ivComment = findViewById(R.id.ivComment);
        tvLikeCount = findViewById(R.id.tvLikeCount);
        tvCommentCount = findViewById(R.id.tvCommentCount);
        ivBack = findViewById(R.id.ivBack);

        // 获取 postId
        if (getIntent() != null) {
            postId = getIntent().getStringExtra(ARG_POST_ID);
        }

        // 初始化 Repository
        forumRepository = new ForumRepository(this);

        // 初始化 Markwon
        markwon = Markwon.create(this);

        // 加载文章数据
        fetchArticleDetail();

        // 设置返回按钮点击事件
        ivBack.setOnClickListener(v -> {
            // 使用 OnBackPressedDispatcher 处理返回
            OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
            onBackPressedDispatcher.onBackPressed();  // 模拟返回操作
        });

        // 点赞按钮点击事件
        ivLike.setOnClickListener(v -> toggleLikeStatus());
    }

    private void fetchArticleDetail() {
        if (TextUtils.isEmpty(postId)) {
            showToast("出现错误！");
            return;
        }

        forumRepository.fetchArticleDetail(postId, new ArticlesCallback() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onArticlesFetched(List<ArticleCoverDto> articleCoverDtos) {

            }


            @Override
            public void onFailure(Throwable t) {
                showToast("加载文章失败: " + t.getMessage());
            }

            @Override
            public void onArticleFetched(ArticleDto article) {
                articleDto = article;  // 保存文章数据

                // 设置标题
                tvTitle.setText(article.getTitle());

                // 使用 Markwon 渲染 Markdown 内容
                String markdownContent = article.getPostContent();
                if (!TextUtils.isEmpty(markdownContent)) {
                    markwon.setMarkdown(tvContent, markdownContent);
                }

                // 设置点赞数量
                tvLikeCount.setText(String.valueOf(article.getPostLike()));

                // 根据 isLove 设置点赞图标
                if (article.isLove()) {
                    ivLike.setImageResource(R.drawable.ic_like_fill);  // 用户已点赞
                } else {
                    ivLike.setImageResource(R.drawable.ic_like_empty);   // 用户未点赞
                }

                // 加载封面图
                Picasso.get()
                        .load(article.getCover())
                        .placeholder(android.R.drawable.ic_menu_report_image)
                        .into(ivCover);
            }
        });
    }

    private void toggleLikeStatus() {
        // 更新本地的 isLove 状态
        boolean newLikeStatus = !articleDto.isLove();
        articleDto.setLove(newLikeStatus);

        // 更新点赞图标
        if (newLikeStatus) {
            ivLike.setImageResource(R.drawable.ic_like_fill);
        } else {
            ivLike.setImageResource(R.drawable.ic_like_empty);
        }

        // 更新点赞数量
        int currentLikes = articleDto.getPostLike();
        articleDto.setPostLike(newLikeStatus ? currentLikes + 1 : currentLikes - 1);
        tvLikeCount.setText(String.valueOf(articleDto.getPostLike()));

        // 向服务器提交点赞状态
        updateLikeStatusOnServer(newLikeStatus);
    }

    // 向服务器提交点赞状态
    private void updateLikeStatusOnServer(boolean isLiked) {
        // 这里可以实现更新点赞状态的网络请求逻辑
        forumRepository.fetchLove(postId,isLiked?0:1 ,0, new ArticlesCallback() {
            @Override
            public void onSuccess() {
                // 成功后，可以选择做一些 UI 更新或提示
            }

            @Override
            public void onArticlesFetched(List<ArticleCoverDto> articleCoverDtos) {

            }

            @Override
            public void onFailure(Throwable t) {
                // 失败时处理
                showToast("更新点赞状态失败: " + t.getMessage());
            }

            @Override
            public void onArticleFetched(ArticleDto articleDto) {

            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
