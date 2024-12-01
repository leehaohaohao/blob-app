package com.lihao.blob.data.response;

import com.lihao.blob.data.model.Article;

import java.util.List;

/**
 * classname
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
    // Getters and Setters

    public static class Data {
        private List<Article> list;
        private int total;

        public List<Article> getList() {
            return list;
        }

        public void setList(List<Article> list) {
            this.list = list;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
        // Getters and Setters
    }
}
