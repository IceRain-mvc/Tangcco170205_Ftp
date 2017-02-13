package com.tangcco170205_ftp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.tangcco170205_ftp.Constants;
import com.tangcco170205_ftp.R;
import com.tangcco170205_ftp.bean.AllDataBean;
import com.tangcco170205_ftp.db.DBHelper;
import com.tangcco170205_ftp.okhttp.OkHttpClientManager;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private String url = "";
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        OkHttpClientManager.getAsyn(Constants.BASEURL + Constants.ALLDATA, new OkHttpClientManager.ResultCallback<List<AllDataBean>>() {

            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(WelcomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<AllDataBean> response) {
//                EventBus.getDefault().post(new EventBusBean(response));
                dbHelper = new DBHelper(WelcomeActivity.this);
                List<AllDataBean> allDataBeen = dbHelper.queryAll();
                if (allDataBeen.size() != response.size()) {
                    dbHelper.deleteAll();
                    dbHelper.insert(response);
                }
//                SystemClock.sleep(2000);
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
