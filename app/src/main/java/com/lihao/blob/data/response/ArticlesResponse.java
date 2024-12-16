package com.lihao.blob.data.response;

import com.lihao.blob.data.model.ArticleCoverDto;

import java.util.List;

/**
 * 文章列表响应
 *
 * @author lihao
 * &#064;date  2024/12/16--19:25
 * @since 1.0
 */
public class ArticlesResponse {
    private boolean success;
    private List<ArticleCoverDto> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ArticleCoverDto> getData() {
        return data;
    }

    public void setData(List<ArticleCoverDto> data) {
        this.data = data;
    }


}
