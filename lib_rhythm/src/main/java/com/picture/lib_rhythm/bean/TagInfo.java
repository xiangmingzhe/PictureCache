package com.picture.lib_rhythm.bean;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Time:2019/11/12
 * Author:xmz-dell
 * Description:
 */
public class TagInfo {
    private String url;
    private ImageView into;
    private Bitmap bitmap;
    private boolean isTag;
    public TagInfo(String url,ImageView into){
        this.url=url;
        this.into=into;
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
}
