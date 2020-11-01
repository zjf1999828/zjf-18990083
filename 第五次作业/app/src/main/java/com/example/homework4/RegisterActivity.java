package com.example.homework4;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homework4.bean.SaveInfo;
import com.example.homework4.bean.UserInfo;
import com.example.homework4.database.UserDBHelper;
import com.example.homework4.util.DataUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences mShared;
    private Button reg_btn_sure; // 声明一个单选按钮对象
    private Button reg_btn_login; // 声明一个单选按钮对象
    private TextView reg_number1; // 声明一个文本视图对象
    private TextView reg_number2;
    private TextView reg_number3;
    private EditText reg_username; // 声明一个编辑框对象
    private EditText reg_password1; // 声明一个编辑框对象
    private EditText reg_password2;
    private TextView reg_login;

    private int mRequestCode = 0; // 跳转页面时的请求代码

    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象
    private long password;

    @Override
    public void onClick(View v) {
        String username = reg_username.getText().toString().trim();
        String password1 = reg_password1.getText().toString().trim();
        String password2 = reg_password2.getText().toString().trim();
        switch (v.getId()) {
            //注册开始，判断注册条件
            case R.id.reg_btn_sure:
                if (TextUtils.isEmpty(username)) {
                    showToast("请先填写姓名");
                    return;
                } else if (TextUtils.isEmpty(password1)) {
                    showToast("请填写密码");
                    return;
                } else if (TextUtils.isEmpty(password2)) {
                    showToast("请再次输入密码");
                    return;
                } else {
                    if (TextUtils.equals(password1, password2)) {
                        //执行注册操作
                        SaveInfo.SaveInformation(RegisterActivity.this, username, password1, password2);
                        Toast.makeText(RegisterActivity.this, "注册成功,请返回登录", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.reg_btn_login:
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                break;

        }
// 以下声明一个用户信息对象，并填写它的各字段值
        UserInfo info = new UserInfo();
        info.username = username;
        info.password= password;
        info.update_time = DataUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");

        // 执行数据库帮助器的插入操作
        mHelper.insert(info);
        showToast("数据已写入SQLite数据库");

        Intent intent = new Intent(this, ReadRegisterActivity.class);
        startActivity(intent);
    }

    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_username = findViewById(R.id.reg_username);
        reg_number1 = findViewById(R.id.reg_number1);
        reg_password1 = findViewById(R.id.reg_password1);
        reg_password2 = findViewById(R.id.reg_password2);
        reg_number2 = findViewById(R.id.reg_number2);
        reg_number3 = findViewById(R.id.reg_number3);
        reg_btn_sure = (Button) findViewById(R.id.reg_btn_sure);
        reg_btn_login = (Button) findViewById(R.id.reg_btn_login);
        reg_btn_sure.setOnClickListener(new RegisterActivity());
        reg_btn_login.setOnClickListener(new RegisterActivity());

        mShared = getSharedPreferences("share", MODE_PRIVATE);

        //跳转登陆界面
        final Button MainActivity = (Button) findViewById(R.id.reg_btn_sure);
        MainActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //提示框确定是否跳转
                new AlertDialog.Builder(RegisterActivity.this).setTitle("跳转").setMessage("注册成功，是否准备跳转?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }})
                        .setNegativeButton("否",null)
                        .show();
            }
        });
    }


}