package com.lihao.blob.data.network;

import com.lihao.blob.base.RetrofitClient;
import com.lihao.blob.data.network.service.FeedbackService;
import com.lihao.blob.data.network.service.ForumService;
import com.lihao.blob.data.network.service.LogService;
import com.lihao.blob.data.network.service.UserService;
import com.lihao.blob.data.repository.UserRepository;

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
    private static UserService userService;
    private static FeedbackService feedbackService;

    /**
     * 获取登陆接口
     * @return
     */
    public static LogService getLogService() {
        if (logService == null) {
            logService = RetrofitClient.getInstance().create(LogService.class);
        }
        return logService;
    }
    /**
     * 获取文章接口
     * @return
     */
    public static ForumService getForumService() {
        if (forumService == null) {
            forumService = RetrofitClient.getInstance().create(ForumService.class);
        }
        return forumService;
    }
    /**
     * 获取用户接口
     * @return
     */
    public static UserService getUserService(){
        if(userService == null){
            userService = RetrofitClient.getInstance().create(UserService.class);
        }
        return userService;
    }
    /**
     * 获取问题反馈接口
     * @return
     */
    public static FeedbackService getFeedbackService(){
        if(feedbackService == null){
            feedbackService = RetrofitClient.getInstance().create(FeedbackService.class);
        }
        return feedbackService;
    }
}
