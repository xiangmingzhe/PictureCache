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
     * 加载的网页
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
        setOccupationMap();
        netCache.error(getErrorDrawable());
        netCache.loadBitmap(imageView, url);
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
     * 将占位图设置到目标控件
     */
    private void setOccupationMap() {
        imageView.setImageDrawable(getPlaceholderDrawable());
    }

    /**
     *
     * @return
     */
    private Drawable getPlaceholderDrawable() {
        if (placeholderResId != 0) {
            return context.getResources().getDrawable(placeholderResId);
        } else {
            return placeholderDrawable;
        }
    }

    /**
     *
     * @return
     */
    private Drawable getErrorDrawable() {
        if (errorResId != 0) {
            return context.getResources().getDrawable(errorResId);
        } else {
            return errorDrawable;
        }
    }

    public static class Builder {
        public Context context;
        public Cache lruCache;
        public LocalCache localCache;
        public NetCache netCache;
        public String url;
        public String uniqueName;
        public ImageView imageView;

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
