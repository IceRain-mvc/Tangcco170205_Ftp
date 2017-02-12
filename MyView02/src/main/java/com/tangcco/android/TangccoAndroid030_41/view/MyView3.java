package com.tangcco.android.TangccoAndroid030_41.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tangcco.android.TangccoAndroid030_41.R;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class MyView3 extends LinearLayout {
    private ImageButton mBtn;
    private TextView mTv;

    public MyView3(Context context) {
        super(context);
       // init(context);
    }

    public MyView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        //init(context);
        View view = LayoutInflater.from(context).inflate(R.layout.compount_item, this, true);
        mTv = (TextView) view.findViewById(R.id.tvImageBtnWithText);
        mBtn = (ImageButton) view.findViewById(R.id.imageBtnImageBtnWithText);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView3);
        CharSequence text = typedArray.getText(R.styleable.MyView3_Text);
        mTv.setText(text);

        int color = typedArray.getColor(R.styleable.MyView3_android_textColor, Color.BLACK);
        mTv.setTextColor(color);

        float dimension = typedArray.getDimension(R.styleable.MyView3_TextSize,40);
        mTv.setTextSize(dimension);

        Drawable drawable = typedArray.getDrawable(R.styleable.MyView3_android_src);
        mBtn.setImageDrawable(drawable);
    }

    public MyView3(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
       // init(context);
    }

    public void init(Context context){
//        View view = LayoutInflater.from(context).inflate(R.layout.compount_item, this, true);
//        mTv = (TextView) view.findViewById(R.id.tvImageBtnWithText);
//        mBtn = (ImageButton) view.findViewById(R.id.imageBtnImageBtnWithText);
//        mTv.setText("这是一个TextView");
//        mTv.setTextColor(Color.RED);
//        mBtn.setImageResource(R.drawable.ic_launcher);
    }
}
