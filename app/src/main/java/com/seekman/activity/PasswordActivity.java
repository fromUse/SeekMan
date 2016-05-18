package com.seekman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.seekman.R;

public class PasswordActivity extends AppCompatActivity {

    private static final String TAG = "PasswordActivity";
    private EditText mEditPassword = null;
    private EditText mEditConfimm = null;
    private Intent data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_password);

        init();
        data();
    }

    private void data() {
        data =  getIntent ();
        if (data != null) {
            String country = data.getStringExtra ("country");
            String phone = data.getStringExtra ("phone");
        }
    }

    private void init() {
            mEditPassword = (EditText) findViewById (R.id.password);

    }

    /**
     * 这里做用户注册的相关操作
     * @param view
     */
    public void register(View view){
        Log.i (TAG, "register: ");
    }

}
