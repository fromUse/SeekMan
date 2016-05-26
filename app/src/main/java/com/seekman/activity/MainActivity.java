package com.seekman.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.seekman.R;
import com.seekman.library.template.BasicActivity;
import com.seekman.personal.fragment.Fragment_personal;
import com.seekman.square.fragment.Fragment_square;
import com.seekman.theme.fragment.Fragment_theme;

public class MainActivity extends BasicActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup group;
    private RadioButton main_square;
    private TextView mTitle = null;
    //private RadioButton main_theme;
   // private RadioButton main_personal;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView (R.layout.bottom);
        super.onCreate (savedInstanceState);

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
        mTitle = (TextView) findViewById (R.id.title);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.main_square:
                changeFragment(new Fragment_square(),true);
                mTitle.setText (getString(R.string.square_title));
                break;
            case R.id.main_theme:
                changeFragment(new Fragment_theme (),true);
                mTitle.setText (getString(R.string.theme_title));
                break;
            case R.id.main_personal:
                changeFragment(new Fragment_personal (),true);
                mTitle.setText (getString(R.string.personal));
                break;
            default:
                break;
        }
    }
    //切换不同的fragment
    public void changeFragment(Fragment fragment, boolean isInit){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mframe,fragment);
        if (!isInit){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
