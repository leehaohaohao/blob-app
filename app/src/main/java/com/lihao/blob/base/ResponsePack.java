package com.lihao.blob.base;

import java.io.Serializable;

/**
 * 响应包装类
 *
 * @author lihao
 * &#064;date  2024/11/27--9:50
 * @since 1.0
 */
public class ResponsePack<T> implements Serializable {
    /**
     * 响应是否成功
     */
    private Boolean success;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 错误信息
     */
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * 成功的响应（没有 message）
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponsePack<T> success(T data) {
        ResponsePack<T> response = new ResponsePack<>();
        response.setSuccess(true);
        response.setData(data);
        response.setMessage(null);
        return response;
    }
    /**
     * 成功的响应，不带数据
     * @return
     * @param <T>
     */
    public static <T> ResponsePack<T> success() {
        return success(null);
    }

    /**
     * 失败的响应，带错误信息
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ResponsePack<T> failure(String message) {
        ResponsePack<T> response = new ResponsePack<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(null);
        return response;
    }
    /**
     * 失败的响应，带错误信息和数据（例如错误详情）
     * @param message
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponsePack<T> failure(String message, T data) {
        ResponsePack<T> response = new ResponsePack<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
    /**
     * 来自网络错误的响应
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ResponsePack<T> fromNetworkError(String message) {
        return failure("Network error: " + message);
    }
    /**
     * 创建一个空的成功响应
     * @return
     * @param <T>
     */
    public static <T> ResponsePack<T> emptyResponse() {
        ResponsePack<T> response = new ResponsePack<>();
        response.setSuccess(true);
        response.setData(null);
        response.setMessage(null);
        return response;
    }
}
