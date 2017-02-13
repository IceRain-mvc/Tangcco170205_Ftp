package com.tangcco170205_ftp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tangcco170205_ftp.service.CallUploadService;
import com.tangcco170205_ftp.utils.AudioRecoderUtils;
import com.tangcco170205_ftp.utils.TimeUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mRecordTime;
    private AudioRecoderUtils mAudioRecoderUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecordTime = (TextView) findViewById(R.id.textView2);
        mAudioRecoderUtils = new AudioRecoderUtils();

        //录音回调
        mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            long timenow;

            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
                mRecordTime.setText(TimeUtils.long2String(time));
                timenow = time;
            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath) {
                Toast.makeText(MainActivity.this, "录音保存在：" + filePath, Toast.LENGTH_SHORT).show();
                mRecordTime.setText(TimeUtils.long2String(timenow));

            }
        });
    }

    public void start(View view) {
        mAudioRecoderUtils.startRecord();
        Intent intent = new Intent(this, CallUploadService.class);
        startService(intent);

    }

    public void stop(View view) {
        mAudioRecoderUtils.stopRecord();
    }

}
