package com.lihao.blob.data.network;

import com.lihao.blob.base.RetrofitClient;
import com.lihao.blob.data.network.service.ForumService;
import com.lihao.blob.data.network.service.LogService;

/**
 * api管理
 *
 * @author lihao
 * &#064;date  2024/11/27--10:19
 * @since 1.0
 */
public class ApiManager {
    private static LogService logService;
    private static ForumService forumService;
    public static LogService getUserService() {
        if (logService == null) {
            logService = RetrofitClient.getInstance().create(LogService.class);
        }
        return logService;
    }
    public static ForumService getForumService() {
        if (forumService == null) {
            forumService = RetrofitClient.getInstance().create(ForumService.class);
        }
        return forumService;
    }
}
