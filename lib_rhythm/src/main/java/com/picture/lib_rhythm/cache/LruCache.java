package com.picture.lib_rhythm.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.picture.lib_rhythm.utils.Utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Time:2019/11/7
 * Author:xmz-dell
 * Description:
 */
public class LruCache implements Cache{
    private int size;
    private int maxSize;
    private int cacheCount; //缓存计数
    private int evictionCount;//清除计数
    private final LinkedHashMap<String, Bitmap> map;

    public LruCache(Context context){
        this(Utils.calculateMemoryCacheSize(context));
    }
    public LruCache(int maxSize){
        if(maxSize<=0){
            throw new IllegalArgumentException("Max size Cannot be less than or equal to 0");
        }
        this.maxSize=maxSize;
        this.map = new LinkedHashMap<String, Bitmap>(0, 0.75f, true);
    }

    @Override
    public Bitmap get(String key) {
        if(TextUtils.isEmpty(key)){
            throw new NullPointerException("key Can not be empty");
        }
        synchronized (this){
            Bitmap bitmap;
            bitmap=map.get(key);
            if(bitmap!=null){
                cacheCount++;
                return bitmap;
            }
        }
        return null;
    }

    @Override
    public void set(String key, Bitmap bitmap) {
        if(TextUtils.isEmpty(key)||bitmap==null){
            throw new NullPointerException("key Can not be empty||bitmap Can not be empty");
        }
        Bitmap readyMoveBitmap;//等待移动的bitmap
        size+= Utils.getBitmapBytes(bitmap);//得到当前bitmap大小并叠加
        readyMoveBitmap=map.put(key,bitmap);
        if(readyMoveBitmap!=null){
            size-=Utils.getBitmapBytes(readyMoveBitmap);
        }

        reduceMemoryPressure();
    }

    /**
     * //如果当前size超过maxsize 就从前往后移除bitmap 来减少内存
     */
    public void reduceMemoryPressure(){
        while(true){
            String key;
            Bitmap bitmap;
            synchronized (this){
                if(size<0||map.isEmpty()){
                    throw new NullPointerException("size Cannot be less than 0 ||map Can not be empty");
                }
                if(size<=maxSize||map.isEmpty()){//如果没有超过阈值就结束整个死循环;
                    break;
                }
                Map.Entry<String, Bitmap> decompose = map.entrySet().iterator().next();
                key=decompose.getKey();
                bitmap=decompose.getValue();
                map.remove(key);
                size-=Utils.getBitmapBytes(bitmap);
                evictionCount++;
            }
        }
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public int maxSize() {
        return maxSize;
    }

    @Override
    public void clear() {
        reduceMemoryPressure();
    }

    @Override
    public void clearKeyUri(String keyPrefix) {

    }
}
