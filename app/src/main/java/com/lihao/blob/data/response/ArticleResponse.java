package com.lihao.blob.data.response;

import com.lihao.blob.data.model.ArticleCover;

import java.util.List;

/**
 * 文章接口响应
 *
 * @author lihao
 * &#064;date  2024/12/1--18:36
 * @since 1.0
 */
public class ArticleResponse {
    private boolean success;
    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<ArticleCover> list;
        private int total;

        public List<ArticleCover> getList() {
            return list;
        }

        public void setList(List<ArticleCover> list) {
            this.list = list;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
