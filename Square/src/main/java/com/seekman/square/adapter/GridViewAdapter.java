package com.seekman.square.adapter;

import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seekman.library.utils.ImageLoad;
import com.seekman.square.R;
import com.seekman.square.bean.City_ActivityGsonData;


import java.util.List;

/**
 * Created by alice on 2016/6/3.
 *
 *
 * 适配器 适用于ListView 或者GridView
 */
public class GridViewAdapter extends BaseAdapter {

    private List<City_ActivityGsonData> mData = null;
    private Context context = null;


    public GridViewAdapter(List<City_ActivityGsonData> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold = null;
        City_ActivityGsonData item = null;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview,null);
            hold.img = (ImageView) convertView.findViewById(R.id.gidviewimage);
            hold.title = (TextView) convertView.findViewById(R.id.texttitle);
            hold.address = (TextView) convertView.findViewById(R.id.address);
            hold.theme_name = (TextView) convertView.findViewById(R.id.theme_name);
            hold.cityname = (TextView) convertView.findViewById(R.id.cityname);

            hold.address.getBackground().setAlpha(100);
            hold.title.getBackground().setAlpha(50);
            hold.theme_name.getBackground().setAlpha(50);
            hold.cityname.getBackground().setAlpha(50);
            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }

        item = mData.get(position);

        hold.img.setTag(item.getPicture_name());
        new ImageLoad(hold.img).execute(item.getPicture_name());
        hold.theme_name.setText(item.getTheme_name());
        hold.address.setText(item.getAtt_Address());
        hold.title.setText(item.getAtt_Title());
        hold.cityname.setText(item.getCity_name());
        return convertView;
    }

    class ViewHold {
        TextView theme_name = null;
        TextView address = null;
        TextView title = null;
        ImageView img = null;
        TextView cityname = null;
    }
}
