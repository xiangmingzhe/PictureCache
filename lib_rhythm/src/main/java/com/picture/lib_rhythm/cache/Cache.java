package com.picture.lib_rhythm.cache;

import android.graphics.Bitmap;

/**
 * Time:2019/11/7
 * Author:xmz-dell
 * Description:
 */
public interface Cache {
    Bitmap get(String key);

    void set(String key, Bitmap bitmap);

    int size();

    int maxSize();

    void clear();

    void clearKeyUri(String keyPrefix);

    Cache NONE = new Cache() {
        @Override
        public Bitmap get(String key) {
            return null;
        }

        @Override
        public void set(String key, Bitmap bitmap) {
            // Ignore.
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public int maxSize() {
            return 0;
        }

        @Override
        public void clear() {
        }

        @Override
        public void clearKeyUri(String keyPrefix) {
        }
    };
}
