package com.xxxy.yjw.handerandokhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ThreadActivity extends AppCompatActivity {

    private TextView tv_show_progress;
    private ProgressBar pb_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        tv_show_progress = findViewById(R.id.tv_show_progress);
        pb_progress = findViewById(R.id.pb_progress);
        tv_show_progress.setText("" + 1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pb_progress.setProgress(i);
                    pb_progress.setSecondaryProgress(i * 2);
                    //3.建立消息
                    Message message = new Message();
                    message.what = 1;
                    message.obj = i;
                    //4.发送消息
                    handler.sendMessage(message);

                }
            }
        }).start();


    }

    //1.建立Handler
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //2.建立处理机制
            switch (msg.what) {
                case 1:
                    tv_show_progress.setText(msg.obj.toString());
                    break;
            }
        }
    };
}