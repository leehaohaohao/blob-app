package com.lihao.blob.data.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lihao.blob.base.ResponsePack;
import com.lihao.blob.data.model.UserInfoDto;
import com.lihao.blob.data.network.ApiManager;
import com.lihao.blob.data.network.service.UserService;
import com.lihao.blob.data.repository.CallBack.UserCallBack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 用户接口
 *
 * @author lihao
 * &#064;date  2024/12/6--15:53
 * @since 1.0
 */
public class UserRepository {
    private Context context;
    private UserService userService;
    public UserRepository(Context context){
        this.context=context;
        this.userService = ApiManager.getUserService();
    }
    //获取用户信息
    public void fetchUserInfo(UserCallBack userCallBack){
        userService.getUserInfo().enqueue(new Callback<ResponsePack<UserInfoDto>>() {
            @Override
            public void onResponse(Call<ResponsePack<UserInfoDto>> call, Response<ResponsePack<UserInfoDto>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    ResponsePack<UserInfoDto> responsePack = response.body();
                    UserInfoDto userInfoDto = responsePack.getData();
                    if(userInfoDto != null){
                        userCallBack.getUserInfo(userInfoDto);
                    }else{
                        showToast("服务器错误！");
                    }
                }else{
                    onFailure(call,new Exception("请求失败！"));
                }
            }
            @Override
            public void onFailure(Call<ResponsePack<UserInfoDto>> call, Throwable t) {
                // 失败处理，显示悬浮窗提示
                Log.e("UserRepository", "请求失败！", t);
                showToast("请求失败！");
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
