package com.seekman.library.template;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        inits();
        listeners();
        settings();
    }


    /**
     *
     * 对控件进行一些属性设置
     */
    abstract protected void settings();

    /**
     *
     * 初始化事件的绑定
     *
     */
    abstract  protected void listeners() ;

    /**
     *
     * 初始化控件，控件的绑定
     *
     */
    abstract protected void inits();

    @Override
    public void onBackPressed() {
        Exit.exitonBackPressed(this);
    }
}
