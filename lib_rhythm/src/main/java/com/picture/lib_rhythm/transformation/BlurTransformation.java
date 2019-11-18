package com.picture.lib_rhythm.transformation;

import android.content.Context;

/**
 * Time:2019/11/18
 * Author:xmz-dell
 * Description:高斯模糊实体类
 */
public class BlurTransformation {
    private int radius;
    private int scale;
    public BlurTransformation(int radius){
        this.radius=radius;
    }
    public BlurTransformation(int radius,int scale){
        this.radius=radius;
        this.scale=scale;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
