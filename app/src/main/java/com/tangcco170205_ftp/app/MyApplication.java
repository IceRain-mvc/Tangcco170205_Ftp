package com.tangcco170205_ftp.app;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by Administrator on 2017/2/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }
    private void initImageLoader() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tcmp038";
        File file = new File(path);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(file))//缓存路径
                .threadPoolSize(5)//1-5
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//MD5加密
                .diskCacheSize(10 * 1024 * 1024)//设置缓存目录大小
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
