package com.khdev.git_work_test;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HelperAdapter extends RecyclerView.Adapter< HelperAdapter.MyViewClass > {
    ArrayList< String > login;
    ArrayList< String > id;
    ArrayList< String > avatar;
    Context context;

    public HelperAdapter(ArrayList< String > login, ArrayList< String > id, ArrayList< String > avatar, Context context) {
        this.login = login;
        this.id = id;
        this.avatar = avatar;
        this.context = context;
        Log.d("TEST_API", ("" + login + avatar));
    }
    @NonNull
    @Override
    public MyViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new MyViewClass(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewClass holder, @SuppressLint("RecyclerView") int position) {

        holder.login.setText(login.get(position));
        holder.id.setText(id.get(position));
        Picasso.get().load(avatar.get(position)).into(holder.avatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(context, User_Info.class);
                mainIntent.putExtra("login", login.get(position));
                context.startActivity(mainIntent);
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return login.size();
    }

    public static class MyViewClass extends RecyclerView.ViewHolder{
        TextView login;
        TextView id;
        ImageView avatar;
        public MyViewClass(@NonNull View itemView) {
            super(itemView);
            login = itemView.findViewById(R.id.login);
            id = itemView.findViewById(R.id.id);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }

}