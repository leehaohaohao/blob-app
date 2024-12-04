package com.lihao.blob.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.lihao.blob.R;
import com.lihao.blob.data.model.ArticleCoverDto;
import com.lihao.blob.data.model.ArticleDto;
import com.lihao.blob.data.repository.ArticlesCallback;
import com.lihao.blob.data.repository.ForumRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.noties.markwon.Markwon;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
                // 不需要处理
            }

            @Override
            public void onFailure(Throwable t) {
                showToast("加载文章失败: " + t.getMessage());
            }

            @Override
            public void onArticleFetched(ArticleDto articleDto) {
                tvTitle.setText(articleDto.getTitle());

                // 使用 Markwon 渲染 Markdown 内容
                String markdownContent = articleDto.getPostContent();  // 获取文章内容
                if (!TextUtils.isEmpty(markdownContent)) {
                    markwon.setMarkdown(tvContent, markdownContent);  // 渲染到 TextView
                }

                tvLikeCount.setText(String.valueOf(articleDto.getPostLike()));

                // 加载封面图
                Picasso.get()
                        .load(articleDto.getCover())
                        .placeholder(android.R.drawable.ic_menu_report_image)
                        .into(ivCover);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
