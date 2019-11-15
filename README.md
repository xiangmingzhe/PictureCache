# PictureCache
# PictureCache是一个图片加载框架,支持gif图片加载、设置圆角。
 
# 设置图片四周圆角:
 Rhythm.with(context).load(url).transform(10.0f).into(imageView);
# 设置占位图: 
Rhythm.with(context).load(url).placeholder(R.drawable.xx).into(imageView);
# 加载错误视图:
 Rhythm.with(context).load(url).error(R.drawable.xx).into(imageView);
# 开启加载gif图片
 Rhythm.with(context).load(url).openGif(false).into(imageView);
# 设置成圆形图片
 Rhythm.with(context).load(url).style(TypeEnum.CIRCLE).into(imageView);
# 设置圆形or圆角图片边框
 Rhythm.with(context).load(url).style(TypeEnum.CIRCLE).boarder(2).into(imageView);
 
 Rhythm.with(context).load(url).transform(10.0f).boarder(2).into(imageView);
# 增加图片渐变

# 图片懒加载

# 取消单个加载
 Rhythm.with(ListActivity.this).cancleTask(tag);
# 取消所有加载
 Rhythm.with(context).cancleAllTask();

# 加载图片模糊效果

 

 
