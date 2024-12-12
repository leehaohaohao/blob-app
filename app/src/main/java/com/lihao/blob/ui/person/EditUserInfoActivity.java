package com.lihao.blob.ui.person;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lihao.blob.R;
import com.lihao.blob.data.model.UserInfoDto;
import com.lihao.blob.data.repository.CallBack.UserCallBack;
import com.lihao.blob.data.repository.UserRepository;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 更改个人信息界面
 *
 * @author lihao
 * &#064;date  2024/12/12--20:31
 * @since 1.0
 */
public class EditUserInfoActivity extends AppCompatActivity {

    private EditText etName, etTelephone;
    private RadioGroup rgGender;
    private ImageView imgAvatar;
    private Button btnSave;
    private Uri selectedImageUri;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        // 初始化 UI 元素
        etName = findViewById(R.id.edt_name);
        etTelephone = findViewById(R.id.edt_phone);
        rgGender = findViewById(R.id.radio_gender);
        imgAvatar = findViewById(R.id.img_avatar);
        btnSave = findViewById(R.id.btn_submit);

        userRepository = new UserRepository(this);

        // 调用接口获取用户数据并填充
        loadUserData();

        // 设置保存按钮点击事件
        btnSave.setOnClickListener(v -> onSaveButtonClick());

        // 设置头像选择事件
        imgAvatar.setOnClickListener(v -> selectImage());
    }

    private void loadUserData() {
        userRepository.fetchUserInfo(new UserCallBack() {
            @Override
            public void getUserInfo(UserInfoDto userInfo) {
                // 填充用户数据
                if (userInfo != null) {
                    // 填充姓名和电话
                    etName.setText(userInfo.getName());
                    etTelephone.setText(userInfo.getTelephone());
                    // 根据性别设置 RadioButton
                    if (userInfo.getGender() == 1) {
                        rgGender.check(R.id.radio_male);  // 男
                    } else if (userInfo.getGender() == 2) {
                        rgGender.check(R.id.radio_female);  // 女
                    }
                    // 显示头像
                    String avatarUrl = userInfo.getPhoto();
                    if (avatarUrl != null && !avatarUrl.isEmpty()) {
                        Picasso.get().load(userInfo.getPhoto()).into(imgAvatar);
                    }
                }
            }
        });
    }

    private void onSaveButtonClick() {
        // 获取输入的用户信息
        String name = etName.getText().toString().trim();
        String telephone = etTelephone.getText().toString().trim();
        Integer gender = rgGender.getCheckedRadioButtonId() == R.id.radio_male ? 1 : 2;  // 男 1, 女 2

        MultipartBody.Part filePart = null;
        if (selectedImageUri != null) {
            filePart = prepareFilePart("file", selectedImageUri);  // 准备上传头像文件
        }

        // 调用 UserRepository 提交更改
        userRepository.updateUserInfo(name, gender, telephone, filePart, new UserCallBack() {
            @Override
            public void getUserInfo(UserInfoDto userInfo) {
                // 成功后显示提示信息，并关闭当前界面
                Toast.makeText(EditUserInfoActivity.this, "个人信息更新成功", Toast.LENGTH_SHORT).show();
                // 将更新后的用户信息传回
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedName", name);
                resultIntent.putExtra("updatedPhone", telephone);
                resultIntent.putExtra("updatedGender", gender);
                setResult(RESULT_OK, resultIntent);  // 设置返回结果
                finish();
            }
        });
    }

    // 选择头像图片
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imgAvatar.setImageURI(selectedImageUri); // 显示选中的图片
        }
    }

    // 将选中的文件转换为 MultipartBody.Part
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = new File(getRealPathFromURI(fileUri));
        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }

    // 获取文件的真实路径
    private String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            return cursor.getString(index);
        }
    }
}
