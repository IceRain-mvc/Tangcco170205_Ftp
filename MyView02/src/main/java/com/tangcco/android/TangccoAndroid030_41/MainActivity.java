package com.tangcco.android.TangccoAndroid030_41;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tangcco.android.TangccoAndroid030_41.view.ClockView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MyView1 view = new MyView1(this);
//        setContentView(view);

//        setContentView(R.layout.activity_myview1);

//        setContentView(R.layout.activity_myview2);

//        setContentView(R.layout.activity_myview3);

//        setContentView(R.layout.activity_myview4);

//        setContentView(R.layout.activity_roundimage);
        setContentView(R.layout.time_layout);
        clockView = (ClockView) findViewById(R.id.mClock);
    }

    ClockView clockView;

    public void startTime(View view) {
        clockView.startClock();

    }

    public void stopTime(View view) {
        clockView.stopClock();

    }
}
