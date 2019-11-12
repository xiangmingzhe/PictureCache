package com.picture.lib_rhythm.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Time:2019/11/11
 * Author:xmz-dell
 * Description:
 */
public class BitmapUtils {
    /** */
    /**
     * 把图片变成圆角
     *
     * @param bitmap
     *            需要修改的图片
     * @param pixels
     *            圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, float pixels,int boarder) {
        return getRoundBitmapByShader(bitmap,bitmap.getWidth(),bitmap.getHeight(),pixels,boarder);
    }
    /** */
    /**
     * 把图片变成圆角
     *
     * @param drawable
     *            需要修改的图片
     * @param pixels
     *            圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Drawable drawable, float pixels,int boarder) {
        Bitmap bitmap=DrawableToBitmap(drawable);
        return getRoundBitmapByShader(bitmap,bitmap.getWidth(),bitmap.getHeight(),pixels,boarder);
    }

    /**
     * 通过BitmapShader 圆角边框
     * @param bitmap
     * @param outWidth
     * @param outHeight
     * @param radius
     * @param boarder
     * @return
     */
    public static Bitmap getRoundBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, float radius, int boarder) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float widthScale = outWidth * 1f / width;
        float heightScale = outHeight * 1f / height;

        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        //创建输出的bitmap
        Bitmap desBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        //创建canvas并传入desBitmap，这样绘制的内容都会在desBitmap上
        Canvas canvas = new Canvas(desBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //创建着色器
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //给着色器配置matrix
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        //创建矩形区域并且预留出border
        RectF rect = new RectF(boarder, boarder, outWidth - boarder, outHeight - boarder);
        //把传入的bitmap绘制到圆角矩形区域内
        canvas.drawRoundRect(rect, radius, radius, paint);

        if (boarder > 0) {
            //绘制boarder
            Paint boarderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            boarderPaint.setColor(Color.GREEN);
            boarderPaint.setStyle(Paint.Style.STROKE);
            boarderPaint.setStrokeWidth(boarder);
            canvas.drawRoundRect(rect, radius, radius, boarderPaint);
        }
        return desBitmap;
    }

    /**
     * Drawable  转bitmap
     * @param drawable
     * @return
     */
    public static Bitmap DrawableToBitmap(Drawable drawable) {

        // 获取 drawable 长宽
        int width = drawable.getIntrinsicWidth();
        int heigh = drawable.getIntrinsicHeight();

        drawable.setBounds(0, 0, width, heigh);

        // 获取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, heigh, config);
        // 创建bitmap画布
        Canvas canvas = new Canvas(bitmap);
        // 将drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 创建圆形图片
     * @param source
     * @return
     */
    public static Bitmap createCircleImage(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        float raduis = Math.min(width, height) * 0.5f;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //paint.setColor(Color.RED);
        //画布设置遮罩效果
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        //处理图像数据
        Bitmap bitmap = Bitmap.createBitmap(width, height, source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        //bitmap的显示由画笔paint来决定
        canvas.drawCircle(width * 0.5f, height *0.5f, raduis, paint);
        return bitmap;
    }
    /**
     * 创建圆形图片
     * @param source
     * @return
     */
    public static Bitmap createCircleImage(Bitmap source,int boarder) {
        return getCircleBitmapByShader(source,source.getWidth(),source.getHeight(),boarder);
    }

    /**
     * 通过BitmapShader 圆形边框
     * @param bitmap
     * @param outWidth
     * @param outHeight
     * @param boarder
     * @return
     */
    public static Bitmap getCircleBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int boarder) {
        if (bitmap == null) {
            return null;
        }
        int radius;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float widthScale = outWidth * 1f / width;
        float heightScale = outHeight * 1f / height;

        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        Bitmap desBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        if (outHeight > outWidth) {
            radius = outWidth / 2;
        } else {
            radius = outHeight / 2;
        }
        //创建canvas
        Canvas canvas = new Canvas(desBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        canvas.drawCircle(outWidth / 2, outHeight / 2, radius - boarder, paint);
        if (boarder > 0) {
            //绘制boarder
            Paint boarderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            boarderPaint.setColor(Color.GREEN);
            boarderPaint.setStyle(Paint.Style.STROKE);
            boarderPaint.setStrokeWidth(boarder);
            canvas.drawCircle(outWidth / 2, outHeight / 2, radius - boarder, boarderPaint);
        }
        return desBitmap;
    }
}
