package com.xxxy.yjw.handerandokhttp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xxxy.yjw.handerandokhttp.R;
import com.xxxy.yjw.handerandokhttp.holder.UserHolder;
import com.xxxy.yjw.handerandokhttp.model.User;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<User> list;
    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UserHolder userHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.user_item,null);
            userHolder = new UserHolder();
            userHolder.tv_id = view.findViewById(R.id.tv_id);
            userHolder.tv_username = view.findViewById(R.id.tv_username);
            userHolder.tv_password = view.findViewById(R.id.tv_password);
            userHolder.tv_phone = view.findViewById(R.id.tv_phone);
            view.setTag(userHolder);
        }else {
            userHolder = (UserHolder) view.getTag();
        }
        //绑定数据
        userHolder.tv_id.setText(String.valueOf(list.get(i).getId()));
        userHolder.tv_username.setText(String.valueOf(list.get(i).getUsername()));
        userHolder.tv_password.setText(String.valueOf(list.get(i).getPassword()));
        userHolder.tv_phone.setText(list.get(i).getPhone());


        return view;
    }
}
