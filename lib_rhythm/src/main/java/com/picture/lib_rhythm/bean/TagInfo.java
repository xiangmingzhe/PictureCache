package com.picture.lib_rhythm.bean;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.picture.lib_rhythm.constant.AnimateType;
import com.picture.lib_rhythm.transformation.BlurTransformation;

/**
 * Time:2019/11/12
 * Author:xmz-dell
 * Description:
 */
public class TagInfo {
    private String url;
    private ImageView into;
    private Bitmap bitmap;
    private AnimateType animateType;
    private int animateID;
    private boolean isTag;
    private BlurTransformation blurTransformation;
    public TagInfo(String url,ImageView into){
        this.url=url;
        this.into=into;
    }
    public TagInfo(String url,ImageView into,AnimateType animateType,int animateID){
        this.url=url;
        this.into=into;
        this.animateType=animateType;
        this.animateID=animateID;
    }
    public TagInfo(String url,ImageView into,AnimateType animateType,int animateID,BlurTransformation blurTransformation){
        this.url=url;
        this.into=into;
        this.animateType=animateType;
        this.animateID=animateID;
        this.blurTransformation=blurTransformation;
    }
    public boolean isTag() {
        return isTag;
    }

    public void setTag(boolean tag) {
        isTag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getInto() {
        return into;
    }

    public void setInto(ImageView into) {
        this.into = into;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setAnimateType(AnimateType animateType) {
        this.animateType = animateType;
    }

    public AnimateType getAnimateType() {
        return animateType;
    }

    public int getAnimateID() {
        return animateID;
    }

    public void setAnimateID(int animateID) {
        this.animateID = animateID;
    }

    public BlurTransformation getBlurTransformation() {
        return blurTransformation;
    }

    public void setBlurTransformation(BlurTransformation blurTransformation) {
        this.blurTransformation = blurTransformation;
    }
}
