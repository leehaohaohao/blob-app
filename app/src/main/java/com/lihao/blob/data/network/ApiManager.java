package com.lihao.blob.data.network;

import com.lihao.blob.base.RetrofitClient;
import com.lihao.blob.data.network.service.UserService;

/**
 * api管理
 *
 * @author lihao
 * &#064;date  2024/11/27--10:19
 * @since 1.0
 */
public class ApiManager {
    private static UserService userService;
    public static UserService getUserService() {
        if (userService == null) {
            userService = RetrofitClient.getInstance().create(UserService.class);
        }
        return userService;
    }
}
