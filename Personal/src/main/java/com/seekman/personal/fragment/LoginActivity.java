package com.seekman.personal.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.seekman.library.utils.StringLoad;
import com.seekman.personal.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * 1.用户登录界面
 * 用户名输入框——获取用户输入的值
 * 密码输入框——获取密码输入的值
 * 登录按钮——连接服务器数据库验证用户名密码是否存在或正确
 * 注册和忘了密码文本框——通过intent跳转到注册页面
 * 惊喜文本框——弹出活动介绍
 * Created by Administrator on 2016/5/26 0026.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static Handler mhandle;

    public ImageView login_return;//返回图片
    private EditText user_username, user_passwd;//账号，密码输入框
    private Button user_login;//登录按钮
    private TextView user_forgot, user_register, user_surprise;//忘记密码文本，注册文本，惊喜文本

    private Context context = LoginActivity.this;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
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
    }

    private void init() {
        login_return = (ImageView) findViewById(R.id.login_return);
        user_username = (EditText) findViewById(R.id.user_username);
        user_passwd = (EditText) findViewById(R.id.user_passwd);
        user_login = (Button) findViewById(R.id.user_login);
        user_forgot = (TextView) findViewById(R.id.user_forgot);
        user_register = (TextView) findViewById(R.id.user_register);
        user_surprise = (TextView) findViewById(R.id.user_surprise);

        shared = this.getSharedPreferences("userfile", Context.MODE_PRIVATE);
        user_username.setText(shared.getString("username",""));
        user_passwd.setText(shared.getString("passwd",""));

        login_return.setOnClickListener(this);
        user_login.setOnClickListener(this);
        user_forgot.setOnClickListener(this);
        user_register.setOnClickListener(this);
        user_surprise.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.login_return) {
            finish();
        } else if (i == R.id.user_login) {
            headleLogin();
        } else if (i == R.id.user_forgot || i == R.id.user_register) {
            register();
        } else if (i == R.id.user_surprise) {
            indialog();
        }
    }

    //登录调用
    private void headleLogin() {
        String username = user_username.getText().toString();
        String passwd = user_passwd.getText().toString();
        String url = final_data.LOGIN;
        if (sendUsername(username) && sendUserpasswd(passwd)){

            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setTitle("请稍等");
            pDialog.setMessage("玩命登录中...");
            pDialog.setIcon(R.mipmap.logo);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
//            new Thread(){
//                @Override
//                public void run() {
//                    try {
//                        String username = user_username.getText().toString();
//                        String passwd = user_passwd.getText().toString();
//                        String url = final_data.LOGIN;
//                        URL HttpUrl = new URL(url);
//                        HttpURLConnection connection = (HttpURLConnection) HttpUrl.openConnection();
//                        connection.setRequestMethod("POST");//请求方式post
//                        connection.setReadTimeout(5000);//请求超时时间
//                        OutputStream outputStream = connection.getOutputStream();
//                        String content = "user_username=" + username +
//                                "&user_passwd=" + passwd;
//                        outputStream.write(content.getBytes());
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                        String string;
//                        StringBuffer stringBuffer = new StringBuffer();
//                        while ((string = reader.readLine()) != null) {
//                            stringBuffer.append(string);
//                        }
//                        Log.d("LoginPOST--------- ", stringBuffer.toString());
//
//                        if(stringBuffer.toString().equals("true")){
//                            Log.d("HttpLoginHread",new HttpLoginHread(url,username,passwd).toString());
//                            pDialog.dismiss();
//                            loginShared();
//                            loginSuccess();
//
//                        }else{
//                            Log.d("HttpLoginHread",new HttpLoginHread(url,username,passwd).toString());
//                            Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
//                            pDialog.cancel();
//                        }
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.start();
//            /*Log.d("HttpLoginHread--------------",inLoginto);*/

            new StringLoad(StringLoad.METHOD_GET){

                @Override
                public void executeUI(final String result) {
                    if (result.equals("true")){
                        pDialog.dismiss();
                        loginShared();
                        loginSuccess();
                    }else if (result.equals("false")){
                        Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                        pDialog.cancel();
                    }

                }


            }.execute(final_data.LOGIN+"?user_username=" + username +
                                "&user_passwd=" + passwd);
        }
    }

    //

    /** 1.登陆成功将数据使用sharedPrefernces存储到本地
     *  2.存储文件名为userfile，覆盖模式（只能保存一个用户的数据）
     */
    private void loginShared() {
        String username = user_username.getText().toString();
        String passwd = user_passwd.getText().toString();
        String iflonig = "true";

        SharedPreferences.Editor editor = shared.edit();
        editor.putString("username",username)
                .putString("passwd",passwd)
                .putString("iflogin",iflonig)
                .putString("user_nickname", "")
                .putString("user_name", "")
                .putString("user_sex", "")
                .putString("user_age", "")
                .putString("city_name", "")
                .putString("information","false");
        editor.commit();
    }

    //惊喜调用方法
    private void indialog() {
        new AlertDialog.Builder(this)
                .setTitle("活动惊喜")
                .setMessage("敬请期待")
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //不做代码执行
                    }
                }).create().show();
    }

    //忘了密码或者注册调用方法
    private void register() {
        startActivity(new Intent(context, RegisterActivity.class));
    }

    //登录成功调用的方法
    private void loginSuccess() {
        String username = user_username.getText().toString();
        Intent intent = new Intent();
        setResult(CONTEXT_INCLUDE_CODE,intent.putExtra("username",username).putExtra("iflogin","true"));
        finish();
    }
    //判断手机号码
    private boolean sendUsername(String username) {
        if (username == null || username.isEmpty()){
            Toast.makeText(context, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            Log.d("手机号码不能为空","手机号码不能为空"+username.length());
            return false;
        }else if (username.length() != 11){
            Toast.makeText(context, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            Log.d("手机号码格式不正确","手机号码格式不正确"+username.length());
            return false;
        }else {
            return true;
        }
    }
    //判断密码
    private boolean sendUserpasswd(String passwd) {
        if (passwd == null || passwd.isEmpty()){
            Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
            Log.d("密码不能为空","密码不能为空"+passwd.length());
            return false;
        }else if ( passwd.length() < 6 || passwd.length() > 18){
            Toast.makeText(context, "密码长度必须为6到18之间", Toast.LENGTH_SHORT).show();
            Log.d("密码长度必须为6到18之间","密码长度必须为6到18之间"+passwd.length());
            return false;
        }else {
            return true;
        }
    }
}