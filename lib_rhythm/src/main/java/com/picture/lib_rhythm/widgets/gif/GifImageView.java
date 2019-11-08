package com.picture.lib_rhythm.widgets.gif;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;


import com.picture.lib_rhythm.R;

import java.io.InputStream;
import java.lang.reflect.Field;
/**
 * Time:2019/11/8
 * Author:xmz-dell
 * Description:
 */
public class GifImageView extends ImageView
{


    /**
     * 播放GIF动画的关键类
     */
    private Movie mMovie;
    /**
     * 控制播放的按钮
     */
    private Bitmap mStartButton;

    /**
     * 记录动画开始的时间
     */
    private long mMovieStart;

    /**
     * GIF图片的宽度
     */
    private int mImageWidth;

    /**
     * GIF图片的高度
     */
    private int mImageHeight;

    /**
     * 图片是否正在播放
     */
    private boolean isPlaying;

    /**
     * 是否允许自动播放
     */
    private boolean isAutoPlay;

    public GifImageView(Context context)
    {
        super(context);
        Log.e("ten", "72");
    }

    public GifImageView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
        Log.e("ten", "73");
    }

    public GifImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        setImageResLoad(context, attrs, defStyleAttr);

    }
    public void isAutoPlay(boolean isAutoPlay){
        this.isAutoPlay=isAutoPlay;
        invalidate();
    }
    private void setImageResLoad(Context context, AttributeSet attrs, int defStyleAttr)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GifImageView);
        Log.e("ten", "1 ");
        int resourceID = getResourcesID(typedArray);

        if (resourceID != 0) {
            Log.e("ten", "2 ");
            InputStream inputStream = getResources().openRawResource(resourceID);
            //对图片进行解码
            mMovie = Movie.decodeStream(inputStream);
            if (mMovie != null) {
                Log.e("ten", "3 ");
                // 如果返回值不等于null，就说明这是一个GIF图片，下面获取是否自动播放的属性
                isAutoPlay = typedArray.getBoolean(R.styleable.GifImageView_auto_play, false);
                Bitmap bitmap ;
                bitmap = BitmapFactory.decodeStream(inputStream);
                if(bitmap!=null){
                    mImageWidth = bitmap.getWidth();
                    mImageHeight = bitmap.getHeight();
                    bitmap.recycle();
                }else{
                    bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.anim_flag_iceland);
                    mImageWidth = bitmap.getWidth();
                    mImageHeight = bitmap.getHeight();
                    bitmap.recycle();
                }

            }

        }
        if (typedArray != null) {
            typedArray.recycle();
        }
    }

    /**
     * 通过Java反射，获取src指定图片资源所对应的id。
     */
    public int getResourcesID(TypedArray typedArray)
    {
        Log.e("ten", "4 ");
        try {
            Field field = TypedArray.class.getDeclaredField("mValue");
            field.setAccessible(true);
            TypedValue typedValueObject = (TypedValue) field.get(typedArray);
            return typedValueObject.resourceId;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return 0;
    }




    @Override
    protected void onDraw(Canvas canvas)
    {
        Log.e("ten", "6");
        if (mMovie == null) {
            Log.e("ten", "7b");
            // mMovie等于null，说明是张普通的图片，则直接调用父类的onDraw()方法
            super.onDraw(canvas);

        } else {
            Log.e("ten", "7c");
            //如果mMovie不等于null，那就说明是gif图片
            if (isAutoPlay) {
                Log.e("ten", "7d");
                //如果允许播放,调用palyMovie();
                playMovie(canvas);
                invalidate();
            } else {
                Log.e("ten", "7e");
                //不允许自动播放，判断是否要播放
                if (isPlaying) {
                    if (playMovie(canvas)) {
                        isPlaying = false;
                        Log.e("ten", "7f");
                    }
                    invalidate();
                } else {
                    // 还没开始播放就只绘制GIF图片的第一帧，并绘制一个开始按钮
                    Log.e("ten", "g");
                    mMovie.setTime(0);
                    mMovie.draw(canvas, 0, 0);
                    int imageX = (mImageWidth - mStartButton.getWidth()) / 2;
                    int imageY = (mImageHeight - mStartButton.getHeight()) / 2;
                    canvas.drawBitmap(mStartButton, imageX, imageY, null);

                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("ten", "onMeasure_1");
        if (mMovie != null) {
            Log.e("ten", "onMeasure_2");
            // 如果是GIF图片则重写设定myImageView的大小
            setMeasuredDimension(mImageWidth, mImageHeight);
        }
    }
    /**
     * 开始播放GIF动画，播放完成返回true，未完成返回false。
     *
     * @param canvas
     * @return 播放完成返回true，未完成返回false。
     */
    public boolean playMovie(Canvas canvas)
    {
        Log.e("ten", "playMovie_1");
        long now = SystemClock.uptimeMillis();
        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        int duration = mMovie.duration();
        if (duration == 0) {
            duration = 1000;
        }
        int loadTime = (int) ((now - mMovieStart) % duration);
        mMovie.setTime(loadTime);
        mMovie.draw(canvas, 0, 0);
        if ((now - mMovieStart) >= duration) {
            mMovieStart = 0;
            return true;
        }
        return false;
    }
}
