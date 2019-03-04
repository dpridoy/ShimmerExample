package com.dma_bd.shimmerexample;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ModelClass> list;

    public MyAdapter(Context context, ArrayList<ModelClass> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View v = LayoutInflater.from(context).inflate(R.layout.single_received_view, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_placeholder_item, parent, false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.ViewHolder viewHolder, int i) {
        final ModelClass receivedItemClass=list.get(i);

        viewHolder.id.setText(receivedItemClass.getId());
        viewHolder.title.setText(receivedItemClass.getTitle());
        viewHolder.url.setText(receivedItemClass.getUrl());

        Glide.with(context).load(receivedItemClass.getThumbnail()).into(viewHolder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id, title,url;
        public ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            title=itemView.findViewById(R.id.title);
            url=itemView.findViewById(R.id.url);
            thumbnail=itemView.findViewById(R.id.thumbnail);
        }
    }
}