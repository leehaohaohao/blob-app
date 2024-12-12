package com.lihao.blob.base;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
    //private static final String BASE_URL = "http://10.0.2.2:9090/blob/";
    private static final String BASE_URL = "http://121.40.154.188:9090/blob/";
    private static Retrofit retrofit;
    private static final AuthInterceptor authInterceptor = new AuthInterceptor();

    /**
     * 获取 Retrofit 实例
     * @return
     */
    public static Retrofit getInstance() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 设置token
     * @param token
     */
    public static void setToken(String token) {
        authInterceptor.setToken(token);
    }
}
