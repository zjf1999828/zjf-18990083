package com.example.homework4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homework4.util.DataUtil;
import com.example.homework4.util.FileUtil;

public class TextWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_username;
    private EditText et_type;
    private EditText et_password;
    private String mPath;
    private TextView tv_path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_write);

        et_username = findViewById(R.id.et_name);
        et_type = findViewById(R.id.et_age);
        et_password = findViewById(R.id.et_height);
        tv_path = findViewById(R.id.tv_path);
        findViewById(R.id.btn_save).setOnClickListener(this);

        // 获取当前App的私有存储目录
        mPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/";
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            String username = et_username.getText().toString();
            String type = et_type.getText().toString();
            String password = et_password.getText().toString();
            if (TextUtils.isEmpty(username)) {
                showToast("请先填写姓名");
                return;
            } else if (TextUtils.isEmpty(type)) {
                showToast("请先填写账号");
                return;
            } else if (TextUtils.isEmpty(password)) {
                showToast("请先填写密码");
                return;
            }

            String content = "";
            content = String.format("%s　姓名：%s\n", content, username);
            content = String.format("%s　账号：%s\n", content, type);
            content = String.format("%s　密码：%scm\n", content, password);
            content = String.format("%s　注册时间：%s\n", content, DataUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));

            // 检查SD卡状态,存储媒体已经挂载，并且挂载点可读/写。
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String file_path = mPath + DataUtil.getNowDateTime("") + ".txt";
                // 把文本字符串保存为文本文件
                FileUtil.saveText(file_path, content);
                tv_path.setText("用户注册信息文件的保存路径为：\n" + file_path);
                showToast("数据已写入SD卡文件");

                Intent intent = new Intent(this, TextReadActivity.class);
                startActivity(intent);
            } else {
                showToast("未发现已挂载的SD卡，请检查");
            }
        }
    }

    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
}
