# PictureCache
# PictureCache是一个图片加载框架,支持gif图片加载，目前调用方式跟Picasso保持同步,调用如下
 
 Rhythm.with(MainActivity.this).load("https://uinpay.oss-cn-shenzhen.aliyuncs.com/icon/20190515/caifu.png").openGif(false).error(R.drawable.robot).placeholder(R.drawable.robot).into(imageView);
