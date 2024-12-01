package com.lihao.blob.data.repository;

import com.lihao.blob.data.model.Article;

import java.util.List;

/**
 * classname
 *
 * @author lihao
 * &#064;date  2024/12/1--18:39
 * @since 1.0
 */
public interface ArticlesCallback {
    void onArticlesFetched(List<Article> articles);
    void onFailure(Throwable t);
}
