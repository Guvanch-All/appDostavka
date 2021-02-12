package com.example.ues_finalversion.ChatActivites;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ues_finalversion.R;

import java.util.List;

public class FirstMessageAdapter extends ArrayAdapter<FirstMessage> {
    private  List<FirstMessage> ListFirstMessages;
    private  Activity activity;
    public FirstMessageAdapter(Activity context, int resource,
                               List<FirstMessage> ListFirstMessages) {
        super(context, resource,ListFirstMessages);
        this.ListFirstMessages=ListFirstMessages;
        this.activity=context;
    }




    //переопределение метода convertView
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater=
                (LayoutInflater)activity.getSystemService(
                        Activity.LAYOUT_INFLATER_SERVICE);
        FirstMessage firstMessage=getItem(position);
        int layoutResource=0;
        int viewType=getItemViewType(position);

        if (viewType == 0) {
            layoutResource = R.layout.my_message_item;
        } else {
            layoutResource = R.layout.your_message_item;
        }
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(
                    layoutResource, parent, false
            );
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        boolean isText = firstMessage.getImageURL() == null;

        if (isText) {
            viewHolder.messageTextView.setVisibility(View.VISIBLE);
            viewHolder.photoImageView.setVisibility(View.GONE);
            viewHolder.messageTextView.setText(firstMessage.getText());

        } else {
            viewHolder.messageTextView.setVisibility(View.GONE);
            viewHolder.photoImageView.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.photoImageView.getContext()).
                    load(firstMessage.getImageURL()).into(viewHolder.photoImageView);

        }
        return convertView;
    }



    @Override
    public int getItemViewType(int position) {
        int flag;
        FirstMessage firstMessage = ListFirstMessages.get(position);
        if (firstMessage.isMine()) {
            flag = 1;
        } else {
            flag = 0;
        }
        return flag;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private class ViewHolder{
        private ImageView photoImageView;
        private TextView messageTextView;
        public ViewHolder(View view){
            photoImageView=view.findViewById(R.id.photoImageView1);
            messageTextView=view.findViewById(R.id.messageTextView1);
        }

    }
    //переопределение метода convertView
}
