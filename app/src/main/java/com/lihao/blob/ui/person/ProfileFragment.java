package com.lihao.blob.ui.person;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lihao.blob.R;
import com.lihao.blob.base.RetrofitClient;
import com.lihao.blob.data.model.UserInfoDto;
import com.lihao.blob.data.repository.CallBack.UserCallBack;
import com.lihao.blob.data.repository.UserRepository;
import com.lihao.blob.ui.login.LogActivity;
import com.lihao.blob.ui.person.article.ProfileActivity;
import com.squareup.picasso.Picasso;

/**
 * 个人中心
 *
 * @author lihao
 * &#064;date  2024/11/30--16:25
 * @since 1.0
 */
public class ProfileFragment extends Fragment {
    //控件
    private TextView tvName, tvUserId, tvLikes, tvPostsCount, tvPhone;
    private ImageView imgAvatar, imgGender;
    private Button btnEditUserInfo,btnLogout,btnFeedback;
    private UserRepository userRepository;
    private LinearLayout myPost,myLike;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvName = view.findViewById(R.id.tv_name);
        tvUserId = view.findViewById(R.id.tv_user_id);
        tvLikes = view.findViewById(R.id.tv_likes_count);
        tvPostsCount = view.findViewById(R.id.tv_posts_count);
        tvPhone = view.findViewById(R.id.tv_phone);
        imgAvatar = view.findViewById(R.id.img_avatar);
        imgGender = view.findViewById(R.id.img_gender);
        btnEditUserInfo = view.findViewById(R.id.btn_edit_user_info);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnFeedback = view.findViewById(R.id.btn_feedback);
        myPost =view.findViewById(R.id.my_post);
        myLike = view.findViewById(R.id.my_like);
        // 获取用户信息
        userRepository = new UserRepository(getContext());
        fetchUserInfo();
        // 设置更改用户信息按钮点击事件
        btnEditUserInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditUserInfoActivity.class);
            startActivity(intent);
        });
        // 设置退出登录按钮点击事件
        btnLogout.setOnClickListener(v -> {
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
        });
        //设置问题反馈按钮点击事件
        btnFeedback.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), FeedbackActivity.class);
            startActivity(intent);
        });
        //设置我的文章按钮点击事件
        myPost.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });
        //设置我的喜欢按钮点击事件
        myLike.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });
        return view;
    }

    /**
     * 调用用户信心接口
     */
    private void fetchUserInfo() {
        userRepository.fetchUserInfo(new UserCallBack() {
            @Override
            public void getUserInfo(UserInfoDto userInfo) {
                tvName.setText(userInfo.getName());
                tvUserId.setText("UID: " + userInfo.getUserId());
                tvLikes.setText(String.valueOf(userInfo.getLove()));
                tvPostsCount.setText(String.valueOf(userInfo.getPost()));
                tvPhone.setText(userInfo.getTelephone());
                // 性别图标
                if (userInfo.getGender() == 1) {
                    imgGender.setImageResource(R.drawable.ic_gender_male);
                } else {
                    imgGender.setImageResource(R.drawable.ic_gender_female);
                }
                Picasso.get().load(userInfo.getPhoto()).into(imgAvatar);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        fetchUserInfo();
    }
}

