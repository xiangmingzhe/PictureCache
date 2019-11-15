package com.picture.lib_rhythm.widgets.vague;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.picture.lib_rhythm.Rhythm;
import com.picture.lib_rhythm.utils.BitmapUtils;

import java.util.Arrays;

/**
 * Time:2019/11/14
 * Author:xmz-dell
 * Description:
 */
public class VagueView {
    volatile static VagueView singleton=null;
    private Bitmap mBitmap;
    private ImageView into;
    private Context context;
    public static VagueView with(Context context) {
        if (singleton == null) {
            synchronized (VagueView.class) {
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }
        return singleton;
    }
    public VagueView(Context context){
        this.context=context;
    }

    /***
     * 目标控件
     * @param into
     * @return
     */
    public VagueView into(ImageView into){
        this.into=into;
        return this;
    }

    /**
     * 目标资源
     * @param mBitmap
     * @return
     */
    public VagueView targetResources(Bitmap mBitmap){
        this.mBitmap=mBitmap;
        return this;
    }
    private byte[]bs;
    private Bitmap buildBitmap;
    /**
     * 构建模糊视图
     */
    public void buildVagueView(){
        if(this.mBitmap==null||this.into==null){
            throw new NullPointerException("targetResources  cannot be empty|| into cannot be empty");
        }
        byte[]bytes= BitmapUtils.getBitmapByte(mBitmap);
        bs = Arrays.copyOfRange(bytes, 1, 40);
        handler.postDelayed(runnable,100);
    }
    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
//            if(buildBitmap!=null){
//                buildBitmap.recycle();
//                buildBitmap=null;
//            }
            buildBitmap=BitmapUtils.getBitmapFromByte(bs);
            if(buildBitmap!=null){
                into.setImageBitmap(buildBitmap);
            }

        }
    };
    public static class Builder{
        private Context context;
        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context cannot be empty");
            }
            this.context = context.getApplicationContext();
        }
        public VagueView build(){
            return new VagueView(context);
        }
    }
}
