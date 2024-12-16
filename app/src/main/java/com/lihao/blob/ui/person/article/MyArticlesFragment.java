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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_articles, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // 假数据填充，可以替换为实际的数据请求
        articles = new ArrayList<>();

        articlesAdapter = new ArticlesAdapter(articles);
        recyclerView.setAdapter(articlesAdapter);

        return view;
    }
}

