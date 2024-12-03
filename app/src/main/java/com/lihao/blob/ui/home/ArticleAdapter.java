package com.lihao.blob.ui.home;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lihao.blob.R;
import com.lihao.blob.data.model.ArticleCover;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 文章页面适配器
 *
 * @author lihao
 * &#064;date  2024/12/1--17:40
 * @since 1.0
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final List<ArticleCover> articleCoverList;

    public ArticleAdapter(List<ArticleCover> articleCoverList) {
        this.articleCoverList = articleCoverList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleCover articleCover = articleCoverList.get(position);

        // 设置标题和标签
        holder.tvTitle.setText(articleCover.getTitle());
        holder.tvTag.setText(articleCover.getTag());

        // 时间格式化
        String formattedTime = formatPostTime(articleCover.getPostTime());
        holder.tvPostInfo.setText("发布于 " + formattedTime);

        // 图片路径替换
        String coverUrl = articleCover.getCover();
        if (!TextUtils.isEmpty(coverUrl)) {
            coverUrl = coverUrl.replace("localhost", "10.0.2.2");
        }
        // 加载封面图片
        Picasso.get()
                .load(coverUrl)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(holder.ivCover);

        // 设置点赞
        holder.tvLikeCount.setText(String.valueOf(articleCover.getPostLike()));

        // 设置用户头像和名字
        String userPhotoUrl = articleCover.getOtherInfoDto().getUserInfoDto().getPhoto(); // 获取用户头像链接
        String userName = articleCover.getOtherInfoDto().getUserInfoDto().getName(); // 获取用户名
        if (!TextUtils.isEmpty(userPhotoUrl)) {
            // 使用 Picasso 加载头像
            Picasso.get()
                    .load(userPhotoUrl)
                    .placeholder(R.drawable.ic_default_avatar)
                    .into(holder.ivUserAvatar);
        }
        holder.tvUserName.setText(userName);

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

    @Override
    public int getItemCount() {
        return articleCoverList.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover, ivLike,ivUserAvatar;
        TextView tvTitle, tvTag, tvPostInfo, tvLikeCount,tvUserName;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivUserAvatar = itemView.findViewById(R.id.ivUserAvatar);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvPostInfo = itemView.findViewById(R.id.tvPostInfo);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvUserName = itemView.findViewById(R.id.tvUsername);
        }
    }
}
