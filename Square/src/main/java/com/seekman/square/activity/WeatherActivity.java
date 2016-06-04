package com.seekman.square.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seekman.library.utils.ImageLoad;
import com.seekman.library.utils.StringLoad;
import com.seekman.square.R;
import com.seekman.square.adapter.CityListAdapter;
import com.seekman.square.bean.Index;
import com.seekman.square.bean.Results;
import com.seekman.square.bean.WeatherBean;
import com.seekman.square.bean.WeatherData;

import java.util.ArrayList;


public class WeatherActivity extends Activity implements View.OnClickListener {
    private TextView tv_cityTitle;
    private ImageView iv_status_iamge;
    private TextView tv_currentday_status;
    private TextView tv_currentday_temp;
    private TextView tv_currentday_wind;
    private TextView tv_currentday_data;
    private TextView tv_flue;
    private TextView tv_fluedetails;
    private TextView tv_support;
    private TextView tv_supportdetails;
    private ImageView iv_firststatus_iamge;
    private TextView first_data;
    private TextView first_status;
    private TextView first_tem;
    private ImageView iv_secondstatus_iamge;
    private TextView second_data;
    private TextView second_status;
    private TextView second_tem;
    private ImageView iv_thirdstatus_iamge;
    private TextView third_data;
    private TextView third_status;
    private TextView third_tem;
    private WeatherBean weatherBean;
    private TextView topback;
    private CityListAdapter mCityAdapter;
    private String mcityname = "北京";

    private SwipeRefreshLayout pullRefreshWeather = null;

    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.weather);

        mcityname = "汕尾";

        mcityname = getIntent ().getStringExtra ("CITY_NAME");

        url = "http://api.map.baidu.com/telematics/v3/weather?location=" + mcityname + "&output=json&ak=FK9mkfdQsloEngodbFl4FeY3";
        findViewById ();
        loadWeather ();
        lister ();
        topback.setOnClickListener (this);
    }

    private void lister() {

        pullRefreshWeather.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            /**
             * 下拉刷新天气信息
             */
            @Override
            public void onRefresh() {
                loadWeather ();
            }
        });

    }

    protected void findViewById() {
        tv_cityTitle = (TextView) findViewById (R.id.cityTitle);
        iv_status_iamge = (ImageView) findViewById (R.id.iv_status_image);
        tv_currentday_status = (TextView) findViewById (R.id.tv_currentday_status);
        tv_currentday_temp = (TextView) findViewById (R.id.tv_currentday_temp);
        tv_currentday_wind = (TextView) findViewById (R.id.tv_currentday_wind);
        tv_currentday_data = (TextView) findViewById (R.id.tv_currentday_data);
        tv_flue = (TextView) findViewById (R.id.tv_flue);
        tv_fluedetails = (TextView) findViewById (R.id.tv_fluedetails);
        tv_support = (TextView) findViewById (R.id.tv_support);
        tv_supportdetails = (TextView) findViewById (R.id.tv_supportdetails);
        first_data = (TextView) findViewById (R.id.first_data);
        first_status = (TextView) findViewById (R.id.first_status);
        first_tem = (TextView) findViewById (R.id.first_tem);
        second_data = (TextView) findViewById (R.id.second_data);
        second_status = (TextView) findViewById (R.id.second_status);
        second_tem = (TextView) findViewById (R.id.second_tem);
        third_data = (TextView) findViewById (R.id.third_data);
        third_status = (TextView) findViewById (R.id.third_status);
        third_tem = (TextView) findViewById (R.id.third_tem);
        iv_firststatus_iamge = (ImageView) findViewById (R.id.iv_firststatus_image);
        iv_secondstatus_iamge = (ImageView) findViewById (R.id.iv_secondstatus_image);
        iv_thirdstatus_iamge = (ImageView) findViewById (R.id.iv_thirdnstatus_image);
        topback = (TextView) findViewById (R.id.topback);

        pullRefreshWeather = (SwipeRefreshLayout) findViewById (R.id.pullRefreshWeather);
    }

    protected void loadWeather() {

        new StringLoad (StringLoad.METHOD_GET) {
            @Override
            public void executeUI(String result) {
                //System.out.print(result);
                initWeather (result);
            }

        }.execute (url);
    }


    private void initWeather(String result) {
        Gson gson = new Gson ();
        WeatherBean bean = gson.fromJson (result, WeatherBean.class);
        if (bean != null) {

            switch (bean.getStatus ()) {

                case "success":

                    //results中的数据
                    ArrayList<Results> mResult = bean.getResults ();
                    for (int i = 0; i < mResult.size (); i++) {
                        Results res = mResult.get (i);
                        tv_cityTitle.setText (res.getCurrentCity ());
                        //index中的数据
                        ArrayList<Index> index = res.getIndex ();
                        for (int j = 0; j < index.size (); j++) {
                            Index in = index.get (j);
                            switch (j) {
                                case 0:
                                    tv_flue.setText (in.getDes ());
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    tv_fluedetails.setText (in.getDes ());
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    tv_support.setText (in.getDes ());
                                    break;
                                case 5:
                                    tv_supportdetails.setText (in.getDes ());
                                    break;
                            }

                        }
                        //weather_data中的数据
                        ArrayList<WeatherData> mWeatherData = res.getWeather_data ();
                        for (int h = 0; h < mWeatherData.size (); h++) {
                            WeatherData wea = mWeatherData.get (h);
                            switch (h) {
                                //第一天天气
                                case 0:
                                    iv_status_iamge.setTag (wea.getDayPictureUrl ());
                                    new ImageLoad (iv_status_iamge).execute (wea.getDayPictureUrl ());
                                    tv_currentday_status.setText (wea.getWeather ());
                                    tv_currentday_temp.setText (wea.getTemperature ());
                                    tv_currentday_wind.setText (wea.getWind ());
                                    tv_currentday_data.setText (wea.getDate ());
                                    break;
                                case 1:
                                    iv_firststatus_iamge.setTag (wea.getDayPictureUrl ());
                                    new ImageLoad (iv_firststatus_iamge).execute (wea.getDayPictureUrl ());
                                    first_status.setText (wea.getWeather ());
                                    first_data.setText (wea.getDate ());
                                    first_tem.setText (wea.getTemperature ());
                                    break;
                                case 2:
                                    iv_secondstatus_iamge.setTag (wea.getDayPictureUrl ());
                                    new ImageLoad (iv_secondstatus_iamge).execute (wea.getDayPictureUrl ());
                                    second_status.setText (wea.getWeather ());
                                    second_data.setText (wea.getDate ());
                                    second_tem.setText (wea.getTemperature ());
                                    break;
                                case 3:
                                    iv_thirdstatus_iamge.setTag (wea.getDayPictureUrl ());
                                    new ImageLoad (iv_thirdstatus_iamge).execute (wea.getDayPictureUrl ());
                                    third_status.setText (wea.getWeather ());
                                    third_data.setText (wea.getDate ());
                                    third_tem.setText (wea.getTemperature ());
                                    break;

                            }


                        }
                    }
                    break;

                case "No result available":
                    Toast.makeText (WeatherActivity.this, "当前城市天气查询不到....", Toast.LENGTH_LONG).show ();
                    break;
            }

            pullRefreshWeather.setRefreshing (false);

        }


    }

    @Override
    public void onClick(View v) {

        if (v.getId () == R.id.topback) {
            finish ();
        }
    }


}

