package com.seekman.theme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seekman.library.bean.Theme;
import com.seekman.library.utils.ImageLoad;
import com.seekman.theme.R;

import java.util.List;

/**
 * Created by chen-gui on 16-5-27.
 */
public class ThemeCategoryAdapter extends RecyclerView.Adapter<ThemeCategoryAdapter.ThemeViewHolder> {
    private List<Theme> mData = null;
    private Context context = null;

    public ThemeCategoryAdapter(List<Theme> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    public interface ItemClickLister {
        public void onItemClickLister(View view, int position);

        public void onLongItemClickLister(View view, int position);
    }

    private ItemClickLister itemClickLister = null;

    public void setOnItemLister(ItemClickLister lister) {
        this.itemClickLister = lister;
    }

    @Override
    public ThemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.theme_category_item, null);

        return new ThemeViewHolder (view);
    }

    @Override
    public void onBindViewHolder(ThemeViewHolder holder, final int position) {

            Theme themeItemData = mData.get (position);
            holder.mThemeItem.setOnClickListener (new View.OnClickListener () {

                @Override
                public void onClick(View v) {
                    if (itemClickLister != null) {
                        itemClickLister.onItemClickLister (v, position);
                    }
                }
            });

            holder.mThemeTitle.setText (themeItemData.getTheme_name ());

            ImageView icon = holder.mThemeCategoryIcon;
            icon.setTag (mData.get (position).getTheme_img ());

            new ImageLoad (icon).execute (mData.get (position).getTheme_img ());




    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }


    class ThemeViewHolder extends RecyclerView.ViewHolder {


        private TextView mThemeTitle = null;
        private ImageView mThemeCategoryIcon = null;
        private View mThemeItem = null;

        public ThemeViewHolder(View itemView) {
            super (itemView);

            ThemeViewHolder.this.mThemeItem = itemView;
            mThemeTitle = (TextView) mThemeItem.findViewById (R.id.theme_title);
            mThemeCategoryIcon = (ImageView) mThemeItem.findViewById (R.id.theme_category_icon);

        }
    }
}
