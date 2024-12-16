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
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * classname
 *
 * @author lihao
 * &#064;date  2024/12/16--18:20
 * @since 1.0
 */
public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.ArticlesViewHolder> {

    private static List<ArticleCoverDto> articles = Collections.emptyList();;

    public LikesAdapter(List<ArticleCoverDto> articles) {
        this.articles = articles;
    }


    @NonNull
    @Override
    public LikesAdapter.ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_like, parent, false);
        return new LikesAdapter.ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikesAdapter.ArticlesViewHolder holder, int position) {
        ArticleCoverDto article = articles.get(position);
        Picasso.get().load(article.getCover()).into(holder.ivCover);
        Picasso.get().load(article.getOtherInfoDto().getUserInfoDto().getPhoto()).into(holder.ivAuthorPhoto);
        holder.tvTitle.setText(article.getTitle());
        holder.tvTag.setText(article.getTag());
        holder.tvAuthorName.setText(article.getOtherInfoDto().getUserInfoDto().getName());
        holder.tvPostTime.setText(formatPostTime(article.getPostTime()));
        holder.tvLikes.setText(article.getPostLike()+" 喜欢");
        holder.tvCollects.setText(article.getCollect()+" 收藏");
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }
    // 格式化时间
    private String formatPostTime(String postTime) {
        // 输入的时间格式是 "2024-09-10T06:35:19.000+00:00"
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            Date date = inputFormat.parse(postTime);
            if (date != null) {
                return outputFormat.format(date); // 返回正常格式化的时间
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return postTime; // 如果解析失败，返回原始时间
    }
    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {
        // 视图控件
        private ImageView ivCover,ivAuthorPhoto;
        private TextView tvTitle,tvAuthorName;
        private TextView tvTag,tvPostTime,tvLikes,tvCollects;

        public ArticlesViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvAuthorName = itemView.findViewById(R.id.tvAuthorName);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvCollects = itemView.findViewById(R.id.tvCollects);
            ivAuthorPhoto = itemView.findViewById(R.id.ivAuthorPhoto);
            itemView.setOnClickListener(v->{
                // 获取当前条目的 postId
                ArticleCoverDto articleCover = articles.get(getAdapterPosition());
                String postId = articleCover.getPostId();
                // 创建 Intent 来跳转到 ArticleDetailActivity
                Intent intent = new Intent(itemView.getContext(), ArticleDetailActivity.class);
                intent.putExtra("post_id", postId);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}

