package com.example.dell.picturecache;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dell.picturecache.adapter.RecyclerviewAdapter;
import com.example.dell.picturecache.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {
    private RecyclerView rcy;
    private String url="https://pics7.baidu.com/feed/e1fe9925bc315c6019816a380ec27c164854774d.jpeg?token=d21ab74381ca956c4ca28ef5c90e4868&s=30FE7084C273359450A844900300708E";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
    }
    private void initView(){
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
        stringList.add("http://g.hiphotos.baidu.com/image/pic/item/d52a2834349b033b95e7b4601fce36d3d539bd19.jpg");
        stringList.add("http://e.hiphotos.baidu.com/image/pic/item/4610b912c8fcc3cef70d70409845d688d53f20f7.jpg");
//        stringList.add("http://g.hiphotos.baidu.com/image/pic/item/c2cec3fdfc03924590b2a9b58d94a4c27d1e2500.jpg");
//        stringList.add("http://h.hiphotos.baidu.com/image/pic/item/0b46f21fbe096b63491b16ea06338744ebf8ac0e.jpg");
//        stringList.add("http://g.hiphotos.baidu.com/image/pic/item/279759ee3d6d55fb1cc13e9c67224f4a20a4dd01.jpg");
//        stringList.add("http://f.hiphotos.baidu.com/image/pic/item/11385343fbf2b2114e41cdf4c08065380cd78e19.jpg");

        return  stringList;
    }
}
