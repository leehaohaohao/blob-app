package com.lihao.blob.ui.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.lihao.blob.R;

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
        // 默认显示登录界面
        showFragment(new LoginFragment());
    }
    private void showFragment(LoginFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}
