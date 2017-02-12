package com.tangcco170205_ftp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tangcco170205_ftp.R;
import com.tangcco170205_ftp.bean.VideoInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/29.
 */

public class MyAdapter extends BaseAdapter {
    private ArrayList<VideoInfo> videos;
    private LayoutInflater inflater;

    public MyAdapter(Context ctx, ArrayList<VideoInfo> videos) {
        super();
        this.videos = videos;
        inflater = LayoutInflater.from(ctx);
    }

    public int getCount() {
        return videos!=null?videos.size():0;
    }

    public Object getItem(int position) {
        return videos.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        VideoInfo video = videos.get(position);
        ViewHolder viewHolder;

        Log.i("listview", "This is position:" + position + "");

        if (convertView == null) {
            Log.i("listview", "convertView =null,findview inflate");
            // 行布局模板
            convertView = inflater.inflate(R.layout.list_item_videoinfo, null);
            viewHolder = new ViewHolder();
            // 找到行布局模板中要显示的控件
            viewHolder.thumbnail = (ImageView) convertView
                    .findViewById(R.id.iv_video_thumbnail);
            viewHolder.name = (TextView) convertView
                    .findViewById(R.id.tv_video_name);
            viewHolder.size = (TextView) convertView
                    .findViewById(R.id.tv_video_size);

            convertView.setTag(viewHolder);
        } else {
            Log.i("listview", "convertView !=null,findview inflate");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (convertView != null) {
            Log.i("listview", "convertView !=null,set value");
            // 给控件赋值
            viewHolder.thumbnail.setImageBitmap(video.thumbnail);
            viewHolder.name.setText(video.name);
            viewHolder.size.setText(video.size);
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView thumbnail;
        TextView name;
        TextView size;
    }
}
