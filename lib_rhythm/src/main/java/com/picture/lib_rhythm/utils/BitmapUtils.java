package com.picture.lib_rhythm.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Time:2019/11/11
 * Author:xmz-dell
 * Description:
 */
public class BitmapUtils {
    private static final String TAG="BitmapUtils";
    /** */
    /**
     * 把图片变成圆角
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, float pixels, int boarder) {
        return getRoundBitmapByShader(bitmap, bitmap.getWidth(), bitmap.getHeight(), pixels, boarder);
    }
    /** */
    /**
     * 把图片变成圆角
     *
     * @param drawable 需要修改的图片
     * @param pixels   圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Drawable drawable, float pixels, int boarder) {
        Bitmap bitmap = DrawableToBitmap(drawable);
        return getRoundBitmapByShader(bitmap, bitmap.getWidth(), bitmap.getHeight(), pixels, boarder);
    }

    /**
     * 通过BitmapShader 圆角边框
     *
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
     *
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
     *
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
        canvas.drawCircle(width * 0.5f, height * 0.5f, raduis, paint);
        return bitmap;
    }

    /**
     * 创建圆形图片
     *
     * @param source
     * @return
     */
    public static Bitmap createCircleImage(Bitmap source, int boarder) {
        return getCircleBitmapByShader(source, source.getWidth(), source.getHeight(), boarder);
    }

    /**
     * 通过BitmapShader 圆形边框
     *
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

    /**
     * 根据图片得到二进制
     * @return
     */
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 根据二进制生成图片
     * @param temp
     * @return
     */
    public static Bitmap getBitmapFromByte(byte[] temp){
        Log.d("getBitmapFromByte","根据二进制生成图片");

        if(temp != null){
            Log.d("getBitmapFromByte","getBitmapFromByte:"+temp.length);
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
    }

    /**
     * 设置高斯模糊效果   API 17以上才能使用
     * @param context  上下文
     * @param source  来源bitmap
     * @param radius 模糊半径
     * @param scale  指定模糊前缩小的倍数
     * @return
     */
    public static Bitmap rsBlur(Context context, Bitmap source, int radius, float scale){
        if(scale<=0){
            scale=1;
        }
        Log.i(TAG,"origin size:"+source.getWidth()+"*"+source.getHeight());
        int width = Math.round(source.getWidth() * scale);
        int height = Math.round(source.getHeight() * scale);

        Bitmap inputBmp = Bitmap.createScaledBitmap(source,width,height,false);

        RenderScript renderScript =  RenderScript.create(context);

        Log.i(TAG,"scale size:"+inputBmp.getWidth()+"*"+inputBmp.getHeight());

        // Allocate memory for Renderscript to work with

        final Allocation input = Allocation.createFromBitmap(renderScript,inputBmp);
        final Allocation output = Allocation.createTyped(renderScript,input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);

        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);

        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp);

        renderScript.destroy();
        return inputBmp;
    }
    /**
     * 设置水印图片在左上角
     *
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskLeftTop(Bitmap src, Bitmap watermark, int paddingLeft, int paddingTop) {
        return createWaterMaskBitmap(src, watermark, dp2px(paddingLeft), dp2px(paddingTop));
    }

    /**
     * 设置水印图片在右下角
     *
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskRightBottom(Bitmap src, Bitmap watermark, int paddingRight, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark, src.getWidth() - watermark.getWidth() - dp2px(paddingRight), src.getHeight() - watermark.getHeight() - dp2px(paddingBottom));
    }

    /**
     * 设置水印图片到右上角
     *
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskRightTop(Bitmap src, Bitmap watermark, int paddingRight, int paddingTop) {
        return createWaterMaskBitmap(src, watermark, src.getWidth() - watermark.getWidth() - dp2px(paddingRight), dp2px(paddingTop));
    }

    /**
     * 设置水印图片到左下角
     *
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskLeftBottom(Bitmap src, Bitmap watermark, int paddingLeft, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark, dp2px(paddingLeft), src.getHeight() - watermark.getHeight() - dp2px(paddingBottom));
    }

    /**
     * 设置水印图片到中间
     *
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createWaterMaskCenter(Bitmap src, Bitmap watermark) {
        return createWaterMaskBitmap(src, watermark, (src.getWidth() - watermark.getWidth()) / 2, (src.getHeight() - watermark.getHeight()) / 2);
    }

    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark, int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newb);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save();
        // 存储
        canvas.restore();
        return newb;
    }

    /**
     * 将View转成Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap viewToBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * dp转pix
     *
     * @param dp
     * @return
     */
    public static int dp2px(float dp) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
