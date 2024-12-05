package com.lihao.blob.base;

/**
 * 响应基类
 *
 * @author lihao
 * &#064;date  2024/12/1--19:11
 * @since 1.0
 */
public class ResponseBase {
    private boolean success;
    private String data;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
