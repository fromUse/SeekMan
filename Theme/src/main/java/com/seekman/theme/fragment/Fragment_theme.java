package com.seekman.theme.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.seekman.library.bean.Theme;
import com.seekman.library.utils.PublicUtil;
import com.seekman.library.utils.StringLoad;
import com.seekman.theme.Activity.ActivityDetailsContainer;
import com.seekman.theme.Animators.SpacesItemDecoration;
import com.seekman.theme.R;
import com.seekman.theme.adapter.ThemeCategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alice on 2016/5/19.
 */
public class Fragment_theme extends Fragment {
    private static final String TAG = "Fragment_theme";
    //主题
    private View root = null;
    private RecyclerView recyclerView = null;
    private List<Theme> mData = null;
    private ThemeCategoryAdapter adapter = null;
    private ProgressBar progressBar = null;

    private Handler handler = new Handler () {

        @Override
        public void handleMessage(Message msg) {


        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate (R.layout.theme_category_content, null);

        init ();
        data ();
        setting ();
        lister ();
        Log.i (TAG, "onCreateView: Fragment_theme");
        return root;
    }

    private void data() {

        mData = new ArrayList<Theme> ();


        /**异步请求服务器，获取json数据**/
        new StringLoad (StringLoad.METHOD_GET) {
            /**
             *
             * 当json数据成功返回时回调方法
             *  <p>此方法是UI线程回调</p>
             * **/
            @Override
            public void executeUI(String result) {
                /**解析json数据到List中**/
                initThemeData (result);
                progressBar.setVisibility (View.GONE);


                /**
                 *
                 * 数据解析完成后才可以，设置适配器
                 *
                 * 数据解析完成后才可以 UI相关的操作
                 *
                 * **/
                adapter = new ThemeCategoryAdapter (mData, getContext ());
                recyclerView.setAdapter (adapter);
                adapter.setOnItemLister (new ThemeCategoryAdapter.ItemClickLister () {
                    @Override
                    public void onItemClickLister(View view, int position) {

                        Intent it = new Intent (getContext (), ActivityDetailsContainer.class);
                        it.putExtra ("ThemeName", mData.get (position).getTheme_name ());
                        it.putExtra ("THEME_ICON_URL", mData.get (position).getTheme_img ());
                        startActivity (it);
                    }

                    @Override
                    public void onLongItemClickLister(View view, int position) {

                    }
                });

                adapter.notifyDataSetChanged ();
            }
        }.execute (PublicUtil.THEME_CATEGORY);


}

    /**
     * 解析数据到mData上
     *
     * @param result
     */
    private void initThemeData(String result) {

        try {

            if (result != null) {

                JSONObject jsonObject = new JSONObject (result);

                //  Toast.makeText (getContext (),jsonObject.getString ("status"),Toast.LENGTH_LONG).show ();

                if (jsonObject.getString ("status").equals ("200")) {
                    JSONArray data = jsonObject.getJSONArray ("data");
                    for (int i = 0; i < data.length (); i++) {
                        JSONObject theme = data.getJSONObject (i);

                        Theme item = new Theme ();

                        item.setId (theme.getString ("id"));
                        item.setTheme_name (theme.getString ("theme_Name"));
                        item.setTheme_img (theme.getString ("theme_Images"));

                        mData.add (item);
                    }

                }
            } else {

                /**隐藏圈**/
                Toast.makeText (getContext (), R.string.net_error, Toast.LENGTH_SHORT).show ();

             /*   Intent it = new Intent (getContext (), ActivityTest.class);
                startActivity (it);*/
            }


        } catch (JSONException e) {
            e.printStackTrace ();
        }
    }


    private void setting() {

        recyclerView.setLayoutManager (new GridLayoutManager (getContext (), 1, GridLayoutManager.VERTICAL, false));

        recyclerView.addItemDecoration (new SpacesItemDecoration (20));


    }

    private void lister() {
    }

    private void init() {
        recyclerView = (RecyclerView) root.findViewById (R.id.theme_container);
        progressBar = (ProgressBar) root.findViewById (R.id.progressBar);
    }


}
