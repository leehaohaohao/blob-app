package com.lihao.blob.data.repository;

import com.lihao.blob.data.model.ArticleCover;

import java.util.List;

/**
 * classname
 *
 * @author lihao
 * &#064;date  2024/12/1--18:39
 * @since 1.0
 */
public interface ArticlesCallback {
    void onArticlesFetched(List<ArticleCover> articleCovers);
    void onFailure(Throwable t);
}
