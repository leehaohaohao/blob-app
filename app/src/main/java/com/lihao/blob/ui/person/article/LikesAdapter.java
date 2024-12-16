package com.lihao.blob.ui.person.article;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lihao.blob.R;
import com.lihao.blob.data.model.ArticleCoverDto;

import java.util.List;

/**
 * classname
 *
 * @author lihao
 * &#064;date  2024/12/16--18:20
 * @since 1.0
 */
public class LikesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private List<ArticleCoverDto> articles;

    public LikesAdapter(List<ArticleCoverDto> articles) {
        this.articles = articles;
    }


    @NonNull
    @Override
    public ArticlesAdapter.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesAdapter.ArticleViewHolder holder, int position) {
        ArticleCoverDto article = articles.get(position);
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        public ArticleViewHolder(View itemView) {
            super(itemView);
        }
    }
}

