package com.seekman.template;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.seekman.R;


public class Bot extends RelativeLayout {

    private Button dleftButton, drightButton, dcentButton;
    private int dleftTextColor;
    private Drawable dleftBackgound;
    private String dleftText;

    private int drightTextColor;
    private Drawable drightBackgound;
    private String drightText;

    private int dcentTextColor;
    private Drawable dcentBackgound;
    private String dcentText;

    private LayoutParams dleftParams, drightParams, dcentParams;

    private botClickListener listener;

    public interface botClickListener {
        public void dleftClick();

        public void drightClick();

        public void dcentClick();
    }

    public void setOnbotClickListener(botClickListener listener) {
        this.listener = listener;
    }

    public Bot(Context context, AttributeSet attrsb) {
        super (context, attrsb);


        TypedArray ta = context.obtainStyledAttributes (attrsb, R.styleable.bottom);

        dleftTextColor = ta.getColor (R.styleable.bottom_dleftTextColor, 0);
        dleftBackgound = ta.getDrawable (R.styleable.bottom_dleftBackground);
        dleftText = ta.getString (R.styleable.bottom_dleftText);

        drightTextColor = ta.getColor (R.styleable.bottom_drightTextColor, 0);
        drightBackgound = ta.getDrawable (R.styleable.bottom_drightBackground);
        drightText = ta.getString (R.styleable.bottom_drightText);

        dcentTextColor = ta.getColor (R.styleable.bottom_dcentTextColor, 0);
        dcentBackgound = ta.getDrawable (R.styleable.bottom_dcentBackground);
        dcentText = ta.getString (R.styleable.bottom_dcentText);

        ta.recycle ();

        dleftButton = new Button (context);
        drightButton = new Button (context);
        dcentButton = new Button (context);

        dleftButton.setTextColor (dleftTextColor);
        dleftButton.setBackground (dleftBackgound);
        dleftButton.setText (dleftText);

        drightButton.setTextColor (drightTextColor);
        drightButton.setBackground (drightBackgound);
        drightButton.setText (drightText);

        dcentButton.setTextColor (dcentTextColor);
        dcentButton.setBackground (dcentBackgound);
        dcentButton.setText (dcentText);

        //setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));

        dleftParams = new LayoutParams (ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        dleftParams.addRule (RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        dleftParams.setMargins (40, 0, 0, 0);
        addView (dleftButton, dleftParams);

        drightParams = new LayoutParams (ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        drightParams.setMargins (0, 0, 40, 0);
        drightParams.addRule (RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);

        addView (drightButton, drightParams);

        dcentParams = new LayoutParams (ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        dcentParams.addRule (RelativeLayout.CENTER_HORIZONTAL, TRUE);
        addView (dcentButton, dcentParams);

        dleftButton.setOnClickListener (new OnClickListener () {
            @Override
            public void onClick(View v) {
                listener.dleftClick ();
            }
        });

        drightButton.setOnClickListener (new OnClickListener () {
            @Override
            public void onClick(View v) {
                listener.drightClick ();
            }
        });
        dcentButton.setOnClickListener (new OnClickListener () {
            @Override
            public void onClick(View v) {
                listener.dcentClick ();
            }
        });
    }


    public void setLeftBackgroundResource(int resourceID){

        dleftButton.setBackgroundResource (resourceID);
    }
    public void setCenterBackgroundResource(int resourceID){

        dcentButton.setBackgroundResource (resourceID);
    }
    public void setRightBackgroundResource(int resourceID){

        drightButton.setBackgroundResource (resourceID);
    }

    public void setAllBackgroundToNormal(){
//        dleftButton.setBackgroundResource (R.mipmap.home_normal);
//        dcentButton.setBackgroundResource (R.mipmap.community_normal);
//        drightButton.setBackgroundResource (R.mipmap.user_info_normal);
    }
}
