package com.lihao.blob.data.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lihao.blob.base.ResponsePack;
import com.lihao.blob.data.model.UserInfoDto;
import com.lihao.blob.data.network.ApiManager;
import com.lihao.blob.data.network.service.UserService;
import com.lihao.blob.data.repository.CallBack.UserCallBack;
import com.lihao.blob.utils.StrUtil;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    /**
     * 获取用户信息接口封装
     * @param userCallBack 回调
     */
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

    /**
     * 更改用户信息接口封装
     * @param name 名字
     * @param gender 性别
     * @param telephone 电话
     * @param file 头像
     * @param userCallBack 回调
     */
    public void updateUserInfo(String name, Integer gender, String telephone, MultipartBody.Part file, UserCallBack userCallBack){
        // 构建文本数据的 RequestBody
        RequestBody namePart = RequestBody.create(MultipartBody.FORM, name != null ? name : "");
        RequestBody genderPart = RequestBody.create(MultipartBody.FORM, gender != null ? gender.toString() : "");
        RequestBody telephonePart = RequestBody.create(MultipartBody.FORM, telephone != null ? telephone : "");
        UserInfoDto userInfoDto = new UserInfoDto();
        if(!StrUtil.isBlank(name)){
            userInfoDto.setName(name);
        }
        if( gender != null &&!StrUtil.isBlank(gender.toString())){
            userInfoDto.setGender(gender);
        }
        if(!StrUtil.isBlank(telephone)){
            userInfoDto.setTelephone(telephone);
        }
        // 调用接口并提交表单数据
        Call<ResponsePack<String>> call = userService.updateUserInfo(
                namePart,
                genderPart,
                telephonePart,
                file != null ? file : MultipartBody.Part.createFormData("file", "") // 如果没有文件，传递空文件
        );

        call.enqueue(new Callback<ResponsePack<String>>() {
            @Override
            public void onResponse(Call<ResponsePack<String>> call, Response<ResponsePack<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponsePack<String> responsePack = response.body();
                    if (responsePack.getSuccess()) {
                        userCallBack.getUserInfo(userInfoDto);
                    } else{
                        showToast("更新失败！");
                    }
                } else {
                    onFailure(call, new Exception("请求失败！"));
                }
            }

            @Override
            public void onFailure(Call<ResponsePack<String>> call, Throwable t) {
                Log.e("UserRepository", "请求失败！", t);
                showToast("请求失败！");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
