package com.lihao.blob.base;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * http客户端基类
 *
 * @author lihao
 * &#064;date  2024/11/27--9:46
 * @since 1.0
 */
public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:9090/blob/";
    private static Retrofit retrofit;

    /**
     * 获取 Retrofit 实例
     * @return
     */
    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
