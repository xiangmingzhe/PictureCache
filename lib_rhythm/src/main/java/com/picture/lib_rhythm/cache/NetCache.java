package com.picture.lib_rhythm.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.picture.lib_rhythm.R;
import com.picture.lib_rhythm.utils.BitmapUtils;
import com.picture.lib_rhythm.widgets.gif.GifImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Time:2019/11/7
 * Author:xmz-dell
 * Description:
 */
public class NetCache {
    private LruCache lruCache;
    private LocalCache localCache;
    private static final String TAG="NetCache";
    private Drawable errorDrawable;
    private Context mContext;
    private float radius=0f;
    public NetCache(Cache lruCache,LocalCache localCache){
        this.lruCache=(LruCache) lruCache;
        this.localCache=localCache;
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Object[] objects = (Object[]) msg.obj;
            ImageView imageView = (ImageView) objects[0];
            String mUrl = (String) objects[1];
            imageView.setTag(mUrl);
            setErrorView(objects,imageView);
        }
    };

    /**
     * 错误视图
     * @param errorDrawable
     * @return
     */
    public NetCache error(Drawable errorDrawable){
        this.errorDrawable=errorDrawable;
        return this;
    }
    /**
     * 设置圆角
     * @param radius 弧度
     * @return
     */
    public NetCache transform(float radius) {
        this.radius=radius;
        return this;
    }

    /**
     * 是否需要设置圆角
     * @return
     */
    private boolean isRoundCorner(){
        if(radius!=0f){
            return true;
        }
        return false;
    }
    /**
     * 设置错误视图
     * @param objects
     * @param imageView
     */
    private void setErrorView(Object[] objects,ImageView imageView){
        if(errorDrawable!=null){
            boolean isSuccess=(boolean) objects[2];
            if(!isSuccess){
                if(isRoundCorner()){
                    Bitmap bitmap=BitmapUtils.toRoundCorner(errorDrawable,radius);
                    imageView.setImageBitmap(bitmap);
                }else{
                    imageView.setImageDrawable(errorDrawable);
                }
            }
        }
    }
    /**
     * 加载图片
     * @param iv
     * @param url
     */
    public void loadBitmap(final ImageView iv, final String url, Context context){
        if(iv==null||TextUtils.isEmpty(url)||context==null){
            throw new NullPointerException("ImageView Can not be empty || url Can not be empty || context Can not be empty");
        }
        this.mContext=context;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=loadBitmap(url);

                if(bitmap!=null){
                    if(isRoundCorner()){
                        iv.setImageBitmap(BitmapUtils.toRoundCorner(bitmap,radius));
                    }else{
                        iv.setImageBitmap(bitmap);
                    }
                }else{
                    new BitmapTask().execute(iv, url);// 启动AsyncTask,
                }
            }
        });
    }

    /**
     * 尝试下加载内存-->磁盘
     * @param url
     * @return
     */
    private Bitmap loadBitmap(String url){
        if(lruCache.get(url)!=null){
                return lruCache.get(url);
        }
        if(localCache.getBitmapFromLocal(url)!=null){
            return localCache.getBitmapFromLocal(url);
        }
        return null;
    }


    /**
     * Handler和线程池的封装
     * <p/>
     * 第一个泛型: 参数类型
     * 第二个泛型: 更新进度的泛型,
     * 第三个泛型是onPostExecute的返回结果
     */
    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView ivPicture;
        private String url;
        private Object[] objects;
        private Message message;

        /**
         * 后台耗时方法在此执行, 子线程
         */
        @Override
        protected Bitmap doInBackground(Object... params) {
            ivPicture = (ImageView) params[0];
            url = (String) params[1];
            message = handler.obtainMessage();
            objects = new Object[]{ivPicture, url,true
            };
            message.obj = objects;
            handler.sendMessage(message);
            return downloadBitmap(url,ivPicture,objects,message);
        }

        /**
         * 更新进度, 主线程
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        /**
         * 耗时方法结束后,执行该方法, 主线程
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                String bindUrl = (String) ivPicture.getTag();
                if (url.equals(bindUrl)) {// 确保图片设定给了正确的imageview
                    if(isRoundCorner()){
                        result= BitmapUtils.toRoundCorner(result,radius);
                    }
                    ivPicture.setImageBitmap(result);
                    localCache.setBitmapToLocal(url, result);// 将图片保存在本地
                    lruCache.set(url, result);// 将图片保存在内存
                    Log.d(TAG,"从网络缓存读取图片啦");
                }
            }else{
                Log.d(TAG,"图片网络加载失败");
                message = handler.obtainMessage();
                objects = new Object[]{ivPicture, url,false
                };
                message.obj = objects;
                handler.sendMessage(message);

            }
        }
    }

    /**
     * 下载图片
     *
     * @param url
     * @return
     */
    private Bitmap downloadBitmap(String url,ImageView iv,Object[]objects,Message message) {

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();

                //图片压缩处理
                BitmapFactory.Options option = new BitmapFactory.Options();
                // option.inSampleSize = 2;//宽高都压缩为原来的二分之一, 此参数需要根据图片要展示的大小来确定
                option.inPreferredConfig = Bitmap.Config.RGB_565;//设置图片格式

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, option);
                return bitmap;
            }

        } catch (Exception e) {
            Log.d(TAG,"图片加载失败,准备加载错误视图");
            message = handler.obtainMessage();
            objects = new Object[]{iv, url,false
            };
            message.obj = objects;
            handler.sendMessage(message);
            e.printStackTrace();

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }


}

