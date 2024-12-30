package com.lihao.blob.ui.person.article;

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
import com.lihao.blob.data.repository.CallBack.ArticlesCallback;
import com.lihao.blob.data.repository.ForumRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的喜欢列表
 *
 * @author lihao
 * &#064;date  2024/12/16--18:13
 * @since 1.0
 */
public class MyLikesFragment extends Fragment {
    //控件
    private RecyclerView recyclerView;
    private LikesAdapter likesAdapter;
    private List<ArticleCoverDto> likedArticles;
    private ForumRepository forumRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_likes, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        likedArticles = new ArrayList<>();
        likesAdapter = new LikesAdapter(likedArticles);
        recyclerView.setAdapter(likesAdapter);
        forumRepository = new ForumRepository(getContext());
        forumRepository.fetchMyLike(1, 10, 0, new ArticlesCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onArticlesFetched(List<ArticleCoverDto> articleCoverDtos) {
                likedArticles.clear();
                likedArticles.addAll(articleCoverDtos);
                likesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onArticleFetched(ArticleDto articleDto) {

            }
        });
        return view;
    }
}

