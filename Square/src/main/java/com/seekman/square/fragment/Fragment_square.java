package com.seekman.square.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private static final String TAG = "Fragment_square";

    public static final int FIRST_LOAD = 1;
    public static final int CHANGE_CITY = 2;
    public static final int REFRESH_DATA = 3;
    private View root = null;
    private String cityname;
    private TextView topcity;
    private TextView topweather;
    private String url;
    //     private GridView gridView;
    private List<City_ActivityGsonData> mData = null;
    //   private GridViewAdapter adapter = null;
    /** 改用RecyclerView **/
    private RecyclerView square_recyclerview = null;
    private RecyclerViewAdapter recyclerViewAdapter = null;
    /** 下拉刷新容器 **/
    private SwipeRefreshLayout pullRefresh = null;

    /** Handler 用于通知UI线程 **/

    private Handler handler = new Handler () {

        @Override
        public void handleMessage(Message msg) {

            /**隐藏下拉刷新的圈**/

            if (pullRefresh != null) {

                pullRefresh.setRefreshing (false);
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate (R.layout.square_main, null);
        cityname = "汕尾";
        init ();
        loadWeather ();
        loadActivity (FIRST_LOAD);
        setting ();
        topcity.setText (cityname);
        return root;
    }


    /**
     * 获取第一天天气
     */
    public void loadWeather() {

        url = "http://api.map.baidu.com/telematics/v3/weather?location=" + cityname + "&output=json&ak=FK9mkfdQsloEngodbFl4FeY3";
        new StringLoad (StringLoad.METHOD_GET) {
            @Override
            public void executeUI(String result) {
                //System.out.print(result);
                if (result != null) {
                    initWeather (result);
                } else {
                    pullRefresh.setRefreshing (false);
                    Toast.makeText (getContext (), "网络貌似有问题....", Toast.LENGTH_SHORT).show ();
                }
            }

        }.execute (url);
    }

    /**
     * 初始化天头部气数据
     *
     * @param result
     */
    private void initWeather(String result) {
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

    //推荐 数据
    private void loadActivity(final int tag) {
        new StringLoad (StringLoad.METHOD_GET) {

            @Override
            public void executeUI(String result) {


                switch (tag) {

                    /**首次加载**/
                    case FIRST_LOAD:
                        if (recyclerViewAdapter == null) {
                            firstLoad (result);
                            lister ();
                        }
                        break;
                    /**切换城市时加载数据**/
                    case CHANGE_CITY:
                        if (recyclerViewAdapter != null) {
                            changeCityLoad (result);
                            lister ();
                        }
                        break;
                    /**下拉刷新时加载数据**/
                    case REFRESH_DATA:

                        if (recyclerViewAdapter != null) {
                            pullToRefresh (result);
                            lister ();
                        }
                        break;
                }


            }
        }.execute ("http://172.30.84.10:8082/AndroidServer/sendcity_activitydata?city_name=" + cityname);
    }


    /**
     * 下拉刷新加载数据
     *
     * @param result
     */
    private void pullToRefresh(String result) {

        Gson gson = new Gson ();

        SendCity_ActivityCson_3 send = gson.fromJson (result, SendCity_ActivityCson_3.class);

        if (send != null) {
            /**记得判断状态码**/
            /**当状态码为200表示list有数据**/

            switch (send.getStatus ()) {


                case "200":
                    List<City_ActivityGsonData> list = send.getList ();
                    for (int i = 0; i < list.size (); i++) {
                        City_ActivityGsonData activity = list.get (i);
                        mData.add (activity);
                    }
                    /**通知适配器数据更新了**/
                    recyclerViewAdapter.notifyDataSetChanged ();
                    break;

                /**当状态码为404表示list为null**/
                case "404":


                    if (mData.size () == 0) {
                        Toast.makeText (getContext (), "当前城市没有数据...", Toast.LENGTH_SHORT).show ();
                    } else {

                        Toast.makeText (getContext (), "没有更多数据了...", Toast.LENGTH_SHORT).show ();
                    }

                    break;

            }

            /**通知UI线程隐藏  圈圈**/
            pullRefresh.setRefreshing (false);
        }


    }


    /**
     * 将数据解析到集合中
     * 并设置适配器，布局管理器
     *
     * @param result
     */
    private void firstLoad(String result) {

        mData = new ArrayList<City_ActivityGsonData> ();

        Gson gson = new Gson ();

        SendCity_ActivityCson_3 send = gson.fromJson (result, SendCity_ActivityCson_3.class);

        if (send != null) {
            /**记得判断状态码**/
            /**当状态码为200表示list有数据**/

            switch (send.getStatus ()) {
                case "200":
                    List<City_ActivityGsonData> list = send.getList ();
                    for (int i = 0; i < list.size (); i++) {
                        City_ActivityGsonData activity = list.get (i);
                        mData.add (activity);
                        //   adapter = new GridViewAdapter (mData, getContext ());
                        //          gridView.setAdapter (adapter);
                    }
                    /**给RecyclerView设置适配器**/
                    recyclerViewAdapter = new RecyclerViewAdapter (mData, getContext ());
                    /**当网络加载完成后在设置事件的监听**/
                    square_recyclerview.setAdapter (recyclerViewAdapter);
                    square_recyclerview.setLayoutManager (new GridLayoutManager (getContext (), 2, GridLayoutManager.VERTICAL, false));
                    break;

                /**当状态码为404表示list为null**/
                case "404":
                    Toast.makeText (getContext (), "当前城市没有数据...", Toast.LENGTH_SHORT).show ();
                    break;

            }

            /**通知UI线程隐藏  圈圈**/
            pullRefresh.setRefreshing (false);

        }

    }


    /**
     * 将数据解析到集合中
     * 并设置适配器，布局管理器
     *
     * @param result
     */
    private void changeCityLoad(String result) {

        /**首先清空数据**/
        mData.clear ();
        Gson gson = new Gson ();
        SendCity_ActivityCson_3 send = gson.fromJson (result, SendCity_ActivityCson_3.class);

        if (send != null) {
            /**记得判断状态码**/
            /**当状态码为200表示list有数据**/

            switch (send.getStatus ()) {
                case "200":
                    List<City_ActivityGsonData> list = send.getList ();
                    for (int i = 0; i < list.size (); i++) {
                        City_ActivityGsonData activity = list.get (i);
                        mData.add (activity);
                    }
                    /**通知适配器数据更新了**/
                    recyclerViewAdapter.notifyDataSetChanged ();
                    /**当状态码为404表示list为null**/
                    break;

                case "404":
                    recyclerViewAdapter.notifyDataSetChanged ();
                    Toast.makeText (getContext (), "当前城市没有数据...", Toast.LENGTH_SHORT).show ();
                    break;

            }
            /**通知UI线程隐藏  圈圈**/
            pullRefresh.setRefreshing (false);
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            cityname = data.getStringExtra ("picked_city");
            topcity.setText (cityname);
            loadWeather ();
            pullRefresh.setRefreshing (true);
            loadActivity (CHANGE_CITY);
        }

    }

    private void setting() {

        /**设置下拉刷新监听**/
        pullRefresh.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh() {
                //下拉刷新UI
                loadActivity (REFRESH_DATA);

            }
        });

        /**设置条目间的分割线   只要设置一次就够了 **/
        square_recyclerview.addItemDecoration (new SpacesItemDecoration (20));
    }

    //事件处理
    private void lister() {

        /**
         * 跳转
         * topcity跳转城市列表
         */
        topcity.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity (), CityPickerActivity.class);
                startActivityForResult (intent, 3);
            }
        });

        /**
         * 跳转
         * topwather跳转天气页面
         */
        topweather.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity (), WeatherActivity.class);
                intent.putExtra ("CITY_NAME", cityname);
                startActivity (intent);
            }
        });


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
        topcity = (TextView) root.findViewById (R.id.topcity);
        topweather = (TextView) root.findViewById (R.id.topweather);

        square_recyclerview = (RecyclerView) root.findViewById (R.id.square_recyclerview);

        pullRefresh = (SwipeRefreshLayout) root.findViewById (R.id.pullRefresh);
    }


}
