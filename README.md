# PictureCache
## 关于
### PictureCache 是一个功能强大且易于使用的Android库。该库封装了Android开发中的大部分图片加载时的需求,通过使用其封装的API,可以大大提高开发效率。
### 使用效果,请根据简书地址查看。
### 简书地址:https://www.jianshu.com/p/e9a458a18be8
 
# Android Studio集成方式
### 项目的build.gradle中引用如下:
  ```
 implementation 'com.lib:pic_cache:1.0.1'
  
  ```
### 根项目的build.gradle引用如下:

```
allprojects {
    repositories {
        google()
        jcenter()
        maven {  url "https://raw.githubusercontent.com/xiangmingzhe/PictureCache/master"}
    }
}
```

## 设置图片四周圆角:
 Rhythm.with(context).load(url).transform(10.0f).into(imageView);
## 设置占位图: 
Rhythm.with(context).load(url).placeholder(R.drawable.xx).into(imageView);
## 加载错误视图:
 Rhythm.with(context).load(url).error(R.drawable.xx).into(imageView);
## 设置成圆形图片
 Rhythm.with(context).load(url).style(TypeEnum.CIRCLE).into(imageView);
## 设置圆形or圆角图片边框
 Rhythm.with(context).load(url).style(TypeEnum.CIRCLE).boarder(2).into(imageView);
 
 Rhythm.with(context).load(url).transform(10.0f).boarder(2).into(imageView);
## 增加图片渐变

## 图片懒加载

## 取消单个加载
 Rhythm.with(ListActivity.this).cancleTask(tag);
## 取消所有加载
 Rhythm.with(context).cancleAllTask();

## 加载图片高斯模糊效果
 Rhythm.with(context).bitmapTransform(new BlurTransformation(10)).into(imageView);
 其中BlurTransformation中分别包括模糊半径和指定模糊前缩小的倍数。


# 动画相关
### 图片淡入淡出效果
 Rhythm.with(context).load(url).crossFade().into(imageView);
### 支持图片自定义动画
Rhythm.with(context).load(url).Animation(R.anim.xx).into(imageView);
### 设置无动画
Rhythm.with(context).load(url).dontAnimation().into(imageView);
# 增加图片水印功能,详细功能:分为居中,左上角,右上角,左下角,右下角
### (1)居中水印
Rhythm.with(context).load(url).watermark(new WatermarkInfo(Watermark.CENTER,watermakeBitmap)).into(imageView);
### (2)左上角水印
 Rhythm.with(context).load(url)
                     .watermark(new WatermarkInfo(Watermark.LEFT_TOP,watermakeBitmap,paddingLeft,paddingTop,paddingRight,paddingBottom))
                     .into(imageView);
### (3)右上角水印                    
 Rhythm.with(context).load(url)
                    .watermark(new WatermarkInfo(Watermark.RIGHT_TOP,watermakeBitmap,paddingLeft,paddingTop,paddingRight,paddingBottom))
                     .into(imageView);
### (4)左下角水印                    
 Rhythm.with(context).load(url)
                  .watermark(new WatermarkInfo(Watermark.LEFT_BOTTOM,watermakeBitmap,paddingLeft,paddingTop,paddingRight,paddingBottom))
                     .into(imageView);                     
### (5)右下角水印                    
 Rhythm.with(context).load(url)
                .watermark(new WatermarkInfo(Watermark.RIGHT_BOTTOM,watermakeBitmap,paddingLeft,paddingTop,paddingRight,paddingBottom))
                     .into(imageView);   
 
