package com.lihao.blob.ui.publish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lihao.blob.R;

/**
 * 发布
 *
 * @author lihao
 * &#064;date  2024/11/30--16:24
 * @since 1.0
 */
public class PublishFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publish, container, false);

        // 示例功能：点击按钮发布内容
        Button publishButton = view.findViewById(R.id.btn_publish);
        publishButton.setOnClickListener(v ->
                Toast.makeText(getActivity(), "发布成功！", Toast.LENGTH_SHORT).show()
        );

        return view;
    }
}