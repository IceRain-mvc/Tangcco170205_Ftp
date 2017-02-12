package com.tangcco170205_ftp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tangcco170205_ftp.adapter.MyAdapter;
import com.tangcco170205_ftp.bean.VideoInfo;
import com.tangcco170205_ftp.player.SlideCutListView;
import com.tangcco170205_ftp.player.ZdyVideoViewMainActivity;
import com.tangcco170205_ftp.utils.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity implements FileUtils.IGetDataVideo, SlideCutListView.RemoveListener {

    Runnable runnable = new Runnable() {
        public void run() {
            //本地 视频文件 MP4/3gp 遍历
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(path);
            //正则
            FileFilter fileFilter = FileUtils.getFileFilter();
            FileUtils.getVideos(file, fileFilter);

        }
    };
    private ArrayList<VideoInfo> videoInfos = new ArrayList<>();
    private SlideCutListView mListView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mListView = (SlideCutListView) findViewById(R.id.mListView);
        FileUtils fileUtils = new FileUtils();
        //回调
        fileUtils.setDataVideo(this);

        adapter = new MyAdapter(this, videoInfos);
        mListView.setAdapter(adapter);
        new Thread(runnable).start();
        //注册接口
        mListView.setRemoveListener(this);


        RelativeLayout rela_empty  = (RelativeLayout) findViewById(R.id.rela_empty);

        mListView.setEmptyView(rela_empty);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoInfo videoInfo = videoInfos.get(position);
                String path = videoInfo.path;
                Intent intent = new Intent(VideoListActivity.this, ZdyVideoViewMainActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
    }

    @Override
    public void addData(VideoInfo videoInfo) {
        //数据源
        videoInfos.add(videoInfo);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        VideoInfo videoInfo = videoInfos.get(position);
        switch (direction) {
            case RIGHT:
                Toast.makeText(this, "文件位置:" + videoInfo.path, Toast.LENGTH_SHORT).show();
                break;
            case LEFT:
                String path = videoInfo.path;
                File file = new File(path);
                file.delete();//删除
                videoInfos.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
