package com.lihao.blob.ui.login;


import android.os.Bundle;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lihao.blob.R;
import com.lihao.blob.base.ResponsePack;

import com.lihao.blob.data.model.RegisterDto;
import com.lihao.blob.data.network.ApiManager;
import com.lihao.blob.data.network.service.LogService;
import com.lihao.blob.utils.StrUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 注册
 *
 * @author lihao
 * &#064;date  2024/11/27--12:52
 * @since 1.0
 */

public class RegisterFragment extends Fragment {

    private EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextVerifyCode;
    private TextView textViewError;
    private Button buttonRegister, buttonGetVerifyCode;
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // 初始化视图
        editTextEmail = view.findViewById(R.id.registerEmail);
        editTextPassword = view.findViewById(R.id.registerPassword);
        editTextConfirmPassword = view.findViewById(R.id.registerConfirmPassword);
        editTextVerifyCode = view.findViewById(R.id.registerCode);
        textViewError = view.findViewById(R.id.textViewError);
        buttonRegister = view.findViewById(R.id.buttonRegister);
        buttonGetVerifyCode = view.findViewById(R.id.getRegisterCode);

        // 获取验证码按钮点击事件
        buttonGetVerifyCode.setOnClickListener(v -> sendVerifyCode());

        // 注册按钮点击事件
        buttonRegister.setOnClickListener(v -> attemptRegister());

        // 跳转到登录页面
        TextView textViewLogin = view.findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(v -> navigateToLogin());

        return view;
    }

    /**
     * 获取验证码
     */
    private void sendVerifyCode() {
        String email = editTextEmail.getText().toString().trim();
        if (StrUtil.isBlank(email)) {
            showError("请输入邮箱！");
            return;
        }
        // 发起获取验证码请求
        LogService logService = ApiManager.getLogService();
        Call<ResponsePack<String>> call = logService.code(email);
        call.enqueue(new Callback<ResponsePack<String>>() {
            @Override
            public void onResponse(Call<ResponsePack<String>> call, Response<ResponsePack<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponsePack<String> responsePack = response.body();
                    if (responsePack.getSuccess()) {
                        startCountDown();
                    } else {
                        showError(responsePack.getMessage());
                    }
                } else {
                    showError("验证码发送失败！");
                }
            }

            @Override
            public void onFailure(Call<ResponsePack<String>> call, Throwable t) {
                showError("网络请求失败：" + t.getMessage());
            }
        });
    }

    /**
     * 验证码倒计时
     */
    private void startCountDown() {
        buttonGetVerifyCode.setEnabled(false);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                buttonGetVerifyCode.setText(millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                buttonGetVerifyCode.setText("重新获取");
                buttonGetVerifyCode.setEnabled(true);
            }
        };
        countDownTimer.start();
    }

    /**
     * 注册
     */
    private void attemptRegister() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String verifyCode = editTextVerifyCode.getText().toString().trim();
        // 表单验证
        if (StrUtil.isBlank(email, password, confirmPassword, verifyCode)) {
            showError("邮箱、密码或验证码不能为空！");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showError("两次密码输入不一致！");
            return;
        }
        // 发起注册请求
        RegisterDto registerDto = new RegisterDto(email, password, verifyCode);
        LogService logService = ApiManager.getLogService();
        Call<ResponsePack<String>> call = logService.register(registerDto.getEmail(), registerDto.getPassword(), registerDto.getCode());
        call.enqueue(new Callback<ResponsePack<String>>() {
            @Override
            public void onResponse(Call<ResponsePack<String>> call, Response<ResponsePack<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponsePack<String> responsePack = response.body();
                    if (responsePack.getSuccess()) {
                        // 注册成功，跳转到登录界面
                        navigateToLogin();
                    } else {
                        showError(responsePack.getMessage());
                    }
                } else {
                    showError("注册失败！");
                }
            }

            @Override
            public void onFailure(Call<ResponsePack<String>> call, Throwable t) {
                showError("网络请求失败：" + t.getMessage());
            }
        });
    }

    /**
     * 显示错误信息
     * @param message
     */
    private void showError(String message) {
        textViewError.setVisibility(View.VISIBLE);
        textViewError.setText(message);
    }

    /**
     * 跳转到登陆页面
     */
    private void navigateToLogin() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, loginFragment);
        transaction.addToBackStack(null); // 将当前Fragment加入返回栈
        transaction.commit();
    }
}
