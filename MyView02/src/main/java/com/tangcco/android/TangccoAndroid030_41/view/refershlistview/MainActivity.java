package com.tangcco.android.TangccoAndroid030_41.view.refershlistview;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tangcco.android.TangccoAndroid030_41.R;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    private RefreshListView listview;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private PullAdapter adapter;
    private String[] moonLight = {
            "桑桑",
            "夫子",
            "陈某",
            "红鱼",
            "君陌",
            "木柚",
            "皮皮",
            "唐",
            "叶苏"
    };

    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (RefreshListView) findViewById(R.id.listview);

        arrayList.add("宁缺");
        arrayList.add("宁缺");
        arrayList.add("宁缺");
        arrayList.add("宁缺");
        arrayList.add("宁缺");
        arrayList.add("宁缺");
        arrayList.add("宁缺");
        arrayList.add("宁缺");

        listview.setonDownRefreshListener(new RefreshListView.OnDownRefreshListener() {

            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    // 刷新过程中需要做的操作在这里
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < 10; i++) {
                            arrayList.add(moonLight[new Random()
                                    .nextInt(moonLight.length)]);
                        }
                        count++;
                        return null;
                    }

                    // 刷新完成后要通知listview进行界面调整
                    @Override
                    protected void onPostExecute(Void result) {
                        adapter.notifyDataSetChanged();
                        if (count == 100) {
                            listview.downFreshOver();
                        }
                    }

                }.execute(null, null, null);
            }
        });

        listview.setonRefreshListener(new RefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new AsyncTask<Void,Void,Void>() {
                    //刷新过程中需要做的操作在这里
                    protected Void doInBackground(Void... params)
                    {
                        try
                        {
                            Thread.sleep(300);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < 10; i++) {

                            arrayList.add(moonLight[new Random().nextInt(moonLight.length)]);
                        }
                        return null;
                    }
                    //刷新完成后要通知listview进行界面调整
                    @Override
                    protected void onPostExecute(Void result)
                    {
                        adapter.notifyDataSetChanged();
                        listview.onRefreshComplete();
                    }

                }.execute(null,null,null);
            }
        });

        adapter = new PullAdapter();
        listview.setAdapter(adapter);
        listview.setDivider(null);
    }
    class PullAdapter extends BaseAdapter{
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MainActivity.this);
            textView.setText(position+"");
            textView.setTextSize(20);
            return textView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }
    }
}
