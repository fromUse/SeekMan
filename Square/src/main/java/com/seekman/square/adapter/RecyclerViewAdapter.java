package com.seekman.square.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seekman.library.utils.ImageLoad;
import com.seekman.square.R;
import com.seekman.square.bean.City_ActivityGsonData;

import java.util.List;

/**
 * Created by alice on 2016/6/3.
 * <p/>
 * <p/>
 * 适配器 RecyclerView
 * <p/>
 * 控件优势，最大限度复用控件，提高复用性能
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHold> {

    private List<City_ActivityGsonData> mData = null;
    private Context context = null;


    public RecyclerViewAdapter(List<City_ActivityGsonData> mData, Context context) {

        this.mData = mData;
        this.context = context;

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
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from (context).inflate (R.layout.recy_item_layout, null);

        return new ViewHold (convertView);
    }

    /**
     * 数据设置到相应的item上
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHold holder,final int position) {

        final ViewHold hold = holder;

        City_ActivityGsonData item = mData.get (position);

        hold.img.setTag (item.getPicture_name ());

        new ImageLoad (hold.img).execute (item.getPicture_name ());

        hold.theme_name.setText (item.getTheme_name ());
        hold.address.setText (item.getAtt_Address ());
        hold.title.setText (item.getAtt_Title ());
        hold.cityname.setText (item.getCity_name ());


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

                    itemClickLister.onLongItemClickLister (v, position);
                }
                return true;
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }


    /**
     * RecyclerView 继承内置Holder
     */
    public class ViewHold extends RecyclerView.ViewHolder {

        TextView theme_name = null;
        TextView address = null;
        TextView title = null;
        ImageView img = null;
        TextView cityname = null;
        View viewItem =  null;

        public ViewHold(View itemView) {
            super (itemView);
            ViewHold.this.viewItem =  itemView;
            /**控件绑定**/
            ViewHold.this.img = (ImageView) itemView.findViewById (R.id.gidviewimage);
            ViewHold.this.title = (TextView) itemView.findViewById (R.id.texttitle);
            ViewHold.this.address = (TextView) itemView.findViewById (R.id.address);
            ViewHold.this.theme_name = (TextView) itemView.findViewById (R.id.theme_name);
            ViewHold.this.cityname = (TextView) itemView.findViewById (R.id.cityname);

        }


    }
}
