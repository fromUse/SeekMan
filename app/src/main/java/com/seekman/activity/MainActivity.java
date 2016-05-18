package com.seekman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.seekman.R;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final String KEY = "12eb6c944537c";
    private final String SECRETE = "1b4e1b70e8bfb71bb7b5459ac6444b24";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        SMSSDK.initSDK (MainActivity.this, KEY, SECRETE);

    }

    public void register_test(View view) {

        Log.i (TAG, "register: --------注册----------");
        RegisterPage registerPage = new RegisterPage ();
        registerPage.setRegisterCallback (new EventHandler () {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    HashMap<String, Object> map = (HashMap<String, Object>) data;
                    String country = (String) map.get ("country");
                    String phone = (String) map.get ("phone");
                    registerUser (country, phone);


                }
            }
        });

        registerPage.show (MainActivity.this);
    }


    /**
     * 验证成功后跳转到注册页面
     *
     * @param country 国家代码
     * @param phone   手机号码
     */
    private void registerUser(String country, String phone) {
        // Toast.makeText (MainActivity.this,country+phone,Toast.LENGTH_LONG).show ();
        Intent it = new Intent (MainActivity.this, PasswordActivity.class);

        it.putExtra ("country", country);
        it.putExtra ("phone", phone);
        startActivity (it);
    }
}
