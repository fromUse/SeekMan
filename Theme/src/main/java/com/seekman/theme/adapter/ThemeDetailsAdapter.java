package com.seekman.theme.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seekman.library.bean.ActivityBean;
import com.seekman.library.utils.ImageLoad;
import com.seekman.theme.Activity.ActivityDetailsContainer;
import com.seekman.theme.R;

import java.util.List;

/**
 * Created by chen-gui on 16-5-28.
 */
public class ThemeDetailsAdapter extends RecyclerView.Adapter<ThemeDetailsAdapter.MyHolder> {


    private static final String TAG = "MyAdapter";
    private ActivityDetailsContainer activityTest;
    private List<ActivityBean> mData = null;

    public ThemeDetailsAdapter(ActivityDetailsContainer activityTest, List<ActivityBean> mdata) {
        this.activityTest = activityTest;
        this.mData = mdata;
    }

    public interface ItemClickLister {
         void onItemClickLister(View view, int position);

         void onLongItemClickLister(View view, int position);
    }

    private ItemClickLister itemClickLister = null;


    public void setOnItemLister(ItemClickLister lister) {
        this.itemClickLister = lister;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (activityTest).inflate (R.layout.theme_details_item, null);

        Log.i (TAG, "onCreateViewHolder: --------------------------------");
        return new MyHolder (view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {


        ActivityBean bean = mData.get(position);

        holder.user_name.setText (bean.getUser_name ());
        holder.content.setText (bean.getAtt_content ());
        holder.title.setText (bean.getAtt_title ());
        holder.time.setText (bean.getAtt_time ());

        holder.city.setText (bean.getCity_id ());

        holder.img.setTag (bean.getImages ().get (0).getImage_url ());

        new ImageLoad (holder.img).execute (bean.getImages ().get (0).getImage_url ());

        holder.viewItem.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (itemClickLister != null) {

                    itemClickLister.onItemClickLister (v, position);
                }
            }
        });

        holder.viewItem.setOnLongClickListener (new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View v) {


                if (itemClickLister != null) {

                    int posi = holder.getLayoutPosition ();

                    itemClickLister.onLongItemClickLister (v, posi);
                }
                return true;
            }
        });
        Log.i (TAG, "onBindViewHolder: -------------------------------");

    }

    @Override
    public int getItemCount() {
        Log.i (TAG, "getItemCount: --------------------------" + mData.size ());
        return mData.size ();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        /** 活动条目根view **/
        View viewItem = null;
        /** 活动标题 **/
        TextView title = null;
        /** 活动内容 **/
        TextView content = null;
        /** 活动时间 **/
        TextView time = null;
        /** 活动用户名 **/
        TextView user_name = null;
        /** 活动所在区域 城市 **/
        TextView city = null;
        /** 活动图片 **/
        ImageView img = null;
        /** 本活动条目访问次数 **/
        TextView read_count = null;


        public MyHolder(View itemView) {

            super (itemView);
            this.viewItem = itemView;
            time = (TextView) viewItem.findViewById (R.id.activity_Time);
            title = (TextView) viewItem.findViewById (R.id.activity_title);
            content = (TextView) viewItem.findViewById (R.id.activity_Content);
            user_name = (TextView) viewItem.findViewById (R.id.activity_userName);
            city = (TextView) viewItem.findViewById (R.id.activity_City);
            img = (ImageView) viewItem.findViewById (R.id.activity_Img);

            /**服务器还没加这字段**/
            read_count = (TextView) viewItem.findViewById (R.id.activity_Read_Count);

        }
    }


}
