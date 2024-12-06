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
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.lihao.blob.R;
import com.lihao.blob.data.model.ArticleCoverDto;
import com.lihao.blob.data.model.ArticleDto;
import com.lihao.blob.data.repository.CallBack.ArticlesCallback;
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
public class HomeFragment extends Fragment{

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<ArticleCoverDto> currentArticleCoverDtos;
    private ForumRepository forumRepository;
    private ViewPager2 viewPager;

    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        currentArticleCoverDtos = new ArrayList<>();
        articleAdapter = new ArticleAdapter(currentArticleCoverDtos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(articleAdapter);
        //论坛接口
        forumRepository = new ForumRepository(getContext());
        //设置标签栏
        tabLayout.addTab(tabLayout.newTab().setText("推荐"));
        tabLayout.addTab(tabLayout.newTab().setText("古代科学"));
        tabLayout.addTab(tabLayout.newTab().setText("中国四大发明"));
        tabLayout.addTab(tabLayout.newTab().setText("医学与生物科学"));
        tabLayout.addTab(tabLayout.newTab().setText("数学与天文学"));
        tabLayout.addTab(tabLayout.newTab().setText("工程与建筑"));
        tabLayout.addTab(tabLayout.newTab().setText("农业与自然科学"));
        tabLayout.addTab(tabLayout.newTab().setText("科学思想与哲学"));
        tabLayout.addTab(tabLayout.newTab().setText("科技人物与故事"));
        tabLayout.addTab(tabLayout.newTab().setText("古代兵器与战争技术"));
        tabLayout.addTab(tabLayout.newTab().setText("科学与文化"));
        //选择标签
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fetchArticlesForTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                fetchArticlesForTab(tab.getPosition());
            }
        });
        fetchArticlesForTab(0);

        return view;
    }
    private void fetchArticlesForTab(int position) {
        // 显示加载中的动画
        recyclerView.setAlpha(0f);
        recyclerView.animate().alpha(1f).setDuration(300);
        forumRepository.fetchArticles(getTabTag(position), 1, 10, new ArticlesCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onArticlesFetched(List<ArticleCoverDto> articleCoverDtos) {
                // 更新文章列表
                currentArticleCoverDtos.clear();
                currentArticleCoverDtos.addAll(articleCoverDtos);
                articleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                // 清空数据并显示错误信息
                currentArticleCoverDtos.clear();
                articleAdapter.notifyDataSetChanged();
                showToast("加载失败: " + t.getMessage());
            }

            @Override
            public void onArticleFetched(ArticleDto articleDto) {

            }
        });
    }
    private String getTabTag(int position) {
        switch (position) {
            case 0:
                return "random_post";
            case 1:
                return "古代科学";
            case 2:
                return "中国四大发明";
            case 3:
                return "医学与生物科学";
            case 4:
                return "数学与天文学";
            case 5:
                return "工程与建筑";
            case 6:
                return "农业与自然科学";
            case 7:
                return "科学思想与哲学";
            case 8:
                return "科技人物与故事";
            case 9:
                return "古代兵器与战争技术";
            case 10:
                return "科学与文化";
            default:
                return "random_post";
        }
    }

    private void showToast(String message) {
        // 使用 Toast 来显示悬浮窗，默认显示 3 秒后消失
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
