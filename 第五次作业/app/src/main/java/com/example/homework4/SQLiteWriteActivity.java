package com.example.homework4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.homework4.bean.UserInfo;
import com.example.homework4.database.UserDBHelper;
import com.example.homework4.util.DataUtil;

public class SQLiteWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private UserDBHelper mHelper; // 声明一个用户数据库帮助器的对象
    private EditText et_username;
    private EditText et_type;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_write);

        et_username = findViewById(R.id.et_username);
        et_type = findViewById(R.id.et_type);
        et_password = findViewById(R.id.et_password);
        findViewById(R.id.btn_save).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        mHelper = UserDBHelper.getInstance(this, 2);
        // 打开数据库帮助器的写连接
        mHelper.openWriteLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭数据库连接
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            String name = et_username.getText().toString();
            String type = et_type.getText().toString();
            String password = et_password.getText().toString();
            if (TextUtils.isEmpty(name)) {
                showToast("请先填写姓名");
                return;
            } else if (TextUtils.isEmpty(type)) {
                showToast("请先填写账号");
                return;
            } else if (TextUtils.isEmpty(password)) {
                showToast("请先填写密码");
                return;
            }


            // 以下声明一个用户信息对象，并填写它的各字段值
            UserInfo info = new UserInfo();
            info.username = name;
            type = "";
            info.type = Integer.parseInt(type);
            password = "";
            info.password = Long.parseLong(password);
            info.update_time = DataUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");

            // 执行数据库帮助器的插入操作
            mHelper.insert(info);
            showToast("注册");

            Intent intent = new Intent(this, SQLiteReadActivity.class);
            startActivity(intent);
        }
    }

    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
}
