package com.lihao.blob.ui.person;

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
import com.lihao.blob.data.model.UserInfoDto;
import com.lihao.blob.data.repository.CallBack.UserCallBack;
import com.lihao.blob.data.repository.UserRepository;
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
    private Button btnEditUserInfo;
    private UserRepository userRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                tvPhone.setText(userInfo.getTelephone());  // 假设电话号码在 selfTag 中

                // 性别图标，使用男性或女性图片
                if (userInfo.getGender() == 1) {
                    imgGender.setImageResource(R.drawable.ic_gender_male); // 男性
                } else {
                    imgGender.setImageResource(R.drawable.ic_gender_female); // 女性
                }

                // 使用 Picasso 加载头像
                Picasso.get().load(userInfo.getPhoto()).into(imgAvatar);
            }
        });

        return view;
    }

    // 更改用户信息按钮点击事件
    public void onEditUserInfoClick(View view) {
        // 跳转到更改用户信息的页面
        Toast.makeText(getContext(), "跳转到更改用户信息页面", Toast.LENGTH_SHORT).show();
        // TODO: 实现跳转到更改用户信息页面的代码
    }
}

