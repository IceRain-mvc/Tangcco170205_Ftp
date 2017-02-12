package com.tangcco.android.TangccoAndroid030_41.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tangcco.android.TangccoAndroid030_41.R;

public class MyView1 extends View {
    Paint paint;
    public MyView1(Context context) {
        super(context);
        Log.i("MyView1","MyView1_1");
        init();
    }

    public MyView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("MyView1","MyView1_2");
        init();
    }

    public MyView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("MyView1","MyView1_3");
        init();
    }

    public void init(){
        paint= new Paint();
        paint.setColor(Color.RED);
      //paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 绘图
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rect = new RectF(60,60,560,560);
        //canvas.drawRect(rect,paint);//画矩形
        //paint.setColor(Color.BLUE);
        //canvas.drawOval(rect,paint);//画椭圆

        paint.setColor(Color.BLUE);
        canvas.drawLine(10,380,660,380,paint);//画线条

     //   canvas.drawArc(rect,300f,140f,true,paint);

        paint.setColor(Color.RED);
        canvas.drawArc(rect,80f,220f,true,paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        canvas.drawBitmap(bitmap,330f,310f,paint);
    }

    /**
     * 计算控件大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * UNSPECIFIED	父容器没有对当前View有任何限制，当前View可以任意取尺寸
         * EXACTLY	当前的尺寸就是当前View应该取的尺寸
         * AT_MOST	当前尺寸是当前View能取的最大尺寸
         */
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                break;
        }
    }

    /**
     * 给子控件进行布局
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
