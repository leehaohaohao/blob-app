package com.lihao.blob.data.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lihao.blob.base.ResponsePack;
import com.lihao.blob.data.network.ApiManager;
import com.lihao.blob.data.network.service.FeedbackService;
import com.lihao.blob.data.repository.CallBack.FeedbackCallBack;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 反馈接口
 *
 * @author lihao
 * &#064;date  2024/12/13--18:30
 * @since 1.0
 */
public class FeedbackRepository {
    private FeedbackService feedbackService;
    private Context context;

    public FeedbackRepository(Context context) {
        feedbackService = ApiManager.getFeedbackService();
        this.context=context;
    }

    /**
     * 文章发布接口封装方法
     * @param content 内容
     * @param status 类型
     * @param file 封面
     * @param feedbackCallBack 回调
     */
    public void fetchFeedbackPublish(String content, Integer status, File file, FeedbackCallBack feedbackCallBack){
        // 构建 RequestBody
        RequestBody requestContent = RequestBody.create(MediaType.parse("text/plain"), content);
        RequestBody requestStatus = RequestBody.create(MediaType.parse("text/plain"), status.toString());
        // 仅当封面文件不为 null 时，才创建封面图片的 RequestBody
        MultipartBody.Part body = null;
        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }
        feedbackService.publish(requestContent,requestStatus,body).enqueue(new Callback<ResponsePack<String>>() {
            @Override
            public void onResponse(Call<ResponsePack<String>> call, Response<ResponsePack<String>> response) {
                if(response.isSuccessful() && response.body() != null){
                    ResponsePack<String> responsePack = response.body();
                    if(responsePack.getSuccess()){
                        feedbackCallBack.getData(responsePack.getData());
                    }else{
                        Log.e("FeedbackRepository", "反馈失败");
                        showToast("反馈失败！");
                    }
                }else{
                    // 错误处理
                    Log.e("FeedbackRepository", "反馈失败");
                    showToast("网络错误！");
                }
            }
            @Override
            public void onFailure(Call<ResponsePack<String>> call, Throwable t) {
                // 网络请求失败
                Log.e("ForumRepository", "请求失败: " + t.getMessage());
            }
        });

    }
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
