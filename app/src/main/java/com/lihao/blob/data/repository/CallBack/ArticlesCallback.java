package com.lihao.blob.data.repository.CallBack;

import com.lihao.blob.data.model.ArticleCoverDto;
import com.lihao.blob.data.model.ArticleDto;

import java.util.List;

/**
 * 文章接口回调
 *
 * @author lihao
 * &#064;date  2024/12/1--18:39
 * @since 1.0
 */
public interface ArticlesCallback {
    void onSuccess();
    void onArticlesFetched(List<ArticleCoverDto> articleCoverDtos);
    void onFailure(Throwable t);
    void onArticleFetched(ArticleDto articleDto);
}
