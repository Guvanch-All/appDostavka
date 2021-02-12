package com.example.ues_finalversion.ChatActivites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ues_finalversion.R;

import java.util.ArrayList;

public class UserAdapter
        extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<User>users;
    private OnUserClickListener listener;

    public  interface  OnUserClickListener{
        Void onUserClick(int positon);
    }

    public void  setOnUserClickListener(OnUserClickListener listener){
       this.listener=listener;
    }

    public UserAdapter(ArrayList<User>users){
        this.users=users;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,parent,false);
        UserViewHolder viewHolder=new UserViewHolder(view,listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser=users.get(position);
        holder.AvatarImage.setImageResource(currentUser.getAvatarMockUpResource());
        holder.userNameTextView.setText(currentUser.getName());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        public ImageView AvatarImage;
        public TextView userNameTextView;

        public UserViewHolder(@NonNull View itemView, OnUserClickListener listener) {
            super(itemView);
             AvatarImage=itemView.findViewById(R.id.AvatarImage);
             userNameTextView=itemView.findViewById(R.id.userNameTextView);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(listener!=null){
                         int position=getAdapterPosition();
                         if (position!=RecyclerView.NO_POSITION){

                             listener.onUserClick(position);
                         }
                     }
                 }
             });

        }
    }
}
