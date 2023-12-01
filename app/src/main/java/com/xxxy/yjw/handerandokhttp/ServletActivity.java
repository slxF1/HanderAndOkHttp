package com.xxxy.yjw.handerandokhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxxy.yjw.handerandokhttp.adapter.UserAdapter;
import com.xxxy.yjw.handerandokhttp.model.JsonRootBean;
import com.xxxy.yjw.handerandokhttp.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServletActivity extends AppCompatActivity {

    ListView lv_userlist;
    List<User> userList = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //接受所有的User数据 以List<USer>的方式
            if (msg.what == 1) {
                String rep = (String) msg.obj;//获取json串
                Gson gson = new Gson();
                userList = gson.fromJson(rep, new TypeToken<List<User>>() {
                }.getType());
                //向listview提供数据 实现adapter
                UserAdapter userAdapter = new UserAdapter(getApplicationContext(), userList);
                lv_userlist.setAdapter(userAdapter);
            }
            if (msg.what == 3){
                String rep = (String) msg.obj;
                Log.d("rep: ",rep);
                if (Integer.parseInt(rep)>0){
                    //修改成功
                    Toast.makeText(ServletActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                    request_userlist();
                }else {
                    Toast.makeText(ServletActivity.this,"修改失败",Toast.LENGTH_LONG).show();

                }
            }

            //删除
            if (msg.what == 5){
                String rep = (String) msg.obj;
                if (Integer.parseInt(rep)>0){
                    //成功
                    Toast.makeText(ServletActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                    request_userlist();
                }else {
                    Toast.makeText(ServletActivity.this,"删除失败",Toast.LENGTH_LONG).show();

                }
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servlet);

        lv_userlist = findViewById(R.id.lv_userlist);
        request_userlist();
        //对listview中的数据进行点击事件的操作
        lv_userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //执行修改操作
                //自定义一个alertdialog 自定义给xml
                AlertDialog.Builder builder = new AlertDialog.Builder(ServletActivity.this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("用户修改过");
                //自定义一个满足需求的view  需要从xml中生成
                View inflate = LayoutInflater.from(ServletActivity.this).inflate(R.layout.show_user, null);
                //修改操作需要我们向alert中放入数据
                User user = userList.get(i);
                EditText et_showid = inflate.findViewById(R.id.et_showid);
                EditText et_showusername = inflate.findViewById(R.id.et_showusername);
                EditText et_showpassword = inflate.findViewById(R.id.et_showpassword);
                EditText et_showphone = inflate.findViewById(R.id.et_showphone);

                et_showid.setText(String.valueOf(user.getId()));
                et_showusername.setText(user.getUsername());
                et_showpassword.setText(user.getPassword());
                et_showphone.setText(user.getPhone());
                //id不允许修改
                et_showid.setEnabled(false);//设置不可编辑
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //请求要写到相应的数据
                        User user = new User();
                        user.setId(Integer.parseInt(et_showid.getText().toString()));
                        user.setUsername(et_showusername.getText().toString());
                        user.setPassword(et_showpassword.getText().toString());
                        user.setPhone(et_showphone.getText().toString());
                        //当准备修改的数据，发送请求
                        request_updateUser(user);
                    }
                });
                builder.setNegativeButton("取消", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.setView(inflate);
                alertDialog.show();

            }
        });
        lv_userlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                 //进行删除操作
                AlertDialog.Builder builder = new AlertDialog.Builder(ServletActivity.this);
                builder.setIcon(R.drawable.ic_launcher_background);
                builder.setTitle("删除");
                builder.setMessage("确定删除本人数据吗");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {
                        User user = userList.get(i);
                        request_deleteUserById(user.getId());

                    }
                });
                builder.setNegativeButton("取消",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;

            }
        });

    }

    private void request_deleteUserById(int id) {
        //通过okhttp访问servlet
        String strURL = "http://10.138.2.195:8088/infotest/DeleteUserServlet";
        //携带数据的请求
        RequestBody requestBody = new FormBody.Builder()
                .add("id", String.valueOf(id))
                .build();
        Request request = new Request.Builder()
                .url(strURL)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {//接收响应 子线程
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 6;
                message.obj = e.getMessage();
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String rep = response.body().string();
                Message message = new Message();
                message.what = 5;
                message.obj = rep;
                handler.sendMessage(message);

            }
        });

    }

    private void request_updateUser(User user) {
        //通过okhttp访问servlet
        String strURL = "http://10.138.2.195:8088/infotest/UpdateUserServlet";
        //携带数据的请求
        RequestBody requestBody = new FormBody.Builder()
                .add("id", String.valueOf(user.getId()))
                .add("username", user.getUsername())
                .add("password", user.getPassword())
                .add("phone", user.getPhone())
                .build();
        Request request = new Request.Builder()
                .url(strURL)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {//接收响应 子线程
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 4;
                message.obj = e.getMessage();
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String rep = response.body().string();
                Message message = new Message();
                message.what = 3;
                message.obj = rep;
                handler.sendMessage(message);

            }
        });

    }

    private void request_userlist() {
        //通过okhttp访问servlet
        String strURL = "http://10.138.2.195:8088/infotest/FindAllServlet";
        Request request = new Request.Builder().url(strURL).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        //接收相应 进入子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 2;
                message.obj = e.getMessage();
                //通过handler向主线程发送json数据
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String rep = response.body().string();
                Message message = new Message();
                message.what = 1;
                message.obj = rep;
                handler.sendMessage(message);


            }
        });

    }
}