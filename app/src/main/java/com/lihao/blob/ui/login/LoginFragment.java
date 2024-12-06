package com.lihao.blob.ui.login;


import android.content.Intent;
import android.os.Bundle;
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
import com.lihao.blob.data.network.ApiManager;
import com.lihao.blob.data.network.service.LogService;
import com.lihao.blob.ui.MainActivity;
import com.lihao.blob.utils.StrUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登陆
 *
 * @author lihao
 * &#064;date  2024/11/27--12:49
 * @since 1.0
 */
public class LoginFragment extends Fragment {

    private EditText editTextEmail, editTextPassword;
    private TextView textViewError, textViewRegister, textViewForgotPassword;
    private Button buttonLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // 初始化视图
        editTextEmail = view.findViewById(R.id.loginEmail);
        editTextPassword = view.findViewById(R.id.loginPassword);
        textViewError = view.findViewById(R.id.textViewError);
        textViewRegister = view.findViewById(R.id.textViewRegister);
        textViewForgotPassword = view.findViewById(R.id.textViewForgotPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);

        // 注册和忘记密码点击事件
        textViewRegister.setOnClickListener(v -> navigateToRegister());
        textViewForgotPassword.setOnClickListener(v -> navigateToForgotPassword());

        // 登录按钮点击事件
        buttonLogin.setOnClickListener(v -> attemptLogin());

        return view;
    }

    // 尝试登录
    private void attemptLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (StrUtil.isBlank(email, password)) {
            showError("邮箱或密码不能为空！");
            return;
        }

        // 发起登录请求
        LogService logService = ApiManager.getLogService();
        Call<ResponsePack<String>> call = logService.login(email, password);

        call.enqueue(new Callback<ResponsePack<String>>() {
            @Override
            public void onResponse(Call<ResponsePack<String>> call, Response<ResponsePack<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponsePack<String> responsePack = response.body();
                    if (responsePack.getSuccess()) {
                        // 登录成功，跳转到主界面或其他页面
                        RetrofitClient.setToken(responsePack.getData());
                        navigateToHome();
                    } else {
                        showError(responsePack.getMessage());
                    }
                } else {
                    showError("登录失败！");
                }
            }

            @Override
            public void onFailure(Call<ResponsePack<String>> call, Throwable t) {
                showError("网络请求失败：" + t.getMessage());
            }
        });
    }

    //显示错误信息
    private void showError(String message) {
        textViewError.setVisibility(View.VISIBLE);
        textViewError.setText(message);
    }

    //导航到注册页面
    private void navigateToRegister() {
        // 创建并替换注册页面Fragment
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, registerFragment);
        transaction.addToBackStack(null); // 将当前Fragment加入返回栈
        transaction.commit();
    }

    //导航到忘记密码页面
    private void navigateToForgotPassword() {
        // 创建并替换忘记密码页面Fragment
        ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, forgotPasswordFragment);
        transaction.addToBackStack(null); // 将当前Fragment加入返回栈
        transaction.commit();
    }

    //登录成功后跳转到主界面
    private void navigateToHome() {
        // 此处可以启动主界面或者跳转到应用的其他页面
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}