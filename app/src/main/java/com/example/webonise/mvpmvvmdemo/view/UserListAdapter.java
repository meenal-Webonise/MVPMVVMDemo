package com.example.webonise.mvpmvvmdemo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.webonise.mvpmvvmdemo.R;
import com.example.webonise.mvpmvvmdemo.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webonise on 22/9/16.
 */
public class UserListAdapter extends BaseAdapter {

    private Context mContext;
    private List<UserModel> userModelList;
    private LayoutInflater mInflater;

    public UserListAdapter(Context mContext, List<UserModel> userModelList) {
        this.mContext = mContext;
        this.userModelList = new ArrayList<>();
        this.userModelList = userModelList;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return userModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return userModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserModel userModel = (UserModel) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.user_list_row_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);
            viewHolder.textViewEmail = (TextView) convertView.findViewById(R.id.textViewEmail);
            viewHolder.textViewBody = (TextView) convertView.findViewById(R.id.textViewBody);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.textViewName.setText(userModel.getName());
        viewHolder.textViewEmail.setText(userModel.getEmail());
        viewHolder.textViewBody.setText(userModel.getBody());


        return convertView;
    }

    private class ViewHolder {
        public TextView textViewName, textViewEmail, textViewBody;
    }
}
