package com.seekman.template;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by jia on 2016/4/29.
 */
public class Exit {

    private static long INTERVAL = 2000;//返回键间隔最大值常量
    private static long mFirstBackKeyPressTime = -1;//第一次按下返回键的事件
    private static long mLastBackKeyPressTime = -1;//第二次按下返回键的事件

    public static void exitonBackPressed(Activity context) {


        if(mFirstBackKeyPressTime == -1){
            mFirstBackKeyPressTime = System.currentTimeMillis();
            Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_LONG).show();
        }else{
            mLastBackKeyPressTime = System.currentTimeMillis();
            if((mLastBackKeyPressTime - mFirstBackKeyPressTime) < INTERVAL){
                context.finish();
                System.exit(0);
            }else{
                mFirstBackKeyPressTime = mLastBackKeyPressTime;
                Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_LONG).show();
            }
        }
        long i = mFirstBackKeyPressTime;
    }



}
