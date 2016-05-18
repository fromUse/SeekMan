package com.seekman.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seekman.R;


public class Top extends RelativeLayout {

    private Button leftButton,rightButton;
    private TextView tvTitle;

    private int mleftTextColor;
    private Drawable mleftBackgound;
    private String mleftText;

    private int mrightTextColor;
    private Drawable mrightBackgound;
    private String mrightText;

    private int mtitleTextColor;
    private float mtitleTextSize;
    private String mtitle;

    private LayoutParams leftParams,rightParams,titleParams;

    private topClickListener listener;

    public interface topClickListener{
        public void leftClick();
        public void rightClick();
    }
    public void setOntopClickListener(topClickListener listener){
        this.listener= listener;
    }

    public Top(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.topder);

        mleftTextColor =ta.getColor(R.styleable.topder_mleftTextColor,0);
        mleftBackgound =ta.getDrawable(R.styleable.topder_mleftBackground);
        mleftText =ta.getString(R.styleable.topder_mleftText);

        mrightTextColor =ta.getColor(R.styleable.topder_mrightTextColor,0);
        mrightBackgound =ta.getDrawable(R.styleable.topder_mrightBackground);
        mrightText =ta.getString(R.styleable.topder_mrightText);

        mtitleTextColor =ta.getColor(R.styleable.topder_mtitleTextColor,0);
        mtitleTextSize =ta.getDimension(R.styleable.topder_mtitleTextSize,0);
        mtitle =ta.getString(R.styleable.topder_mtitle);

        ta.recycle();

        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        leftButton.setTextColor(mleftTextColor);
        leftButton.setBackground(mleftBackgound);
        leftButton.setText(mleftText);

        rightButton.setTextColor(mrightTextColor);
        rightButton.setBackground(mrightBackgound);
        rightButton.setText(mrightText);

        setBackgroundColor(android.graphics.Color.parseColor("#191970"));

        tvTitle.setTextColor(mtitleTextColor);
        tvTitle.setTextSize(mtitleTextSize);
        tvTitle.setText(mtitle);
        tvTitle.setGravity(Gravity.CENTER);

        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(leftButton,leftParams);

        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(rightButton,rightParams);

        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(tvTitle,titleParams);

        leftButton .setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });

        rightButton .setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });
    }
    public void setleftIsvisable(boolean flag){
        if(flag){
                leftButton.setVisibility(View.VISIBLE);
        }else {
            leftButton.setVisibility(View.GONE);
        }
    }
    public void setrightIsvisable(boolean flag){
        if(flag){
            rightButton.setVisibility(View.VISIBLE);
        }else {
            rightButton.setVisibility(View.GONE);
        }
    }

    public void setPagerTitle(String title){

        tvTitle.setText (title);

    }
}
