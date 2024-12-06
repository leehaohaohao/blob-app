package com.lihao.blob.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.lihao.blob.R;
import com.lihao.blob.base.RetrofitClient;
import com.lihao.blob.ui.MainActivity;

/**
 * 登录、注册页面
 *
 * @author lihao
 * &#064;date 2024/11/27--9:52
 * @since 1.0
 */
public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // 检查用户是否已经登录
        if (isUserLoggedIn()) {
            // 如果已经登录，获取 token 并设置到全局 RetrofitClient
            String token = getTokenFromSharedPreferences();
            RetrofitClient.setToken(token);  // 设置 token

            // 跳转到主界面
            navigateToHome();
        } else {
            // 如果没有登录，显示登录界面
            showFragment(new LoginFragment());
        }
    }

    // 检查是否已经登录
    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        return token != null; // 如果 token 存在，表示已经登录过
    }

    // 获取 token
    private String getTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        return sharedPreferences.getString("token", null);  // 获取 token
    }

    // 显示指定的 fragment
    private void showFragment(LoginFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    // 跳转到主界面
    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();  // 关闭当前的登录页面
    }
}