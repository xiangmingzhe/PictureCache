package com.picture.lib_rhythm.animate;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.picture.lib_rhythm.R;
import com.picture.lib_rhythm.RequestCreator;
import com.picture.lib_rhythm.Rhythm;
import com.picture.lib_rhythm.constant.AnimateType;

/**
 * Time:2019/11/18
 * Author:xmz-dell
 * Description:
 */
public class AnimateManage {
    volatile static AnimateManage singleton;
    private int animateID;//动画资源ID
    private AlphaAnimation alphaAnimation;
    private static String TAG="AnimateManage";
    public static AnimateManage getInstance() {
        if (singleton == null) {
            synchronized (AnimateManage.class) {
                if (singleton == null) {
                    singleton = new AnimateManage();
                }
            }
        }
        return singleton;
    }

    /**
     * 为目标控件增绑定动画
     *
     * @param into
     */
    public void bindAnimate(ImageView into, Context context,AnimateType animateType) {
        if (into == null) {
            throw new NullPointerException("into Can not be empty");
        }
        if(animateType==null){
            return;
        }
        Log.d(TAG,"bindAnimate:"+animateType.name());
        switch (animateType.name()) {
            case "CROSS_FADE":
                startAlphaAnimation(into);
                break;
            case "ANIMATE":
                Animation valueAnimator = (Animation) AnimationUtils.loadAnimation(context, animateID);
                into.setAnimation(valueAnimator);
                valueAnimator.start();
                break;
            default:
                into.clearAnimation();
                break;
        }
    }

    private void startAlphaAnimation(ImageView into) {

        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animationSet.addAnimation(animation);
        into.startAnimation(animationSet);

    }

    public AnimateManage setAnimateID(int animateID) {
        this.animateID = animateID;
        return this;
    }
}
