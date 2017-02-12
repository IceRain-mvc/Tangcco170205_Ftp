package tangcco.myview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import tangcco.myview.R;

/**
 * Created by Administrator on 2016/10/26.
 */

public class MyView1 extends View {

    private static final String TAG = "TAG";
    private Paint mPaint;


    public MyView1(Context context) {
        super(context);
        init();
        Log.d(TAG, "MyView1_1: ");
    }


    public MyView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        Log.d(TAG, "MyView1_2: ");

    }

    public MyView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        Log.d(TAG, "MyView1_3: ");
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

    }

    /**
     * 绘制的时候调用
     *
     * @param canvas 画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(60, 60, 300, 300);

//        canvas.drawRect(rectF, mPaint);
        canvas.drawOval(rectF, mPaint);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(0, 180, 500, 180, mPaint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawBitmap(bitmap, 330f, 310f, mPaint);
    }


    /**
     * 测量view的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 如果有子控件 则必须重写 给子控件布局
     *
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

    /**
     * 监听手指在屏幕上的事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.onTouchEvent(event);

    }
}
