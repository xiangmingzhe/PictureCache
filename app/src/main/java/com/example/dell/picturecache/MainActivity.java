package com.example.dell.picturecache;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.picture.lib_rhythm.Rhythm;

public class MainActivity extends Activity {
    private Button bt_loadimage;
    private Button bt_loadimage_id;
    private Button bt_loadimage_local;

    private ImageView imageView;
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

        imageView=findViewById(R.id.image);
        bt_loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rhythm.with(MainActivity.this).transform(10.0f).load("https://pics7.baidu.com/feed/e1fe9925bc315c6019816a380ec27c164854774d.jpeg?token=d21ab74381ca956c4ca28ef5c90e4868&s=30FE7084C273359450A844900300708E").openGif(false).error(R.drawable.robot).placeholder(R.drawable.robot).into(imageView);
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
    }
}
