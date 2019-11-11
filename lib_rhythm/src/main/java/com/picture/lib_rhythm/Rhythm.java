package com.picture.lib_rhythm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.telecom.Call;
import android.text.TextUtils;
import android.widget.ImageView;

import com.picture.lib_rhythm.cache.Cache;
import com.picture.lib_rhythm.cache.Callback;
import com.picture.lib_rhythm.cache.LocalCache;
import com.picture.lib_rhythm.cache.LruCache;
import com.picture.lib_rhythm.cache.NetCache;
import com.picture.lib_rhythm.utils.Utils;
import com.picture.lib_rhythm.widgets.gif.GifImageView;

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
    public Rhythm(Context context, Cache lruCache, LocalCache localCache, NetCache netCache) {
        this.context = context;
        this.lruCache = lruCache;
        this.localCache = localCache;
        this.netCache = netCache;
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

        RequestCreator.Builder builder=new RequestCreator.Builder();
        builder.with(context)
                .addLocalCache(localCache)
                .addLruCache(lruCache)
                .addNetCache(netCache)
                .error(errorResId).
                error(errorDrawable).
                placeholder(placeholderResId).
                placeholder(placeholderResId).
                transform(radius).
                style(style)
                .createTask(url,target);
        RequestCreator.getInstance().createTask(builder);
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

    public static class Builder {
        public Context context;
        public Cache lruCache;
        public LocalCache localCache;
        public NetCache netCache;
        public String uniqueName;

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
            return new Rhythm(context, lruCache, localCache, netCache);
        }
    }
}
