package com.example.dell.picturecache;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.dell.picturecache.adapter.RecyclerviewAdapter;
import com.example.dell.picturecache.adapter.TestAdapter;
import com.picture.lib_rhythm.Rhythm;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {
    private RecyclerView rcy;
    private Button bt_cancleall;
    private Button bt_canclesingle;
    private String url="https://pics7.baidu.com/feed/e1fe9925bc315c6019816a380ec27c164854774d.jpeg?token=d21ab74381ca956c4ca28ef5c90e4868&s=30FE7084C273359450A844900300708E";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
    }
    private void initView(){
        bt_cancleall=findViewById(R.id.bt_cancleall);
        bt_canclesingle=findViewById(R.id.bt_canclesingle);
        bt_cancleall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rhythm.with(ListActivity.this).cancleAllTask();
            }
        });
        bt_canclesingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rhythm.with(ListActivity.this).cancleTask("http://res.img.ifeng.com/2012/0428/wm_0ebc84813b2d4ac3b4096d6cbbe196d9.jpg");
            }
        });
        rcy=findViewById(R.id.rcy);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcy.setLayoutManager(layoutManager);
        TestAdapter adapter = new TestAdapter(R.layout.item_rcy, urlList());
        adapter.setContext(this);
        rcy.setAdapter(adapter);
    }
    private List<String>urlList(){
        List<String>stringList=new ArrayList<>();
        stringList.add("http://pic1.win4000.com/wallpaper/5/58e6f9a62e989.jpg");
        stringList.add("http://img4.imgtn.bdimg.com/it/u=3749936194,4185944615&fm=214&gp=0.jpg");
        stringList.add("http://res.img.ifeng.com/2012/0428/wm_0ebc84813b2d4ac3b4096d6cbbe196d9.jpg");
        stringList.add("http://pic1.win4000.com/wallpaper/5/58e6f99e65c3a.jpg");
        stringList.add("http://img010.hc360.cn/m1/M05/7D/12/wKhQcFQ1ZiKEC66RAAAAAHBFgKg402.jpg");
        stringList.add("http://img003.hc360.cn/m6/M04/4D/73/wKhQolYohXeEfWz9AAAAAEFYOl4869.JPG");

        return  stringList;
    }
}
