package com.example.dell.picturecache;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.picture.lib_rhythm.Rhythm;

public class MainActivity extends Activity {
    private Button bt_loadimage;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        bt_loadimage=findViewById(R.id.bt_loadimage);
        imageView=findViewById(R.id.image);
        bt_loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rhythm.with(MainActivity.this).load("https://uinpay.oss-cn-shenzhen.aliyuncs.com/icon/20190515/caiu.png").error(R.drawable.ic_launcher_background).into(imageView);
            }
        });
    }
}
