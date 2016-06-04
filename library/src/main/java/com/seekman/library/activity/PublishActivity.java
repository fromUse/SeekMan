package com.seekman.library.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.seekman.library.R;
import com.seekman.library.utils.PublicUtil;
import com.seekman.library.utils.StringLoad;

import java.util.Calendar;


public class PublishActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "PublishActivity";

    private TextView start_activity_time = null;
    private TextView start_activity_date = null;

    private EditText activity_address = null;

    private TextView end_activity_time = null;
    private TextView end_activity_date = null;


    private TextView join_start_activity_time = null;
    private TextView join_start_activity_date = null;

    private TextView join_end_activity_time = null;
    private TextView join_end_activity_date = null;

    private TextView activity_theme = null;
    private EditText activity_title = null;
    private EditText activity_details = null;

    private Calendar calendar = null;
    private int year = 0;
    private int month = 0;
    private int day = 0;

    private int hour = 0;
    private int minute = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_publish);

        initView ();
        lister ();

    }

    private void lister() {

        /**活动主题**/
        activity_theme.setOnClickListener (this);
        /**活动标题**/
        activity_title.setOnClickListener (this);
        /**活动开始日期**/
        start_activity_time.setOnClickListener (this);
        /**活动开始时间**/
        start_activity_date.setOnClickListener (this);
        /**活动结束日期**/
        end_activity_time.setOnClickListener (this);
        /**活动结束时间**/
        end_activity_date.setOnClickListener (this);

        join_start_activity_time.setOnClickListener (this);
        join_start_activity_date.setOnClickListener (this);

        join_end_activity_time.setOnClickListener (this);
        join_end_activity_date.setOnClickListener (this);
    }

    private void initView() {


        activity_theme = (TextView) findViewById (R.id.activity_theme);
        activity_title = (EditText) findViewById (R.id.activity_title);


        activity_address = (EditText) findViewById (R.id.activity_address);

        start_activity_time = (TextView) findViewById (R.id.start_activity_time);
        start_activity_date = (TextView) findViewById (R.id.start_activity_date);

        end_activity_time = (TextView) findViewById (R.id.end_activity_time);
        end_activity_date = (TextView) findViewById (R.id.end_activity_date);


        join_start_activity_time = (TextView) findViewById (R.id.join_start_activity_time);
        join_start_activity_date = (TextView) findViewById (R.id.join_start_activity_date);

        join_end_activity_time = (TextView) findViewById (R.id.join_end_activity_time);
        join_end_activity_date = (TextView) findViewById (R.id.join_end_activity_date);


        activity_details = (EditText) findViewById (R.id.activity_details);

        calendar = Calendar.getInstance ();
        year = calendar.get (Calendar.YEAR);
        month = calendar.get (Calendar.MONTH) + 1;
        day = calendar.get (Calendar.DAY_OF_MONTH);

        hour = calendar.get (Calendar.HOUR_OF_DAY);
        minute = calendar.get (Calendar.MINUTE);


    }


    @Override
    public void onClick(View v) {

        /**这里用switch出现莫名报错说以改用if**/


        int id = v.getId ();

        /**活动主题**/
        if (id == R.id.activity_theme) {

            Toast.makeText (this, "  activity_theme ", Toast.LENGTH_SHORT).show ();
        }
        /**活动标题**/
        if (id == R.id.activity_title) {

        }


        /**活动时间**/
        if (id == R.id.start_activity_time) {
            selectTimeDialog (start_activity_time);


        }
        if (id == R.id.start_activity_date) {

            selectDateDialog (start_activity_date);
        }


        /**报名时间**/
        if (id == R.id.end_activity_time) {

            selectTimeDialog (end_activity_time);
        }


        if (id == R.id.end_activity_date) {


            selectDateDialog (end_activity_date);
        }


        /**活动报名时间**/
        if (id == R.id.join_start_activity_time) {
            selectTimeDialog (join_start_activity_time);


        }
        if (id == R.id.join_start_activity_date) {

            selectDateDialog (join_start_activity_date);
        }


        /**报名结束时间**/
        if (id == R.id.join_end_activity_time) {

            selectTimeDialog (join_end_activity_time);
        }


        if (id == R.id.join_end_activity_date) {


            selectDateDialog (join_end_activity_date);
        }


    }


    private void selectDateDialog(final TextView start_activity_date) {

        new DatePickerDialog (this, new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                start_activity_date.setText (year + "/" + (monthOfYear + 1 )+ "/" + dayOfMonth);
            }
        }, year, month, day).show ();
    }

    private void selectTimeDialog(final TextView start_activity_date) {

        new TimePickerDialog (this, new TimePickerDialog.OnTimeSetListener () {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                start_activity_date.setText (hourOfDay + ":" + minute);
            }
        }, hour, minute, true).show ();
    }

    public void backContext(View v) {

        finish ();
    }

    public void publishActivity(View v) {


        Toast.makeText (this, "发布活动", Toast.LENGTH_SHORT).show ();


        StringBuffer post_param = new StringBuffer ();

        /*post_param.append ("?att_title=" + activity_title.getText ());

        post_param.append ("&att_time=" + start_activity_date.getText () + " " + start_activity_time.getText () + "-" + end_activity_date.getText () + " " + end_activity_time.getText ());

        post_param.append ("&att_address=" + activity_address.getText ());

        post_param.append ("&att_content=" + activity_details.getText ());

        post_param.append ("&user_nickname=" + "用户昵称1111");

        post_param.append ("&city_name=" + "汕尾");

        post_param.append ("&theme_name=" + "其他");
        */
        post_param.append ("?att_title=" + "标题111111111111");

        post_param.append ("&att_time=" + "2016/6/15 16:30 - 2016/6/20 19:00");

        post_param.append ("&att_address=" + "汕尾");

        post_param.append ("&att_content=" + "活动描述.........................");

        post_param.append ("&user_nickname=" + "用户昵称1111");

        post_param.append ("&city_name=" + "昵称141984");

        post_param.append ("&theme_name=" + "其他");

        new StringLoad (StringLoad.METHOD_GET) {
            @Override
            public void executeUI(String result) {

                Log.i (TAG, "executeUI:  "  + result);

            }
        }.execute (PublicUtil.PUBLISH_ACTIVITY +post_param.toString ());

        Log.i (TAG, "publishActivity: "  + PublicUtil.PUBLISH_ACTIVITY +post_param.toString ()) ;

    }


}
