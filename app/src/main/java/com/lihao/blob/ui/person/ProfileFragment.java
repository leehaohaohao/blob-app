package com.lihao.blob.ui.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lihao.blob.R;

/**
 * classname
 *
 * @author lihao
 * &#064;date  2024/11/30--16:25
 * @since 1.0
 */
public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // 示例功能：展示用户名
        TextView usernameTextView = view.findViewById(R.id.tv_username);
        usernameTextView.setText("用户名：张三"); // 这里可以替换成动态获取的数据

        return view;
    }
}