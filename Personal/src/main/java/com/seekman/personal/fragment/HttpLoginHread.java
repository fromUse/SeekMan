package com.seekman.personal.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 登陆时所需字段
 * user_username：用户名（手机号）
 * user_passwd：密码
 * 说明：需要发送给服务器用户名（手机号）和密码，服务器返回给客户端true或false
 * *
 * URL：
 * GET方式
 * http://172.30.84.10:8082/AndroidServer/login?user_username=hjw&user_passwd=987
 * <p/>
 * POST方式：
 * http://172.30.84.10:8082/AndroidServer/login
 * <p/>
 * Created by Administrator on 2016/5/29 0029.
 */
public class HttpLoginHread extends Thread {

    String url;
    String username;
    String passwd;

    public HttpLoginHread(String url, String username, String passwd) {
        this.url = url;
        this.username = username;
        this.passwd = passwd;
    }

    public HttpLoginHread() {
    }

    public void doGet() {

        try {
            url = url + "?user_username=" + URLEncoder.encode(username, "utf-8") +
                    "&user_passwd=" + URLEncoder.encode(passwd, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            URL HttpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) HttpUrl.openConnection();
            connection.setRequestMethod("GET");//请求方式get
            connection.setReadTimeout(5000);//请求超时时间
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String string;
            StringBuffer stringBuffer = new StringBuffer();
            while ((string = reader.readLine()) != null) {
                stringBuffer.append(string);
            }
            Log.d("LoginGET ", stringBuffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost() {
        try {
            URL HttpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) HttpUrl.openConnection();
            connection.setRequestMethod("POST");//请求方式post
            connection.setReadTimeout(5000);//请求超时时间
            OutputStream outputStream = connection.getOutputStream();
            String content = "user_username=" + username +
                    "&user_passwd=" + passwd;
            outputStream.write(content.getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String string;
            StringBuffer stringBuffer = new StringBuffer();
            while ((string = reader.readLine()) != null) {
                stringBuffer.append(string);
            }
            Log.d("LoginPOST--------- ", stringBuffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        super.run();
        doPost();
    }
}
