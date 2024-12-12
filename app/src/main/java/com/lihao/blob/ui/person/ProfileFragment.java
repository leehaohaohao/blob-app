package com.lihao.blob.ui.person;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lihao.blob.R;
import com.lihao.blob.base.RetrofitClient;
import com.lihao.blob.data.model.UserInfoDto;
import com.lihao.blob.data.repository.CallBack.UserCallBack;
import com.lihao.blob.data.repository.UserRepository;
import com.lihao.blob.ui.login.LogActivity;
import com.squareup.picasso.Picasso;

/**
 * 个人中心
 *
 * @author lihao
 * &#064;date  2024/11/30--16:25
 * @since 1.0
 */
public class ProfileFragment extends Fragment {

    private TextView tvName, tvUserId, tvLikes, tvPostsCount, tvPhone;
    private ImageView imgAvatar, imgGender;
    private Button btnEditUserInfo,btnLogout;
    private UserRepository userRepository;
    private static final int REQUEST_CODE_EDIT_USER_INFO = 100;
    public static final int RESULT_OK = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // 初始化UI组件
        tvName = view.findViewById(R.id.tv_name);
        tvUserId = view.findViewById(R.id.tv_user_id);
        tvLikes = view.findViewById(R.id.tv_likes_count);
        tvPostsCount = view.findViewById(R.id.tv_posts_count);
        tvPhone = view.findViewById(R.id.tv_phone);
        imgAvatar = view.findViewById(R.id.img_avatar);
        imgGender = view.findViewById(R.id.img_gender);
        btnEditUserInfo = view.findViewById(R.id.btn_edit_user_info);
        btnLogout = view.findViewById(R.id.btn_logout);

        // 获取用户信息
        userRepository = new UserRepository(getContext());
        userRepository.fetchUserInfo(new UserCallBack() {
            @Override
            public void getUserInfo(UserInfoDto userInfo) {
                // 更新UI显示用户信息
                tvName.setText(userInfo.getName());
                tvUserId.setText("UID: " + userInfo.getUserId());
                tvLikes.setText(String.valueOf(userInfo.getLove()));
                tvPostsCount.setText(String.valueOf(userInfo.getPost()));
                tvPhone.setText(userInfo.getTelephone());
                // 性别图标，使用男性或女性图片
                if (userInfo.getGender() == 1) {
                    imgGender.setImageResource(R.drawable.ic_gender_male);
                } else {
                    imgGender.setImageResource(R.drawable.ic_gender_female);
                }
                // 使用 Picasso 加载头像
                Picasso.get().load(userInfo.getPhoto()).into(imgAvatar);
            }
        });
        // 设置"更改用户信息"按钮点击事件
        btnEditUserInfo.setOnClickListener(v -> onEditUserInfoClick(v));
        // 设置退出登录按钮点击事件
        btnLogout.setOnClickListener(v -> onLogoutClick());
        return view;
    }
    // 更改用户信息按钮点击事件
    public void onEditUserInfoClick(View view) {
        // 跳转到更改用户信息的页面
//        Toast.makeText(getContext(), "跳转到更改用户信息页面", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
        startActivity(intent);
    }
    // 退出登录按钮点击事件
    public void onLogoutClick() {
        // 清除 SharedPreferences 中的 token
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
        // 清除 RetrofitClient 中的 token
        RetrofitClient.setToken(null);
        // 跳转到登录页面
        Intent intent = new Intent(getActivity(), LogActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    // 接收返回的数据并更新UI
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_USER_INFO && resultCode == RESULT_OK && data != null) {
            // 从返回的数据中获取更新后的用户信息
            String updatedName = data.getStringExtra("updatedName");
            String updatedPhone = data.getStringExtra("updatedPhone");
            int updatedGender = data.getIntExtra("updatedGender", 1);

            // 更新UI
            tvName.setText(updatedName);
            tvPhone.setText(updatedPhone);
            if (updatedGender == 1) {
                imgGender.setImageResource(R.drawable.ic_gender_male);
            } else {
                imgGender.setImageResource(R.drawable.ic_gender_female);
            }

            // 提示用户信息已更新
            Toast.makeText(getContext(), "个人信息更新成功", Toast.LENGTH_SHORT).show();
        }
    }
}

