package com.lihao.blob.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lihao.blob.R;
import com.lihao.blob.data.model.Article;
import com.lihao.blob.data.repository.ArticlesCallback;
import com.lihao.blob.data.repository.ForumRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 *
 * @author lihao
 * &#064;date  2024/11/30--16:21
 * @since 1.0
 */
public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> currentArticles;

    private ForumRepository forumRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Initialize article list and adapter
        currentArticles = new ArrayList<>();
        articleAdapter = new ArticleAdapter(currentArticles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(articleAdapter);

        // Initialize ForumRepository
        forumRepository = new ForumRepository(getContext());

        // Set up TabLayout with tabs
        tabLayout.addTab(tabLayout.newTab().setText("推荐"));
        tabLayout.addTab(tabLayout.newTab().setText("科学家"));
        tabLayout.addTab(tabLayout.newTab().setText("历史事件"));

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fetch new articles based on the selected tab
                fetchArticlesForTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optional: Refresh content on reselect
                fetchArticlesForTab(tab.getPosition());
            }
        });

        // Load initial data
        fetchArticlesForTab(0);

        return view;
    }

    private void fetchArticlesForTab(int position) {
        // 显示加载中的动画
        recyclerView.setAlpha(0f);
        recyclerView.animate().alpha(1f).setDuration(300);

        forumRepository.fetchArticles(getTabTag(position), 1, 5, new ArticlesCallback() {
            @Override
            public void onArticlesFetched(List<Article> articles) {
                // 更新文章列表
                currentArticles.clear();
                currentArticles.addAll(articles);
                articleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                // 使用 Toast 显示错误提示
                // 清空数据并显示错误信息
                currentArticles.clear();
                articleAdapter.notifyDataSetChanged();
                showToast("加载失败: " + t.getMessage());
            }
        });
    }

    private String getTabTag(int position) {
        switch (position) {
            case 0:
                return "random_post";
            case 1:
                return "科学家";
            case 2:
                return "历史事件";
            default:
                return "推荐";
        }
    }

    private void showToast(String message) {
        // 使用 Toast 来显示悬浮窗，默认显示 3 秒后消失
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}