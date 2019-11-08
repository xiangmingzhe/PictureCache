package com.picture.lib_rhythm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.picture.lib_rhythm.cache.Cache;
import com.picture.lib_rhythm.cache.LocalCache;
import com.picture.lib_rhythm.cache.NetCache;
import com.picture.lib_rhythm.utils.Utils;

import static com.picture.lib_rhythm.TypeEnum.HTTP;

/**
 * Time:2019/11/8
 * Author:xmz-dell
 * Description:
 */
public class RequestCreator {
    static volatile RequestCreator singleton = null;
    private Builder mBuilder;
    private static String TAG = "RequestCreator";

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
                    mBuilder.netCache.error(getErrorDrawable()).loadBitmap(mBuilder.imageView, mBuilder.url, mBuilder.context);
                    break;
                case "RESOURCES":
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mBuilder.imageView.setImageResource(Integer.parseInt(mBuilder.url));
                        }
                    });
                    break;
                case "LOCAL":
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mBuilder.imageView.setImageBitmap(mBuilder.localCache.getBitmapFromLocal(mBuilder.url));
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
    public static class Builder {
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


        public Builder createTask(String url, ImageView image) {
            this.url = url;
            this.imageView = image;
            return this;
        }
    }
}
