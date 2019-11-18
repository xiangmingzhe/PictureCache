package com.example.dell.picturecache.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dell.picturecache.R;
import com.picture.lib_rhythm.Rhythm;

import java.util.List;

/**
 * Time:2019/11/12
 * Author:xmz-dell
 * Description:
 */
public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;
    public TestAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }
    public void setContext(Context context){
        this.mContext=context;
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView=helper.getView(R.id.image);
        if(helper.getPosition()!=1){
            Rhythm.with(mContext)
                    .transform(10.0f).boarder(3)
                    .load(item)
                    .crossFade()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.robot)
                    .into(imageView);
        }else{
            Rhythm.with(mContext)
                    .transform(10.0f).boarder(3)
                    .load(item)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.robot)
                    .into(imageView);
        }

    }
}
