package com.seekman.personal.fragment;

import android.util.Log;

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
 * Created by Administrator on 2016/5/29 0029.
 */
public class HttpRegistertHread extends Thread {

    String url;
    String username;
    String nickname;
    String passwd;

    public HttpRegistertHread(String url, String username, String passwd, String nickname) {
        this.url = url;
        this.username = username;
        this.passwd = passwd;
        this.nickname = nickname;
    }

    public HttpRegistertHread() {
    }

    public void doGet() {
        try {
            url = url + "?user_username=" + URLEncoder.encode(username, "utf-8") +
                    "&user_passwd=" + URLEncoder.encode(passwd, "utf-8") +
                    "&user_nickname=" + URLEncoder.encode(nickname, "utf-8");
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
            Log.d("StringBufferGET ", stringBuffer.toString());
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
                    "&user_passwd=" + passwd +
                    "&user_nickname=" + nickname;
            outputStream.write(content.getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String string;
            StringBuffer stringBuffer = new StringBuffer();
            while ((string = reader.readLine()) != null) {
                stringBuffer.append(string);
            }
            Log.d("StringBufferPOST ", stringBuffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        /*doGet();*/
        doPost();
    }
}
