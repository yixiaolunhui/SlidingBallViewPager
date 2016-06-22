package com.dalong.slidingballviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.dalong.zwlviewpager.SlidingBallPageTransformer;
import com.dalong.zwlviewpager.SlidingBallViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mViewPager_Bg;

    private SlidingBallViewPager mViewPager;

    public List<Item> mlist=new ArrayList<>();

    private int[] mImgs ={R.mipmap.meinv1,R.mipmap.meinv2,R.mipmap.meinv3,R.mipmap.meinv4,R.mipmap.meinv5};
    private MyViewpagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }


    private void initListener() {
        /**
         * 将Viewpager所在容器的事件分发交给ViewPager
         */
        mViewPager_Bg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
    }

    private void initData() {
        for (int i=0;i<mImgs.length;i++){
            Item item=new Item();
            item.setImg(mImgs[i]);
            item.setName(i+"km");
            mlist.add(item);
        }

    }

    private void initView() {
        mViewPager_Bg=(RelativeLayout)findViewById(R.id.mViewPager_Bg);
        mViewPager=(SlidingBallViewPager)findViewById(R.id.mViewPager);
        adapter=new MyViewpagerAdapter(this,mlist);
        mViewPager.setAdapter(adapter);
        //设置缓存数为展示的数目
        mViewPager.setOffscreenPageLimit(mImgs.length);
        mViewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        //设置切换动画
        mViewPager.setPageTransformer(true, new SlidingBallPageTransformer());
        adapter.setOnItemClickListener(new MyViewpagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mViewPager.setCurrentItem(position);
            }
        });
    }
}
