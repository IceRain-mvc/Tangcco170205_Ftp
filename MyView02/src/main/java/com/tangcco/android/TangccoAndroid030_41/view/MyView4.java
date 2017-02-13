package com.tangcco.android.TangccoAndroid030_41.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.tangcco.android.TangccoAndroid030_41.R;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class MyView4 extends EditText {
    private Drawable imgDel;
    private Context mContext;

    public MyView4(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MyView4(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public MyView4(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        setText("");

        imgDel = mContext.getResources().getDrawable(R.drawable.editdel_delete);

        setDrawable();

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setDrawable();
            }
        });
    }

    private void setDrawable() {
        if (length() < 1 || !hasFocus()){
            setCompoundDrawables(null, null, null, null);
        }else{
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgDel, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgDel != null && event.getAction() == MotionEvent.ACTION_UP) {
//            int eventX = (int) event.getRawX();
//            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
//            rect.left = rect.right - 150;
//            if (rect.contains(eventX, eventY))
//                setText("");

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (getCompoundDrawables()[2] != null) {
                    boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                            && (event.getX() < ((getWidth() - getPaddingRight())));

                    if (touchable) {
                        this.setText("");
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
