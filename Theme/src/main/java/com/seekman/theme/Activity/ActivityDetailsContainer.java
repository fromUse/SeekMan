package com.seekman.theme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.seekman.library.activity.PublishActivity;
import com.seekman.library.bean.ActivityBean;
import com.seekman.library.bean.ImageURL;
import com.seekman.library.utils.ImageLoad;
import com.seekman.library.utils.PublicUtil;
import com.seekman.library.utils.StringLoad;
import com.seekman.theme.Animators.ScaleInOutItemAnimator;
import com.seekman.theme.Animators.SpacesItemDecoration;
import com.seekman.theme.R;
import com.seekman.theme.adapter.ThemeCategoryAdapter;
import com.seekman.theme.adapter.ThemeDetailsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ActivityDetailsContainer extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ThemeDetailsAdapter.ItemClickLister {

    private static final String TAG = "ActivityTest";
    private CollapsingToolbarLayout collapsing_toolbar = null;
    private List<ActivityBean> mData = null;
    private ThemeCategoryAdapter adapter = null;

    private ThemeDetailsAdapter myAdapter = null;
    private RecyclerView recyclerview = null;
    private AppBarLayout appBarLayout = null;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    private String title = null;
    private String url =  null;
    private Handler handler = new Handler () {

        /**
         * 当刷新数据成功后，用handle来通知UI线程更新ui
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            /**隐藏下拉刷新的转圈圈的进度条**/
            mSwipeRefreshLayout.setRefreshing (false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.theme_details_container);

        init ();
        data ();
        setting ();
        lister ();
    }

    private void data() {

        Log.i (TAG, "data: ------------------------------");
        try {

             url = PublicUtil.THEME_ITEM + URLEncoder.encode (title, "utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace ();
        }

        /**启动页面加载json原始数据到本地**/
        new StringLoad (StringLoad.METHOD_GET) {
            @Override
            public void executeUI(String result) {
                /**初始化数据到mData中**/
                initThemeData (result);

                mSwipeRefreshLayout.setRefreshing (false);

                /**当数据处理完成后才为RecylerView设置Adapter**/
                myAdapter = new ThemeDetailsAdapter (ActivityDetailsContainer.this, mData);
                recyclerview.setAdapter (myAdapter);

                /**设置item点击事件**/
                myAdapter.setOnItemLister (ActivityDetailsContainer.this);
                /**设置item动画**/
                recyclerview.setItemAnimator (new ScaleInOutItemAnimator (recyclerview));
                /**设置item分割线**/
                recyclerview.addItemDecoration (new SpacesItemDecoration (30));


            }
        }.execute (url);


    }

    private void initThemeData(String result) {

        if (result != null)

            mData = new ArrayList<ActivityBean> ();

        try {


            JSONObject jsonObject = new JSONObject (result);

            String status = jsonObject.getString ("status");


            if (status.equals ("200")) {


                JSONArray list = jsonObject.getJSONArray ("list");


                for (int i = 0; i < list.length (); i++) {
                    JSONObject activity_item = (JSONObject) list.get (i);
                    ActivityBean item = new ActivityBean ();
                    item.setId (activity_item.getString ("id"));
                    item.setAtt_title (activity_item.getString ("att_Title"));
                    item.setAtt_time (activity_item.getString ("att_Time"));
                    item.setAtt_address (activity_item.getString ("att_Address"));
                    item.setAtt_content (activity_item.getString ("att_Content"));
                    item.setCity_id (activity_item.getString ("city_name"));
                    item.setTheme_name (activity_item.getString ("theme_name"));
                    item.setUser_name (activity_item.getString ("user_username"));
                    List<ImageURL> urls = new ArrayList<ImageURL> ();
                    urls.add (new ImageURL (null, activity_item.getString ("picture_name"), activity_item.getString ("user_username")));
                    item.setImages (urls);

                    mData.add (item);


                }

            } else {
                Toast.makeText (this, R.string.net_error, Toast.LENGTH_SHORT).show ();
            }
        } catch (JSONException e) {
            e.printStackTrace ();

        }

    }

    private void init() {
        recyclerview = (RecyclerView) findViewById (R.id.recyclerview);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById (R.id.collapsing_toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById (R.id.refresh);
        mSwipeRefreshLayout.setRefreshing (true);
        appBarLayout = (AppBarLayout) findViewById (R.id.appbar);
        Log.i (TAG, "init:------------------------------ ");


        title = getIntent ().getStringExtra ("ThemeName");

        String theme_icon_url = getIntent ().getStringExtra ("THEME_ICON_URL");

        ImageView backdrop = (ImageView) findViewById (R.id.backdrop);

        backdrop.setTag (theme_icon_url);

        new ImageLoad (backdrop).execute (theme_icon_url);


    }

    private void setting() {

        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager (1, StaggeredGridLayoutManager.VERTICAL);

        recyclerview.setLayoutManager (manager);


        collapsing_toolbar.setTitle (title);

        mSwipeRefreshLayout.setProgressViewOffset (true, -20, 100);
        Log.i (TAG, "setting: ---------------------------------------");
    }


    private void lister() {

        appBarLayout.addOnOffsetChangedListener (new AppBarLayout.OnOffsetChangedListener () {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {
                    mSwipeRefreshLayout.setEnabled (true);
                } else {
                    mSwipeRefreshLayout.setEnabled (false);
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener (this);

    }


    /**
     * 下拉刷新时会调用这个方法
     */
    @Override
    public void onRefresh() {

        /**模拟3秒钟刷新**/
        new StringLoad (StringLoad.METHOD_GET) {
            @Override
            public void executeUI(String result) {

                initThemeData (result);

                mSwipeRefreshLayout.setRefreshing (false);
                myAdapter.notifyDataSetChanged ();

            }
        }.execute (PublicUtil.THEME_ITEM + title);
    }

    /**单击事件短按**/
    @Override
    public void onItemClickLister(View view, int position) {

        Toast.makeText (this, "position : " + position, Toast.LENGTH_SHORT).show ();
        Intent it = new Intent (this, ActivityDetailsJoin.class);


        startActivity (it);
    }

    /**单击事件长按**/
    @Override
    public void onLongItemClickLister(View view, int position) {


    }

    public void addActivity(View v){

        //Toast.makeText (this,"发布活动",Toast.LENGTH_SHORT).show ();

        /**判断用户是否登陆**/

        Intent it =  new Intent (this, PublishActivity.class);

        startActivity (it);
    }
}
