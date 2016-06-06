package com.seekman.activity;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.seekman.R;
import com.seekman.library.template.BasicActivity;
import com.seekman.personal.fragment.Fragment_personal;
import com.seekman.square.fragment.Fragment_square;
import com.seekman.theme.fragment.Fragment_theme;


public class MainActivity extends BasicActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup group;
    private RadioButton main_square;
    //private RadioButton main_theme;
   // private RadioButton main_personal;
    private FragmentManager fragmentManager;

    //用户登录状态及登录的用户,图片,修改过信息
    private String iflogin,username,user_image,information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView (R.layout.bottom);
        super.onCreate (savedInstanceState);
        shareUserLogin();
    }

    private void shareUserLogin() {
        /**
         * 查看用户是否登录
         */
        SharedPreferences sharedPreferences = this.getSharedPreferences("userfile", Activity.MODE_PRIVATE);
        iflogin = sharedPreferences.getString("iflogin","false");
        username = sharedPreferences.getString("username", "");
        user_image = sharedPreferences.getString("user_image","");
        information = sharedPreferences.getString("information","");
    }

    @Override
    protected void settings() {

    }

    @Override
    protected void listeners() {
        group.setOnCheckedChangeListener(this);
    }

    @Override
    protected void inits() {
        group = (RadioGroup) findViewById(R.id.mRadioGroup);
        main_square = (RadioButton) findViewById(R.id.main_square);
        // main_theme = (RadioButton) findViewById(R.id.main_theme);
        // main_personal = (RadioButton) findViewById(R.id.main_personal);
        //默认打开广场
        main_square.setChecked(true);
        //初始化FragmentManager
        fragmentManager = getSupportFragmentManager();
        changeFragment(new Fragment_square (),false);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.main_square:
                changeFragment(new Fragment_square(),true);
                break;
            case R.id.main_theme:
                changeFragment(new Fragment_theme (),true);
                break;
            case R.id.main_personal:
                changeFragment(new Fragment_personal (),true);
                break;
            default:
                break;
        }
    }
    //切换不同的fragment
    public void changeFragment(Fragment fragment, boolean isInit){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations (R.anim.start_in_fragment, R.anim.end_out_fragment);
        //将SharedPreferences的数据传递到fragment
        Bundle bundle = new Bundle();
        bundle.putString("iflogin",iflogin);
        bundle.putString("username",username);
        bundle.putString("user_image",user_image);
        bundle.getString("information",information);
        fragment.setArguments(bundle);

        transaction.replace(R.id.mframe,fragment);
        if (!isInit){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
