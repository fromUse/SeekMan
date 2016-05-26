package com.seekman.personal.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.seekman.personal.R;


/**
 * 1.用户登录界面
 * 用户名输入框——获取用户输入的值
 * 密码输入框——获取密码输入的值
 * 登录按钮——连接服务器数据库验证用户名密码是否存在或正确
 * 注册和忘了密码文本框——通过intent跳转到注册页面
 * 惊喜文本框——弹出活动介绍（待定）
 * Created by Administrator on 2016/5/26 0026.
 */

public class LoginActivity extends AppCompatActivity {

    //控件声明
    EditText user_phone,user_password;
    Button user_login;
    TextView user_forgot,user_register,user_surprise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.user_login);
        //初始化
        init();
        user_surprise.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        //事件监听
        setListeners();
    }

    private void setListeners() {
        /**
         * 1.登录按钮
         * 通过线程Thread连接到服务器
         * 从服务器端的数据库获取用户表里面的值
         * 判断用户名是否存在——不存在（弹出对话框提示用户注册）
         * 判断用户名密码是否正确——错误（弹出对话框提示用户注册）
         *
         */
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlString = "http://172.30.84.10:8088/phpmyadmin/";
                String userPhone = user_phone.getText().toString();
                String userPassword = user_password.getText().toString();
                if (userPhone.equals("userphone") || userPassword.equals("userpassword")){

                }
            }
        });
    }

    private void init() {
        user_phone = (EditText) findViewById(R.id.user_phone);
        user_password = (EditText) findViewById(R.id.user_password);
        user_login = (Button) findViewById(R.id.user_login);
        user_forgot = (TextView) findViewById(R.id.user_forgot);
        user_register = (TextView) findViewById(R.id.user_register);
        user_surprise = (TextView) findViewById(R.id.user_surprise);
    }
}
