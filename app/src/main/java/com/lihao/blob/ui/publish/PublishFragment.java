package com.lihao.blob.ui.publish;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lihao.blob.R;
import com.lihao.blob.data.model.ArticleCoverDto;
import com.lihao.blob.data.model.ArticleDto;
import com.lihao.blob.data.repository.CallBack.ArticlesCallback;
import com.lihao.blob.data.repository.ForumRepository;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 发布
 *
 * @author lihao
 * &#064;date  2024/11/30--16:24
 * @since 1.0
 */
public class PublishFragment extends Fragment {

    private static final String[] TAGS = {
            "古代科学", "中国四大发明", "医学与生物科学","数学与天文学","工程与建筑","农业与自然科学","科学思想与哲学","科技人物与故事","古代兵器与战争技术","科学与文化"
    };

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etTitle, etContent;
    private Spinner spinnerTag;
    private ImageView ivCover;
    private Button publishButton;
    private Uri coverImageUri;
    private ForumRepository forumRepository;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publish, container, false);

        // 初始化视图组件
        etTitle = view.findViewById(R.id.etTitle);
        etContent = view.findViewById(R.id.etContent);
        ivCover = view.findViewById(R.id.ivCover);
        spinnerTag = view.findViewById(R.id.spinnerTag);
        publishButton = view.findViewById(R.id.btn_publish);
        forumRepository = new ForumRepository(getContext());

        // 设置标签选择器的数据
        ArrayAdapter<String> tagAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, TAGS);
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTag.setAdapter(tagAdapter);

        // 设置封面图片点击事件，打开图片选择器
        ivCover.setOnClickListener(v -> openImagePicker());

        // 设置发布按钮的点击事件
        publishButton.setOnClickListener(v -> onPublishClicked());

        return view;
    }

    // 打开图片选择器
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // 处理选择的图片
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            coverImageUri = data.getData();
            // 显示选择的封面图片
            Picasso.get()
                    .load(coverImageUri)
                    .placeholder(R.drawable.ic_default_cover)
                    .into(ivCover);
        }
    }

    private void onPublishClicked() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        String selectedTag = (String) spinnerTag.getSelectedItem();

        if (TextUtils.isEmpty(title)) {
            showToast("标题不能为空！");
            return;
        }

        if (TextUtils.isEmpty(content)) {
            showToast("正文不能为空！");
            return;
        }

        // 如果用户选择了封面图片，转换为 File 对象
        File coverFile = null;
        if (coverImageUri != null) {
            coverFile = getFileFromUri(coverImageUri);
        }

        // 执行发布操作
        forumRepository.fetchPublish(content, selectedTag, title, coverFile, new ArticlesCallback() {
            @Override
            public void onSuccess() {
                // 显示成功提示
                showToast("发布成功！");

                // 清空输入框
                etTitle.setText("");
                etContent.setText("");
                ivCover.setImageResource(R.drawable.ic_default_cover);  // 重置封面
            }

            @Override
            public void onArticlesFetched(List<ArticleCoverDto> articleCoverDtos) {
                // 不需要处理
            }

            @Override
            public void onFailure(Throwable t) {
                // 显示失败提示
                showToast("发布失败: " + t.getMessage());
            }

            @Override
            public void onArticleFetched(ArticleDto articleDto) {
                // 不需要处理
            }
        });
    }

    // 将 Uri 转换为 File
    private File getFileFromUri(Uri uri) {
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            File file = new File(getActivity().getCacheDir(), "cover_image.jpg");
            java.nio.file.Files.copy(inputStream, file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
