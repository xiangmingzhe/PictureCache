package com.picture.lib_rhythm.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;

import java.io.File;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;


/**
 * Time:2019/11/7
 * Author:xmz-dell
 * Description:
 */
public class Utils {
    /**
     * 计算bitmap的大小
     * @param bitmap
     * @return
     */
   public static int getBitmapBytes(Bitmap bitmap){
        int result;
        result = bitmap.getByteCount();
        if(result<0){
            throw new IllegalStateException("bitmap size < 0");
        }
        return result;
    }
    public static long getBitmapKB(Bitmap bitmap){
        int result;
        result = bitmap.getByteCount();
        if(result<0){
            throw new IllegalStateException("bitmap size < 0");
        }
        return result*4;
    }
    @SuppressWarnings("unchecked")
    static <T> T getService(Context context, String service) {
        return (T) context.getSystemService(service);
    }

    /**
     * 得到整个app分配的内存阈值
     * @param context
     * @return
     */
    public static int calculateMemoryCacheSize(Context context) {
        ActivityManager am = getService(context, ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (largeHeap && SDK_INT >= HONEYCOMB) {
            memoryClass = am.getLargeMemoryClass();
        }
        return 1024 * 1024 * memoryClass / 7;
    }

    /**
     * 获取sd剩余的空间
     * @return
     */
    public static long calculateSdCardCacheSize(){
        File datapath = Environment.getDataDirectory();
        StatFs dataFs=new StatFs(datapath.getPath());
        long sizes=(long)dataFs.getFreeBlocks()*(long)dataFs.getBlockSize();
        long available=sizes/((1024*1024));
        return available;
    }

    /**
     * 检查当前线程是否是主线程
     */
    public static void checkMain() {
        if (!isMain()) {
            throw new IllegalStateException("Method call should happen from the main thread.");
        }
    }
    public static boolean isMain() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

}
