package com.seekman.theme.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.seekman.theme.R;

public class ActivityDetailsJoin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_release);
    }

    public void collectionActivity(View v){

        Toast.makeText (this, "position : " + "收藏活动", Toast.LENGTH_SHORT).show ();
    }

    public void joinActivity(View v){

        Toast.makeText (this, "position : " + "参加活动", Toast.LENGTH_SHORT).show ();
    }
}
