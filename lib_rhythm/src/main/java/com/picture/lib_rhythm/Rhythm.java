package com.picture.lib_rhythm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.picture.lib_rhythm.animate.AnimateManage;
import com.picture.lib_rhythm.bean.TagInfo;
import com.picture.lib_rhythm.cache.Cache;
import com.picture.lib_rhythm.cache.Callback;
import com.picture.lib_rhythm.cache.LocalCache;
import com.picture.lib_rhythm.cache.LruCache;
import com.picture.lib_rhythm.cache.NetCache;
import com.picture.lib_rhythm.constant.AnimateType;
import com.picture.lib_rhythm.transformation.BlurTransformation;
import com.picture.lib_rhythm.utils.Utils;
import com.picture.lib_rhythm.widgets.gif.GifImageView;

import java.util.HashMap;
import java.util.Map;

import static com.picture.lib_rhythm.constant.AnimateType.DONT_ANIMATE;

/**
 * Time:2019/11/7
 * Author:xmz-dell
 * Description:
 */
public class Rhythm {
    static volatile Rhythm singleton = null;
    public Context context;
    public Cache lruCache;
    public LocalCache localCache;
    public NetCache netCache;
    public String url;
    public String uniqueName;
    public ImageView imageView;
    private int placeholderResId;
    private int errorResId;
    private Drawable placeholderDrawable;
    private Drawable errorDrawable;
    private boolean gifEnable;
    private float radius=0f;
    private Enum style;
    private int boarder=0;
    private RequestCreator.Builder builder;
    public Map<String,TagInfo>tagInfo;
    private AnimateType animateType;
    private int animationID;
    private BlurTransformation blurTransformation;
    public Rhythm(Context context, Cache lruCache, LocalCache localCache, NetCache netCache) {
        this.context = context;
        this.lruCache = lruCache;
        this.localCache = localCache;
        this.netCache = netCache;
    }
    public Rhythm(Context context, Cache lruCache, LocalCache localCache, NetCache netCache,Map<String,TagInfo>tagInfo) {
        this.context = context;
        this.lruCache = lruCache;
        this.localCache = localCache;
        this.netCache = netCache;
        this.tagInfo=tagInfo;
    }
    public static Rhythm with(Context context) {

        if (singleton == null) {
            synchronized (Rhythm.class) {
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }
        return singleton;
    }


    /**
     * 加载的图片地址
     *
     * @param path
     * @return
     */
    public Rhythm load(String path) {
        if (TextUtils.isEmpty(path)) {
            throw new NullPointerException("path Can not be empty");
        }
        this.url = path;

        return this;
    }
    /**
     * 加载的资源文件
     *
     * @param resId
     * @return
     */
    public Rhythm load(int resId) {
        if (resId==0) {
            throw new NullPointerException("resId Can not be 0");
        }
        this.url = String.valueOf(resId);

        return this;
    }
    /**
     * 目标控件
     *
     * @param target
     */
    public Rhythm into(ImageView target) {
        this.imageView = target;
        into(target, null);
        return this;
    }

    public void into(ImageView target, Callback callback) {
        Utils.checkMain();
        if (target == null) {
            throw new IllegalArgumentException("Target Can not be empty.");
        }
        if(gifEnable){
            GifImageView imageView=(GifImageView)target;
            imageView.isAutoPlay(gifEnable);
        }
        if(builder!=null){
            RequestCreator.getInstance().cleanAllAttr();
            builder=null;
        }
        bindTag();
        builder=new RequestCreator.Builder();
        builder.with(context)
                .addLocalCache(localCache)
                .addLruCache(lruCache)
                .addNetCache(netCache)
                .error(errorResId)
                .error(errorDrawable)
                .placeholder(placeholderResId)
                .placeholder(placeholderResId)
                .transform(radius)
                .style(style)
                .boarder(boarder)
                .bindAnimteType(animateType)
                .bindAnimteId(animationID)
                .createTask(url,target);
        RequestCreator.getInstance().createTask(builder);
        cleanAttr();

    }

    /**
     * 为每个目标控件绑定tag
     */
    private void bindTag(){
        if(imageView!=null&&!TextUtils.isEmpty(url)){
            imageView.setTag(url);
            tagInfo.put(url,new TagInfo(url,imageView,animateType,animationID,blurTransformation));
        }
    }
    /**
     * 通过资源ID设置占位图
     *
     * @param placeholderResId
     * @return
     */
    public Rhythm placeholder(int placeholderResId) {
        if (placeholderResId == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        }
        this.placeholderResId = placeholderResId;
        return this;
    }

    /**
     * 通过Drawable设置占位图
     *
     * @param placeholderDrawable
     * @return
     */
    public Rhythm placeholder(Drawable placeholderDrawable) {
        if (placeholderDrawable == null) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        }
        this.placeholderDrawable = placeholderDrawable;
        return this;
    }

    /**
     * 通过资源ID设置错误视图
     * @param errorResId
     * @return
     */
    public Rhythm error(int errorResId) {
        if (errorResId == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        }
        if (errorDrawable != null) {
            throw new IllegalStateException("Error image already set.");
        }
        this.errorResId = errorResId;
        return this;
    }
    /**
     * 通过errorDrawable设置错误视图
     * @param errorDrawable
     * @return
     */
    public Rhythm error(Drawable errorDrawable) {
        if (errorDrawable == null) {
            throw new IllegalArgumentException("Error image may not be null.");
        }
        if (errorResId != 0) {
            throw new IllegalStateException("Error image already set.");
        }
        this.errorDrawable = errorDrawable;
        return this;
    }

    /**
     * 是否开启gif
     * @param enable
     * @return
     */
    public Rhythm openGif(boolean enable){
        this.gifEnable=enable;
        return this;
    }

    /**
     * 设置圆角
     * @param radius 弧度
     * @return
     */
    public Rhythm transform(float radius) {
        this.radius=radius;
        return this;
    }

    /**
     * 图片风格  圆形/椭圆
     * @param style
     * @return
     */
    public  Rhythm style(Enum style) {
        this.style=style;
        return this;
    }

    /**
     * 设置边框  圆角or圆形
     * @param boarder
     * @return
     */
    public Rhythm boarder(int boarder){
        this.boarder=boarder;
        return this;
    }

    /**
     * 设置淡入淡出的效果
     * @return
     */
    public Rhythm crossFade(){
        animateType=AnimateType.CROSS_FADE;
        return this;
    }
    /**
     * 设置自定义动画
     */
    public Rhythm Animation(int animation){
        animateType=AnimateType.ANIMATE;
        animationID=animation;
        return this;
    }

    /**
     * 无动画
     * @return
     */
    public Rhythm dontAnimation(){
        animateType= DONT_ANIMATE;
        return this;
    }
    /**
     * 设置高斯模糊效果
     * @return
     */
    public Rhythm bitmapTransform(BlurTransformation blurTransformation){
        this.blurTransformation=blurTransformation;
        return this;
    }

    /**
     * 清除所有任务
     */
    public void cancleAllTask(){
        if(netCache!=null){
            netCache.cancleAllTask();
        }
    }
    /**
     * 清除所有任务
     */
    public void cancleTask(String tag){
        if(netCache!=null){
            netCache.cancleTask(tag);
        }
    }
    /**
     * 清除本身所有属性
     */
    public Rhythm cleanAttr(){
        this.url=null;
        this.style=null;
        this.imageView=null;
        this.errorResId=0;
        this.errorDrawable=null;
        this.placeholderResId=0;
        this.placeholderDrawable=null;
        this.radius=0f;
        this.boarder=0;
        this.animateType=DONT_ANIMATE;
        this.animationID=0;
        this.blurTransformation=null;
        return this;
    }
    public static class Builder {
        public Context context;
        public Cache lruCache;
        public LocalCache localCache;
        public NetCache netCache;
        public String uniqueName;
        public Map<String,TagInfo>tagInfo;
        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context cannot be empty");
            }
            this.context = context.getApplicationContext();
        }


        public Rhythm build() {
            if (TextUtils.isEmpty(uniqueName)) {
                uniqueName = "download_test";
            }
            Context context = this.context;
            if (lruCache == null) {
                lruCache = new LruCache(context);
            }
            if (localCache == null) {
                localCache = new LocalCache(context, uniqueName);
            }
            if (netCache == null) {
                netCache = new NetCache(lruCache, localCache);
            }
            if(tagInfo==null){
                tagInfo=new HashMap<>();
            }
            return new Rhythm(context, lruCache, localCache, netCache,tagInfo);
        }
    }
}
