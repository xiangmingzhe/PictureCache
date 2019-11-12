package com.example.dell.picturecache.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.picturecache.MainActivity;
import com.example.dell.picturecache.R;
import com.picture.lib_rhythm.Rhythm;

import java.util.List;

/**
 * Time:2019/11/12
 * Author:xmz-dell
 * Description:
 */
public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    private Context context;
    private List<String> data;

    public RecyclerviewAdapter(Context context,List<String> data){
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcy,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Rhythm.with(context)
                .transform(10.0f).boarder(3)
                .load(data.get(position))
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("这里是点击每一行item的响应事件",""+position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.image);
        }
    }
}

