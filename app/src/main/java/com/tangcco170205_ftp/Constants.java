package com.tangcco170205_ftp;

import android.content.Context;
import android.os.Environment;

import com.tangcco170205_ftp.utils.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/2/7.
 */

public class Constants {

    public static String password = "E6D23658CD1c23";
    public static String userName = "tbhcuili01";
    public static String hostName = "103.239.75.176";
    private static boolean sdCardExist;
    private static String dirName;


    public static String sdPath(Context context) {//TODO 设置存储目录
        sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            dirName = FileUtils.getSDPath(false) + "/myfile/";
            File f = new File(dirName);
            if (!f.exists()) {
                f.mkdirs();
            }
        } else {
            dirName = context.getApplicationContext().getFilesDir().getPath()
                    + "/myfile/";
            File f = new File(dirName);
            if (!f.exists()) {
                f.mkdirs();
            }
        }
        return dirName;
    }
}
