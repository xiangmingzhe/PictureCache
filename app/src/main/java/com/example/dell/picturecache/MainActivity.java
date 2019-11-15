package com.example.dell.picturecache;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.picture.lib_rhythm.Rhythm;
import com.picture.lib_rhythm.constant.GraphicalType;

public class MainActivity extends Activity {
    private Button bt_loadimage;
    private Button bt_loadimage_id;
    private Button bt_loadimage_local;
    private Button bt_loadimage_circular;
    private ImageView imageView;
    private Button bt_loadimage_list;
    private Button bt_loadimage_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        bt_loadimage=findViewById(R.id.bt_loadimage);
        bt_loadimage_id=findViewById(R.id.bt_loadimage_id);
        bt_loadimage_local=findViewById(R.id.bt_loadimage_local);
        bt_loadimage_circular=findViewById(R.id.bt_loadimage_circular);
        bt_loadimage_list=findViewById(R.id.bt_loadimage_list);
        imageView=findViewById(R.id.image);
        bt_loadimage_progress=findViewById(R.id.bt_loadimage_progress);
        bt_loadimage_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        });
        bt_loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rhythm.with(MainActivity.this)
                        .transform(10.0f).boarder(3)
                        .load("https://pics7.baidu.com/feed/e1fe9925bc315c6019816a380ec27c164854774d.jpeg?token=d21ab74381ca956c4ca28ef5c90e4868&s=30FE7084C273359450A844900300708E")
                        .into(imageView);
            }
        });
        bt_loadimage_circular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rhythm.with(MainActivity.this)
                        .style(GraphicalType.CIRCLE).boarder(3)
                        .load("https://pics7.baidu.com/feed/e1fe9925bc315c6019816a380ec27c164854774d.jpeg?token=d21ab74381ca956c4ca28ef5c90e4868&s=30FE7084C273359450A844900300708E")
                        .into(imageView);
            }
        });
        bt_loadimage_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rid=R.drawable.robot;
                Rhythm.with(MainActivity.this).transform(10.0f).load(rid).openGif(false).error(R.drawable.robot).placeholder(R.drawable.robot).into(imageView);
            }
        });
        bt_loadimage_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rhythm.with(MainActivity.this).transform(10.0f).load("https://uinpay.oss-cn-shenzhen.aliyuncs.com/icon/20190515/caifu.png").openGif(false).error(R.drawable.robot).placeholder(R.drawable.robot).into(imageView);
            }
        });
        bt_loadimage_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
