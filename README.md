# PictureCache
# PictureCache是一个图片加载框架,支持gif图片加载、设置圆角，目前调用方式跟Picasso保持同步,调用如下
 
 Rhythm.with(context).load(url).openGif(false).error(R.drawable.robot).placeholder(R.drawable.robot).into(imageView);
