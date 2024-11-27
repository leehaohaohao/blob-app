package com.lihao.blob.ui.login;

import android.annotation.SuppressLint;
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
import com.lihao.blob.base.RetrofitClient;
import com.lihao.blob.data.model.RegisterDto;
import com.lihao.blob.data.network.service.LogService;
import com.lihao.blob.utils.StrUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 忘记密码页面
 *
 * @author lihao
 * &#064;date  2024/11/27--12:53
 * @since 1.0
 */
public class ForgotPasswordFragment extends Fragment {

    private EditText editTextEmail, editTextNewPassword, editTextVerifyCode;
    private TextView textViewError, textViewVerifyCode;
    private Button buttonResetPassword;
    private CountDownTimer countDownTimer;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        // 初始化视图
        editTextEmail = view.findViewById(R.id.forgetEmail);
        editTextNewPassword = view.findViewById(R.id.newPassword);
        editTextVerifyCode = view.findViewById(R.id.forgetCode);
        textViewError = view.findViewById(R.id.textViewError);
        textViewVerifyCode = view.findViewById(R.id.getForgetCode);
        buttonResetPassword = view.findViewById(R.id.buttonResetPassword);

        // 获取验证码按钮
        textViewVerifyCode.setOnClickListener(v -> sendVerifyCode());

        // 重置密码按钮点击事件
        buttonResetPassword.setOnClickListener(v -> attemptResetPassword());

        // 返回到登录页面
        TextView textViewBackToLogin = view.findViewById(R.id.textViewBackToLogin);
        textViewBackToLogin.setOnClickListener(v -> navigateToLogin());

        return view;
    }

    // 发送验证码
    private void sendVerifyCode() {
        String email = editTextEmail.getText().toString().trim();

        if (StrUtil.isBlank(email)) {
            showError("请输入邮箱！");
            return;
        }

        // 发起获取验证码请求
        LogService logService = RetrofitClient.getInstance().create(LogService.class);
        Call<ResponsePack<Void>> call = logService.code(email);

        call.enqueue(new Callback<ResponsePack<Void>>() {
            @Override
            public void onResponse(Call<ResponsePack<Void>> call, Response<ResponsePack<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponsePack<Void> responsePack = response.body();
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
            public void onFailure(Call<ResponsePack<Void>> call, Throwable t) {
                showError("网络请求失败：" + t.getMessage());
            }
        });
    }

    // 启动倒计时
    private void startCountDown() {
        textViewVerifyCode.setEnabled(false);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewVerifyCode.setText(millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                textViewVerifyCode.setText("重新获取");
                textViewVerifyCode.setEnabled(true);
            }
        };
        countDownTimer.start();
    }

    // 重置密码
    private void attemptResetPassword() {
        String email = editTextEmail.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String verifyCode = editTextVerifyCode.getText().toString().trim();

        // 表单验证
        if (StrUtil.isBlank(email, newPassword, verifyCode)) {
            showError("邮箱、密码或验证码不能为空！");
            return;
        }

        // 发起重置密码请求
        RegisterDto resetPasswordDto = new RegisterDto(email, newPassword, verifyCode);
        LogService logService = RetrofitClient.getInstance().create(LogService.class);
        Call<ResponsePack<Void>> call = logService.resetPassword(resetPasswordDto.getEmail(), resetPasswordDto.getPassword(), resetPasswordDto.getCode());

        call.enqueue(new Callback<ResponsePack<Void>>() {
            @Override
            public void onResponse(Call<ResponsePack<Void>> call, Response<ResponsePack<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponsePack<Void> responsePack = response.body();
                    if (responsePack.getSuccess()) {
                        showError("密码重置成功！");
                        // 重置密码成功后跳转回登录页面
                        navigateToLogin();
                    } else {
                        showError(responsePack.getMessage());
                    }
                } else {
                    showError("密码重置失败！");
                }
            }

            @Override
            public void onFailure(Call<ResponsePack<Void>> call, Throwable t) {
                showError("网络请求失败：" + t.getMessage());
            }
        });
    }

    // 公共方法：显示错误信息
    private void showError(String message) {
        textViewError.setVisibility(View.VISIBLE);
        textViewError.setText(message);
    }

    // 跳转到登录页面
    private void navigateToLogin() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, loginFragment);
        transaction.addToBackStack(null); // 将当前Fragment加入返回栈
        transaction.commit();
    }
}