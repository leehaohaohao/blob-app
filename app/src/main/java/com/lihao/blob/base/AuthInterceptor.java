package com.lihao.blob.base;

import com.lihao.blob.utils.StrUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器
 *
 * @author lihao
 * &#064;date  2024/11/27--12:14
 * @since 1.0
 */
public class AuthInterceptor implements Interceptor {
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        // 如果 token 不为空，添加 Authorization header
        Request.Builder builder = originalRequest.newBuilder();
        if (!StrUtil.isBlank(token)) {
            builder.addHeader("Authorization", token);
        }
        return chain.proceed(builder.build());
    }
}
