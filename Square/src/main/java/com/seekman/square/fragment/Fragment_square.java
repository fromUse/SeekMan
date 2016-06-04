package com.seekman.square.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seekman.library.utils.StringLoad;
import com.seekman.square.R;
import com.seekman.square.activity.CityPickerActivity;
import com.seekman.square.activity.WeatherActivity;
import com.seekman.square.adapter.RecyclerViewAdapter;
import com.seekman.square.adapter.SpacesItemDecoration;
import com.seekman.square.bean.City_ActivityGsonData;
import com.seekman.square.bean.Results;
import com.seekman.square.bean.SendCity_ActivityCson_3;
import com.seekman.square.bean.WeatherBean;
import com.seekman.square.bean.WeatherData;

import java.util.ArrayList;
import java.util.List;

public class Fragment_square extends Fragment {
//广场

    private View root = null;
    private String cityname;
    private TextView topcity;
    private TextView topweather;
    private String uli;
    //     private GridView gridView;
    private List<City_ActivityGsonData> mData = null;
    //   private GridViewAdapter adapter = null;
    /** 改用RecyclerView **/
    private RecyclerView square_recyclerview = null;
    private RecyclerViewAdapter recyclerViewAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate (R.layout.square_main, null);
        cityname = "汕尾";
        init ();
        jump ();
        weather ();
        inUli ();
        data ();
        setting ();
        topcity.setText (cityname);
        return root;
    }


    /**
     * 跳转
     * topcity跳转城市列表
     * topwather跳转天气页面
     * gridView
     */

    private void jump() {

    }


    /**
     * 获取第一天天气
     */
    public void weather() {
        uli = "http://api.map.baidu.com/telematics/v3/weather?location=" + cityname + "&output=json&ak=FK9mkfdQsloEngodbFl4FeY3";
        new StringLoad (StringLoad.METHOD_GET) {
            @Override
            public void executeUI(String result) {
                //System.out.print(result);
                Data (result);
            }

            private void Data(String result) {
                Gson gson = new Gson ();
                WeatherBean bean = gson.fromJson (result, WeatherBean.class);
                if (bean != null) {
                    ArrayList<Results> mResult = bean.getResults ();
                    for (int i = 0; i < mResult.size (); i++) {
                        Results res = mResult.get (i);
                        ArrayList<WeatherData> mWeatherData = res.getWeather_data ();
                        for (int j = 0; j < mWeatherData.size (); j++) {
                            WeatherData wea = mWeatherData.get (j);
                            switch (j) {
                                case 0:
                                    topweather.setText (wea.getWeather ());
                                    break;
                            }
                        }
                    }
                }

            }
        }.execute (uli);
    }

    //推荐 数据
    private void inUli() {
        new StringLoad (StringLoad.METHOD_GET) {
            @Override
            public void executeUI(String result) {
                Data (result);
            }

            private void Data(String result) {

                mData = new ArrayList<City_ActivityGsonData> ();

                Gson gson = new Gson ();
                SendCity_ActivityCson_3 send = gson.fromJson (result, SendCity_ActivityCson_3.class);
                if (send != null) {
                    List<City_ActivityGsonData> list = send.getList ();
                    for (int i = 0; i < list.size (); i++) {
                        City_ActivityGsonData activity = list.get (i);
                        mData.add (activity);
                        //   adapter = new GridViewAdapter (mData, getContext ());
                        //          gridView.setAdapter (adapter);


                        /**给RecyclerView设置适配器**/
                        recyclerViewAdapter = new RecyclerViewAdapter (mData, getContext ());
                        /**当网络加载完成后在设置事件的监听**/
                        lister ();

                        square_recyclerview.setAdapter (recyclerViewAdapter);
                        square_recyclerview.setLayoutManager (new GridLayoutManager (getContext (), 2, GridLayoutManager.VERTICAL, false));
                        square_recyclerview.addItemDecoration (new SpacesItemDecoration (3));


                    }
                }

            }
        }.execute ("http://172.30.84.10:8082/AndroidServer/sendcity_activitydata?city_name=" + cityname);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            cityname = data.getStringExtra ("picked_city");
            topcity.setText (cityname);
            weather ();
            inUli ();
        }

    }

    private void data() {

    }

    private void setting() {

    }

    //事件处理
    private void lister() {
        topcity.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity (), CityPickerActivity.class);
                startActivityForResult (intent, 3);
            }
        });
        topweather.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity (), WeatherActivity.class);
                intent.putExtra ("CITY_NAME", cityname);
                startActivity (intent);
            }
        });


     /*   gridView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText (getContext (), "itemid:" + id, Toast.LENGTH_LONG).show ();
            }
        });*/


        /**给控件设置自定义点击事件**/
        recyclerViewAdapter.setOnItemLister (new RecyclerViewAdapter.ItemClickLister () {
            @Override
            public void onItemClickLister(View view, int position) {
                Toast.makeText (getContext (), "Click  :" + position, Toast.LENGTH_LONG).show ();
            }

            @Override
            public void onLongItemClickLister(View view, int position) {
                Toast.makeText (getContext (), "longClick  :" + position, Toast.LENGTH_LONG).show ();
            }
        });

    }

    //初始化
    private void init() {
        //  gridView = (GridView) root.findViewById (R.id.gridView);
        topcity = (TextView) root.findViewById (R.id.topcity);
        topweather = (TextView) root.findViewById (R.id.topweather);

        square_recyclerview = (RecyclerView) root.findViewById (R.id.square_recyclerview);
    }


}
