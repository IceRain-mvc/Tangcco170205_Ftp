package com.tangcco170205_ftp.utils;

import android.media.ThumbnailUtils;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.tangcco170205_ftp.bean.VideoInfo;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/29.
 */

public class FileUtils {

    public static ArrayList<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();
    private static IGetData iGetData;

    public static FileFilter getFileFilter() {
        FileFilter filterFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() ||
                        file.getName().matches("^.*?\\.(mp4|3gp)$"); //   .mp4,   .3gp
            }
        };
        return filterFilter;
    }
	
	    /**
     * 抓取SD卡的目录，也是对Environment.getExternalStorageDirectory的封装。
     *
     * @param writeable
     * @return null表示没有SD卡或者不能写
     */
    public static String getSDPath(boolean writeable) { 
        String state = Environment.getExternalStorageState();
        if (state.equals(android.os.Environment.MEDIA_MOUNTED)
                || (!writeable && state.equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY))) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }



    /**
     * 遍历SDCard，获取视频信息
     */
    public static ArrayList<VideoInfo> getVideos(File file, FileFilter filter) {
        File[] files = file.listFiles(filter);
        for (File f : files) {
            if (f.isDirectory() && f.canRead()) {
                getVideos(f, filter);
            } else if (f.isFile()) {
                VideoInfo video = new VideoInfo();
                video.name = f.getName();// 视频名称
                video.path = f.getAbsolutePath();// 视频路径
                video.size = dealSize(f.length());// 视频大小
                video.thumbnail = ThumbnailUtils.createVideoThumbnail(// 视频缩略图
                        f.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);

                Message message = mHandler.obtainMessage();
                message.obj = video;
                message.sendToTarget();
//                mHandler.sendEmptyMessage(0);
//                videoInfoList.add(video);
            }
        }
        return videoInfoList;
    }

    public interface IGetData {
        void addData(VideoInfo videoInfo);
    }

    static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VideoInfo video = (VideoInfo) msg.obj;
            //接口回调
            iGetData.addData(video);
        }
    };



    public void setGetData(IGetData iGetData) {
        this.iGetData = iGetData;
    }

    /**
     * 处理视频大小
     */
    private static String dealSize(float length) {
        long kb = 1024;
        long mb = 1024 * kb;
        long gb = 1024 * mb;
        if (length < kb) {
            return String.format("%d B", (int) length);
        } else if (length < mb) {
            return String.format("%.2f KB", length / kb);
        } else if (length < gb) {
            return String.format("%.2f MB", length / mb);
        } else {
            return String.format("%.2f GB", length / gb);
        }
    }
}
