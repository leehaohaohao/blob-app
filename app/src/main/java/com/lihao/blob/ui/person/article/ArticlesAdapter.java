package com.lihao.blob.ui.person.article;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lihao.blob.R;
import com.lihao.blob.data.model.ArticleCoverDto;
import com.lihao.blob.ui.home.ArticleDetailActivity;
import com.lihao.blob.utils.StrUtil;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * 文章列表适配器
 *
 * @author lihao
 * &#064;date  2024/12/16--18:20
 * @since 1.0
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {

    private static List<ArticleCoverDto> articles = Collections.emptyList();

    public ArticlesAdapter(List<ArticleCoverDto> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article, parent, false);
        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, int position) {
        ArticleCoverDto article = articles.get(position);
        Picasso.get().load(article.getCover()).into(holder.ivCover);
        holder.tvTitle.setText(article.getTitle());
        holder.tvTag.setText(article.getTag());
        holder.tvPostTime.setText(StrUtil.formatPostTime(article.getPostTime()));
        holder.tvLikes.setText(article.getPostLike()+" 喜欢");
        holder.tvCollects.setText(article.getCollect()+" 收藏");

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {
        //控件
        private ImageView ivCover;
        private TextView tvTitle;
        private TextView tvTag,tvPostTime,tvLikes,tvCollects;

        public ArticlesViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvCollects = itemView.findViewById(R.id.tvCollects);
            itemView.setOnClickListener(v->{
                // 获取当前条目的 postId
                ArticleCoverDto articleCover = articles.get(getAdapterPosition());
                String postId = articleCover.getPostId();
                //跳转到 ArticleDetailActivity
                Intent intent = new Intent(itemView.getContext(), ArticleDetailActivity.class);
                intent.putExtra("post_id", postId);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
