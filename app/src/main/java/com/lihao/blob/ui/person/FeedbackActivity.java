package com.lihao.blob.ui.person;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import com.lihao.blob.R;
import com.lihao.blob.data.repository.CallBack.FeedbackCallBack;
import com.lihao.blob.data.repository.FeedbackRepository;

import java.io.File;

/**
 * 问题反馈页面
 *
 * @author lihao
 * &#064;date  2024/12/13--18:42
 * @since 1.0
 */
public class FeedbackActivity extends AppCompatActivity {

    private EditText etContent;
    private Spinner spinnerFeedbackType;
    private Button btnSubmitFeedback;
    private ImageView ivCover, ivBack;
    private File selectedImageFile;
    private FeedbackRepository feedbackRepository;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        etContent = findViewById(R.id.etContent);
        spinnerFeedbackType = findViewById(R.id.spinnerFeedbackType);
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);
        ivCover = findViewById(R.id.ivCover);
        ivBack = findViewById(R.id.ivBack);

        feedbackRepository = new FeedbackRepository(this);
        String feedbackTypes[] = new String[]{"页面bug", "优化", "功能", "数据丢失", "安全问题", "其他"};
        // 设置Spinner选项
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, feedbackTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFeedbackType.setAdapter(adapter);

        // 点击封面图片触发选择图片
        ivCover.setOnClickListener(v -> {
            // 打开图片选择器，选中图片后更新 ivCover
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        });

        // 提交反馈按钮点击事件
        btnSubmitFeedback.setOnClickListener(v -> {
            String content = etContent.getText().toString();
            int feedbackType = spinnerFeedbackType.getSelectedItemPosition();
            File file = selectedImageFile;

            // 调用接口发送反馈
            feedbackRepository.fetchFeedbackPublish(content, feedbackType + 1, file, new FeedbackCallBack() {
                @Override
                public void getData(String content) {
                    Toast.makeText(FeedbackActivity.this, content, Toast.LENGTH_SHORT).show();
                    back();
                }
            });
        });

        //返回按钮点击事件
        ivBack.setOnClickListener(v -> back());
    }
    public void back(){
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.onBackPressed();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            selectedImageFile = new File(getRealPathFromURI(selectedImageUri));
            ivCover.setImageURI(selectedImageUri);
        }
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
}
