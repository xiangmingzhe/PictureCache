package com.picture.lib_rhythm.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import com.picture.lib_rhythm.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Time:2019/11/7
 * Author:xmz-dell
 * Description:
 */
public class LocalCache {
    private String cachePath;

    public LocalCache(Context context, String uniqueName) {
        if(TextUtils.isEmpty(uniqueName)){
            throw new NullPointerException("uniqueName Can not be empty");
        }
        cachePath = getCacheDirString(context, uniqueName);
    }

    /**
     * 根据url获取bitmap
     * @param url
     * @return
     */
    public Bitmap getBitmapFromLocal(String url){
        try{
            File file = new File(cachePath, encode(url));
            if (file.exists()) {
                // 如果文件存在
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取缓存目录的路径
     * @param context
     * @param uniqueName
     * @return
     */
    private String getCacheDirString(Context context, String uniqueName) {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            file = new File(context.getExternalCacheDir(), uniqueName);
        } else {
            file = new File(context.getCacheDir(), uniqueName);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 设置Bitmap数据到本地
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        if(TextUtils.isEmpty(url)||bitmap==null){
            throw new NullPointerException("url Can not be empty || bitmap Can not be empty");
        }
        if(Utils.calculateSdCardCacheSize()<Utils.getBitmapKB(bitmap)){
            throw new IllegalArgumentException("sdcard Insufficient space left");
        }
        FileOutputStream fos = null;
        try {
            String fileName = encode(url);
            File file = new File(cachePath, fileName);
            File parentFile = file.getParentFile();//获取上级所有目录
            if (!parentFile.exists()) {
                // 如果文件不存在，则创建文件夹
                parentFile.mkdirs();
            }
            fos = new FileOutputStream(file);
            // 将图片压缩到本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fos != null) {
                try {
                    fos.close();//关闭流
                    fos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /***
     * md5 加密
     * @param pwd
     * @return
     */
    public  String encode(String pwd) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(pwd.getBytes("UTF-8"));
            for (int i = 0; i < bytes.length; i++) {
                String s = Integer.toHexString(0xff & bytes[i]);

                if (s.length() == 1) {
                    sb.append("0" + s);
                } else {
                    sb.append(s);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
