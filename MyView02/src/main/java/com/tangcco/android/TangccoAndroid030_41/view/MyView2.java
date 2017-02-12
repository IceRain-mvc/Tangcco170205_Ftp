package com.tangcco.android.TangccoAndroid030_41.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class MyView2 extends ViewGroup {

    public MyView2(Context context) {
        super(context);
    }

    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 对子控件进行宽和高的计算
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int heigth = measureHeight(heightMeasureSpec);
        // 设置自定义的控件MyViewGroup的大小
        setMeasuredDimension(width, heigth);

        // 计算自定义的ViewGroup中所有子控件的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    //计算当前这个子控件的宽度
    private int measureWidth(int pWidthMeasureSpec) {//pWidthMeasureSpec->布局文件中的layout_width属性的值
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);
        int widthwidth = MeasureSpec.getSize(pWidthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.AT_MOST://包裹
            case MeasureSpec.EXACTLY://填充或者精确的数值
                result = widthwidth;
                break;
        }
        return result;
    }

    //计算当前控件的高度
    private int measureHeight(int pHeightMeasureSpec) {//pWidthMeasureSpec->布局文件中的layout_width属性的值
        int result = 0;
        int widthMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int widthheight = MeasureSpec.getSize(pHeightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.AT_MOST://包裹
            case MeasureSpec.EXACTLY://填充或者精确的数值
                result = widthheight;
                break;
        }
        return result;
    }

    /**
     * 对子控件进行布局
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 记录总高度
        int mTotalHeight = 0;

        // 遍历所有子视图
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            // 获取在onMeasure中计算的视图尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();

            childView.layout(l, mTotalHeight, measuredWidth, measureHeight + mTotalHeight);

            mTotalHeight = mTotalHeight + measureHeight;
        }
    }


}
