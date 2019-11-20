package com.picture.lib_rhythm.bean;

import android.graphics.Bitmap;

import com.picture.lib_rhythm.constant.Watermark;

/**
 * Time:2019/11/19
 * Author:xmz-dell
 * Description:
 */
public class WatermarkInfo {
    private Watermark watermark;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;
    private Bitmap bitmap;//水印图片

    public WatermarkInfo(Watermark watermark,Bitmap watermarkBitmap){
            this.watermark=watermark;
            this.bitmap=watermarkBitmap;
    }
    public WatermarkInfo(Watermark watermark,Bitmap watermarkBitmap,int paddingLeft,int paddingTop,int paddingRight,int paddingBottom){
        this.watermark=watermark;
        this.bitmap=watermarkBitmap;
        this.paddingLeft=paddingLeft;
        this.paddingTop=paddingTop;
        this.paddingBottom=paddingBottom;
        this.paddingRight=paddingRight;
    }
    public Watermark getWatermark() {
        return watermark;
    }

    public void setWatermark(Watermark watermark) {
        this.watermark = watermark;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
