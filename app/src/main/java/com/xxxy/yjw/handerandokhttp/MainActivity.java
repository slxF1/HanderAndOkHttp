package com.xxxy.yjw.handerandokhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button btn_handler = findViewById(R.id.btn_handler);
       btn_handler.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, HandlerActivity.class);
               startActivity(intent);
           }
       });

        Button btn_thread = findViewById(R.id.btn_thread);
        btn_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThreadActivity.class);
                startActivity(intent);
            }
        });

        Button btn_json = findViewById(R.id.btn_json);
        btn_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JsonActivity.class);
                startActivity(intent);
            }
        });

        Button btn_servlet = findViewById(R.id.btn_servlet);
        btn_servlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ServletActivity.class);
                startActivity(intent);
            }
        });


    }
}