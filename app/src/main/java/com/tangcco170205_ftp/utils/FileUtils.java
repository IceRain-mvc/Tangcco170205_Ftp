package com.tangcco170205_ftp.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.tangcco170205_ftp.bean.VideoInfo;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */

public class FileUtils {

    private static final int SCAN_OK = 1;
    private static final int SCAN_VIDEO_OK = 2;
    public static ArrayList<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();
    private static IGetDataVideo iGetDataVideo;
    static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    List<File> images = (List<File>) msg.obj;
                    iGetDataImage.addDataImage(images);
                    break;

                case SCAN_VIDEO_OK:
                    VideoInfo video = (VideoInfo) msg.obj;
                    //接口回调
                    iGetDataVideo.addData(video);
                    break;
            }

        }
    };
    private static IGetDataImage iGetDataImage;

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
                message.what = SCAN_VIDEO_OK;
                message.obj = video;
                message.sendToTarget();
//                mHandler.sendEmptyMessage(0);
//                videoInfoList.add(video);
            }
        }
        return videoInfoList;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        } else {
            dir.delete();
        }
        // 目录此时为空，可以删除
        return dir.delete();
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

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    public static void getImages(final Context context) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        new Thread(new Runnable() {

            private List<File> chileList = new ArrayList<File>();

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    File file = new File(path);
                    //根据父路径名将图片放入到mGruopMap中
                    chileList.add(file);
                }

                mCursor.close();

                //通知Handler扫描图片完成
                Message message = mHandler.obtainMessage();
                message.obj = chileList;
                message.what = SCAN_OK;
                mHandler.sendMessage(message);

            }
        }).start();

    }


    public void setDataVideo(IGetDataVideo iGetDataVideo) {
        this.iGetDataVideo = iGetDataVideo;
    }

    public void setDataImage(IGetDataImage iGetDataImage) {
        this.iGetDataImage = iGetDataImage;
    }

    public interface IGetDataVideo {
        void addData(VideoInfo videoInfo);
    }

    public interface IGetDataImage {
        void addDataImage(List<File> imagePaths);
    }
}
