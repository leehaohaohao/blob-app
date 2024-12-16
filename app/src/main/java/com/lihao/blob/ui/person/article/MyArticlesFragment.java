package com.lihao.blob.ui.person.article;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lihao.blob.R;
import com.lihao.blob.data.model.ArticleCoverDto;
import com.lihao.blob.data.model.ArticleDto;
import com.lihao.blob.data.network.ApiManager;
import com.lihao.blob.data.network.service.ForumService;
import com.lihao.blob.data.repository.CallBack.ArticlesCallback;
import com.lihao.blob.data.repository.ForumRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * classname
 *
 * @author lihao
 * &#064;date  2024/12/16--18:14
 * @since 1.0
 */
public class MyArticlesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArticlesAdapter articlesAdapter;
    private List<ArticleCoverDto> articles;
    private ForumRepository forumRepository;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_articles, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(articlesAdapter);
        forumRepository=new ForumRepository(getContext());
        loadArticles();
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        loadArticles();
//    }

    private void loadArticles() {
        forumRepository.fetchMyPost(1, 10, 2, new ArticlesCallback() {
            @Override
            public void onSuccess() {
                // Success callback if needed
            }

            @Override
            public void onArticlesFetched(List<ArticleCoverDto> articleCoverDtos) {
                articles.clear();
                articles.addAll(articleCoverDtos);
                articlesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                // Handle failure
            }

            @Override
            public void onArticleFetched(ArticleDto articleDto) {
                // Handle single article fetch if needed
            }
        });
    }
}

