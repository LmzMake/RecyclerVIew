package com.walxy.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.walxy.recyclerview.adapter.MyAdapter;
import com.walxy.recyclerview.bean.Bean;
import com.walxy.recyclerview.utils.OkHttp;

import java.io.IOException;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private String lxy = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=15&gender=2&ts=1871746850&page=";
    private RecyclerView mRcy;
    private MyAdapter mMyAdapter;
    private int page = 3;
    private LinearLayoutManager mManager;
    private Bean mBean;
    private SpringView mSv;
    private int[] pullAnimSrcs = new int[]{R.drawable.mt_pull, R.drawable.mt_pull01, R.drawable.mt_pull02, R.drawable.mt_pull03, R.drawable.mt_pull04, R.drawable.mt_pull05};
    private int[] refreshAnimSrcs = new int[]{R.drawable.mt_refreshing01, R.drawable.mt_refreshing02, R.drawable.mt_refreshing03, R.drawable.mt_refreshing04, R.drawable.mt_refreshing05, R.drawable.mt_refreshing06};
    private int[] loadingAnimSrcs = new int[]{R.drawable.mt_loading01, R.drawable.mt_loading02};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mSv.setHeader(new DefaultHeader(this));
        mSv.setFooter(new DefaultFooter(this));
        mSv.setType(SpringView.Type.FOLLOW);
        mSv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSv.onFinishFreshAndLoad();
                        page--;
                        setOkHttp();
                        mMyAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSv.onFinishFreshAndLoad();
                        page++;
                        setOkHttp();
                        mMyAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
        mSv.setHeader(new MeituanHeader(this, pullAnimSrcs, refreshAnimSrcs));
        mSv.setFooter(new MeituanFooter(this, loadingAnimSrcs));
    }

    private void initView() {
        mRcy = (RecyclerView) findViewById(R.id.rcy);
        mManager = new LinearLayoutManager(this);
        mRcy.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRcy.setLayoutManager(mManager);
        setOkHttp();
        mSv = (SpringView) findViewById(R.id.sv);
    }

    public void setOkHttp() {
        OkHttp.getAsync(lxy + page, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                mBean = gson.fromJson(result, Bean.class);
                mMyAdapter = new MyAdapter(MainActivity.this, mBean);
                mMyAdapter.setOnItemClickLitener(new MyAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Toast.makeText(MainActivity.this, mBean.data.get(position).userName, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        Toast.makeText(MainActivity.this, mBean.data.get(position).userName, Toast.LENGTH_LONG).show();
                    }
                });
                mRcy.setAdapter(mMyAdapter);
            }
        });
    }
}
