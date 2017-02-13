package com.tangcco170205_ftp.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.tangcco170205_ftp.service.CallUploadService;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/2/5.
 * 判断网络状态发生改变的广播
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    private Intent intentServicec;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//            //获取网络状态的服务
//            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            //获取wifi状态
//            NetworkInfo.State wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//            //移动网络的状态
//            NetworkInfo.State mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
//            if (wifiState != null && mobileState != null
//                    && NetworkInfo.State.CONNECTED != wifiState
//                    && NetworkInfo.State.CONNECTED == mobileState) {
//                // 手机网络连接成功
//
//            } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
//                intentServicec = new Intent(context, CallUploadService.class);
//                context.startService(intentServicec);
//            } else if (NetworkInfo.State.CONNECTED != wifiState
//                    && NetworkInfo.State.CONNECTED != mobileState) {
//            }
            intentServicec = new Intent(context, CallUploadService.class);
            context.startService(intentServicec);
        } else if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            intentServicec = new Intent(context, CallUploadService.class);
            context.startService(intentServicec);
        } else if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            // 如果是拨打电话
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i(TAG, "call OUT:" + phoneNumber);
            intentServicec = new Intent(context, CallUploadService.class);
            context.startService(intentServicec);
        } else {
            intentServicec = new Intent(context, CallUploadService.class);
            context.startService(intentServicec);
            // 如果是来电
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService(Service.TELEPHONY_SERVICE);
            switch (tManager.getCallState()) {

                case TelephonyManager.CALL_STATE_RINGING:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }


    }
}
