package com.lihao.blob.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lihao.blob.R;
import com.lihao.blob.base.ResponsePack;
import com.lihao.blob.base.RetrofitClient;
import com.lihao.blob.data.model.LoginDto;
import com.lihao.blob.data.network.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录
 *
 * @author lihao
 * &#064;date  2024/11/27--10:23
 * @since 1.0
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView textViewError;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化视图
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewError = findViewById(R.id.textViewError);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        // 获取用户名和密码
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // 检查输入的合法性
        if (username.isEmpty() || password.isEmpty()) {
            textViewError.setVisibility(View.VISIBLE);
            textViewError.setText("Username and Password cannot be empty");
            return;
        }

        // 创建登录请求对象
        LoginDto loginDto = new LoginDto(username, password);

        // 发起网络请求
        UserService userService = RetrofitClient.getInstance().create(UserService.class);
        Call<ResponsePack<String>> call = userService.login(loginDto.getEmail(),loginDto.getPassword());

        call.enqueue(new Callback<ResponsePack<String>>() {
            @Override
            public void onResponse(Call<ResponsePack<String>> call, Response<ResponsePack<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponsePack<String> responsePack = response.body();

                    if (responsePack.getSuccess()) {
                        // 登录成功，跳转到主页面或其他操作
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        // 这里可以跳转到另一个 Activity 或保存登录信息
                    } else {
                        // 登录失败，显示错误信息
                        textViewError.setVisibility(View.VISIBLE);
                        textViewError.setText(responsePack.getMessage());
                    }
                } else {
                    // 处理响应失败情况
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("Login failed. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<ResponsePack<String>> call, Throwable t) {
                // 网络请求失败
                textViewError.setVisibility(View.VISIBLE);
                textViewError.setText("Network error: " + t.getMessage());
            }
        });
    }
}
