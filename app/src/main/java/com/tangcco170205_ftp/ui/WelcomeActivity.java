package com.tangcco170205_ftp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.tangcco170205_ftp.Constants;
import com.tangcco170205_ftp.R;
import com.tangcco170205_ftp.bean.AllDataBean;
import com.tangcco170205_ftp.db.DBHelper;
import com.tangcco170205_ftp.okhttp.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mProgressBar.setVisibility(View.GONE);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
    };
    private DBHelper dbHelper;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        new Thread(queryAllrunnable).start();

    }

    Runnable queryAllrunnable = new Runnable() {
        public void run() {
            try {
                String response = OkHttpClientManager.getAsString(Constants.ALLDATA);
                JSONArray jsonArray = new JSONArray(response);
                dbHelper = new DBHelper(WelcomeActivity.this);
                List<AllDataBean> allDataQuery = dbHelper.queryAll();
                if (jsonArray.length() == allDataQuery.size()) {
                    mHandler.sendEmptyMessage(0);
                    return;
                }
                AllDataBean allDataBean;
                List<AllDataBean> allDataGet = new ArrayList<AllDataBean>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    allDataBean = new AllDataBean();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    allDataBean.setTitle(jsonObject.getString("title"));
                    allDataBean.setActor(jsonObject.getString("actor"));
                    allDataBean.setDirector(jsonObject.getString("director"));
                    allDataBean.setCategory(jsonObject.getString("category"));
                    allDataBean.setClick(jsonObject.getInt("click"));
                    allDataBean.setPic(jsonObject.getString("pic"));
                    allDataGet.add(allDataBean);
                }
                if (allDataGet.size() != allDataQuery.size()) {
                    dbHelper.deleteAll();
                    dbHelper.insert(allDataGet);
                    mHandler.sendEmptyMessage(0);
                } else {
                    mHandler.sendEmptyMessage(0);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
