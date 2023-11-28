package com.xxxy.yjw.handerandokhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class HandlerActivity extends AppCompatActivity {



    TextView tv_new ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        tv_new = findViewById(R.id.tv_new);
        //主线程更新自己的ui
//        tv_new.setText("jjj");

        //子线程更新主线程ui
        //创建一个子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //子线程处理  耗时操作
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //创建出Message对象
                //给message对象设置what 名字
                //给message对象设置值
                //通过handler发送message
                Message message = new Message();
                message.what = 1;
                message.obj = "kkk";
                handler.sendMessage(message);


            }
        }).start();


    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            //接受message
            switch (msg.what){
                case 1:
                    String value = (String) msg.obj;
                    tv_new.setText(value);
            }

        }
    };



}