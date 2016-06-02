package com.seekman.activity;


import android.annotation.SuppressLint;
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


public class MainActivity extends BasicActivity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup group;
    private RadioButton main_square;

    private Fragment mSquareFragment = null;
    private Fragment mThemeFragment = null;
    private Fragment mPersonalFragment = null;
    //private RadioButton main_theme;
   // private RadioButton main_personal;
    private FragmentManager fragmentManager = null;
    private FragmentTransaction transaction = null;

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

    @SuppressLint("WrongViewCast")
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
                mSquareFragment = mSquareFragment!=null?mSquareFragment:new Fragment_square();

                changeFragment(mSquareFragment,true);



                break;
            case R.id.main_theme:
                mThemeFragment = mThemeFragment!=null?mThemeFragment:new Fragment_theme ();

                changeFragment(new Fragment_theme (),true);

                break;
            case R.id.main_personal:
                mPersonalFragment = mPersonalFragment!=null?mPersonalFragment:new Fragment_personal ();
                /*Bundle bundle = new Bundle ();
                bundle.putSerializable ("text",mTitle);
                mPersonalFragment.setArguments (bundle);*/
                changeFragment(mPersonalFragment,true);
                break;
            default:
                break;
        }
    }
    //切换不同的fragment
    public void changeFragment(Fragment fragment, boolean isInit){
         transaction = fragmentManager.beginTransaction();

        transaction.setCustomAnimations(R.anim.start_in_fragment,R.anim.end_out_fragment);

        transaction.replace(R.id.mframe,fragment);
        if (!isInit){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
