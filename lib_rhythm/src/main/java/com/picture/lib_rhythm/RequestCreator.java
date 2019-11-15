package com.picture.lib_rhythm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.picture.lib_rhythm.bean.TagInfo;
import com.picture.lib_rhythm.cache.Cache;
import com.picture.lib_rhythm.cache.LocalCache;
import com.picture.lib_rhythm.cache.NetCache;
import com.picture.lib_rhythm.utils.BitmapUtils;
import com.picture.lib_rhythm.utils.Utils;
import com.picture.lib_rhythm.widgets.vague.VagueView;

import java.util.HashMap;
import java.util.Map;


/**
 * Time:2019/11/8
 * Author:xmz-dell
 * Description:
 */
public class RequestCreator {
    static volatile RequestCreator singleton = null;
    private Builder mBuilder;
    private static String TAG = "RequestCreator";
    public Map<String,NetCache.BitmapTask>taskMap;

    public static RequestCreator getInstance() {
        if (singleton == null) {
            synchronized (Rhythm.class) {
                if (singleton == null) {
                    singleton = new RequestCreator();
                }
            }
        }
        return singleton;
    }
    public RequestCreator(){
        taskMap=new HashMap<>();
    }
    /**
     * 创建任务
     * @param builder
     */
    public void createTask(Builder builder) {

        this.mBuilder = builder;
        if (mBuilder != null) {
            setOccupationMap();
            Enum type = Utils.getUrlType(mBuilder.url);
            Log.d(TAG, "tag-n debug 接收到的类型为:" + type.name());
            switch (type.name()) {
                case "HTTP":
                    mBuilder.netCache.transform(mBuilder.radius).error(getErrorDrawable())
                            .setOnLoadSuccessListener(new NetCache.OnLoadSuccessListener() {
                                @Override
                                public void loadBitmapSuccess(Bitmap bitmap) {
                                    setBitmapInto(bitmap);
                                }
                            })
                            .loadBitmap(mBuilder.imageView, mBuilder.url, mBuilder.context);
                    break;
                case "RESOURCES":
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap=getBitmap(Integer.parseInt(mBuilder.url));
                            saveMemory(mBuilder.url,bitmap);
                            setBitmapInto(bitmap);
                        }
                    });
                    break;
                case "LOCAL":
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap=getBitmap(mBuilder.url);
                            saveMemory(mBuilder.url,bitmap);
                            setBitmapInto(bitmap);
                        }
                    });
                    break;
            }
        }
    }
    /**
     *
     * @return
     */
    private Drawable getErrorDrawable() {
        if (mBuilder.errorResId != 0) {
            return mBuilder.context.getResources().getDrawable(mBuilder.errorResId);
        } else {
            return mBuilder.errorDrawable;
        }
    }
    /**
     * 将占位图设置到目标控件
     */
    private void setOccupationMap() {
        mBuilder.imageView.setImageDrawable(getPlaceholderDrawable());
    }

    /**
     *
     * @return
     */
    private Drawable getPlaceholderDrawable() {
        if (mBuilder.placeholderResId != 0) {
            return mBuilder.context.getResources().getDrawable(mBuilder.placeholderResId);
        } else {
            return mBuilder.placeholderDrawable;
        }
    }

    /**
     * 将bitmap保存至内存及本地
     * @param url
     * @param bitmap
     */
    private void saveMemory(String url, Bitmap bitmap){
        if(mBuilder.localCache.getBitmapFromLocal(url)==null){
            mBuilder.localCache.setBitmapToLocal(url,bitmap);
        }
        if(mBuilder.lruCache.get(url)==null){
            mBuilder.lruCache.set(url,bitmap);
        }
    }

    /**
     * 获取bitmap
     * @param url
     * @return
     */
    private Bitmap getBitmap(String url){
        if(mBuilder.lruCache.get(url)!=null){
            return mBuilder.lruCache.get(url);
        }
        if(mBuilder.localCache.getBitmapFromLocal(url)!=null){
            return mBuilder.localCache.getBitmapFromLocal(url);
        }
        return null;
    }

    /**
     * 根据资源ID 获取bitmap
     * @param resId
     * @return
     */
    private Bitmap getBitmap(int resId){
        if(mBuilder.lruCache.get(String.valueOf(resId))!=null){
            return mBuilder.lruCache.get(String.valueOf(resId));
        }
        if(mBuilder.localCache.getBitmapFromLocal(String.valueOf(resId))!=null){
            return mBuilder.localCache.getBitmapFromLocal(String.valueOf(resId));
        }
        return BitmapFactory.decodeResource(mBuilder.context.getResources(),resId);
    }
    /**
     * 将 bitmap设置给目标控件
     * @param bitmap
     */
    private void setBitmapInto(Bitmap bitmap){
        setBitmapInto(bitmap,true);
    }
    /**
     * 将 bitmap设置给目标控件
     * @param bitmap
     */
    private void setBitmapInto(Bitmap bitmap,boolean isTag){
        synchronized (this){
            Map<String,TagInfo>tagInfo=Rhythm.singleton.tagInfo;
            for (Map.Entry<String, TagInfo> m : tagInfo.entrySet()) {
                String  key= m.getKey();
                TagInfo mTagInfo=m.getValue();
                if(!mTagInfo.isTag()){
                    ImageView imageView=mTagInfo.getInto();
                    imageView.setImageBitmap(getBitmapFromStyle(bitmap));
                    mTagInfo.setTag(true);
                    Rhythm.singleton.tagInfo.put(mTagInfo.getUrl(),mTagInfo);
                    VagueView.with(mBuilder.context).into(imageView).targetResources(getBitmapFromStyle(bitmap)).buildVagueView();
                    break;
                }

            }

        }

    }

    /**
     *   返回不同风格的bitmap
     * @return
     */
    private Bitmap getBitmapFromStyle(Bitmap bitmap){
        if(bitmap!=null){
            if(mBuilder.radius!=0f){//如果设置了圆角
                bitmap= BitmapUtils.toRoundCorner(bitmap,mBuilder.radius,mBuilder.boarder);
            }
            if(mBuilder.style!=null){ //如果设置了风格
                switch (mBuilder.style.name()){
                    case "CIRCLE":
                        bitmap=BitmapUtils.createCircleImage(bitmap,mBuilder.boarder);
                        break;
                    default:
                        break;
                }
            }
        }
        return  bitmap;
    }
    /**
     * 判断目标控件是否跟url相匹配
     * @return
     */
    private boolean isViewTag(){
        return mBuilder.imageView.getTag().equals(mBuilder.url);
    }
    /**
     * 清除所有属性
     */
    public void cleanAllAttr(){
        if(mBuilder!=null){
            mBuilder.cleanAttr();
            mBuilder=null;
        }
    }


    public static class Builder {
        private Map<String,TagInfo>tagInfo=new HashMap<>();

        private Context context;
        private Cache lruCache;
        private LocalCache localCache;
        private NetCache netCache;
        private String url;
        private ImageView imageView;
        private int errorResId;
        private Drawable errorDrawable;
        private int placeholderResId;
        private Drawable placeholderDrawable;
        private float radius;
        private Enum style;
        private int boarder;
        public Builder addLruCache(Cache lruCache) {
            this.lruCache = lruCache;
            return this;
        }

        public Builder addLocalCache(LocalCache localCache) {
            this.localCache = localCache;
            return this;
        }

        public Builder addNetCache(NetCache netCache) {
            this.netCache = netCache;
            return this;
        }

        public Builder with(Context context) {
            this.context = context;
            return this;
        }
        /**
         * 通过资源ID设置错误视图
         * @param errorResId
         * @return
         */
        public Builder error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }
        /**
         * 通过errorDrawable设置错误视图
         * @param errorDrawable
         * @return
         */
        public Builder error(Drawable errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        /**
         * 通过资源ID设置占位图
         *
         * @param placeholderResId
         * @return
         */
        public Builder placeholder(int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        /**
         * 通过Drawable设置占位图
         *
         * @param placeholderDrawable
         * @return
         */
        public Builder placeholder(Drawable placeholderDrawable) {
            this.placeholderDrawable = placeholderDrawable;
            return this;
        }
        /**
         * 设置圆角
         * @param radius 弧度
         * @return
         */
        public Builder transform(float radius) {
            this.radius=radius;
            return this;
        }

        /**
         * 图片风格
         * @param style
         * @return
         */
        public  Builder style(Enum style) {
            this.style=style;
            return this;
        }
        /**
         * 设置边框  圆角or圆形
         * @param boarder
         * @return
         */
        public Builder boarder(int boarder){
            this.boarder=boarder;
            return this;
        }

        public Builder createTask(String url, ImageView image) {
            this.url = url;
            this.imageView = image;
            bindTag();
            return this;
        }
        /**
         * 为每个目标控件绑定tag
         */
        private void bindTag(){
            if(imageView!=null&&!TextUtils.isEmpty(url)){
                imageView.setTag(url);
                tagInfo.put(url,new TagInfo(url,imageView));
            }
        }
        /**
         * 清除本身所有属性
         */
        public void cleanAttr(){
            this.url=null;
            this.style=null;
            this.context=null;
            this.imageView=null;
            this.errorResId=0;
            this.errorDrawable=null;
            this.placeholderResId=0;
            this.placeholderDrawable=null;
            this.radius=0f;
            this.boarder=0;
        }
    }
}
