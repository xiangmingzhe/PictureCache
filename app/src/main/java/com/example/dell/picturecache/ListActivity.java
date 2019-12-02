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



        stringList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1186878998,3597197046&fm=15&gp=0.jpg");
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574660491422&di=1b9f7210e98335c311e575eff160a871&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F7%2F57eb892e7431e.jpg");
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574660491422&di=7239a2eca34de15dc0ae362a938f499d&imgtype=0&src=http%3A%2F%2Fwww.qqoi.cn%2Fimg_bizhi%2F70707972.jpeg");
        stringList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3120478776,2048301353&fm=26&gp=0.jpg");
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574660491421&di=1d37d910b7d6c153d2d561ffe74e3a0b&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2018-11-27%2F5bfce839a79b4.jpg");
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574660491421&di=7bede38544b5528cacf5c3c2acb9de51&imgtype=0&src=http%3A%2F%2Fp2.qhimgs4.com%2Ft0173189944640e215c.jpg");
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574660491420&di=0534745e2197fab1c37746e1b89c9beb&imgtype=0&src=http%3A%2F%2Fsc.68design.net%2Fphotofiles%2F201807%2FKUukqkpm5n.jpg");
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574660491419&di=6ed3929f5f638906648718c694f536ff&imgtype=0&src=http%3A%2F%2Fimg-download.pchome.net%2Fdownload%2F1k1%2F34%2F55%2Fofhy7l-1o88.jpg");
        stringList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574660491419&di=2e262de1639d01787a8226189b383744&imgtype=0&src=http%3A%2F%2Fstatic01.coloros.com%2Fbbs%2Fdata%2Fattachment%2Fforum%2F201508%2F27%2F084342curucwvezb30cll0.jpg");



        return  stringList;
    }
}
