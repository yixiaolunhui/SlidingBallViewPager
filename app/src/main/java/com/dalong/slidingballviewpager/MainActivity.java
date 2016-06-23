package com.dalong.slidingballviewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
        /**
         * viewpager的页面切换回调
         */
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (mViewPager_Bg != null) {
                    mViewPager_Bg.invalidate();//viewpagr滑动的父布局刷新   部分手机滑动bug
                }
            }
            @Override
            public void onPageSelected(int i) {}
            @Override
            public void onPageScrollStateChanged(int i) {}
        });
        /**
         * 点击viewpager点击回调
         */
        adapter.setOnItemClickListener(new MyViewpagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if(position==mViewPager.getCurrentItem()){//中间位置
                    Toast.makeText(MainActivity.this,"你和她相隔:"+mlist.get(position).getName(),Toast.LENGTH_LONG).show();
                    //do something....
                }else{//两边位置  点击切换到中间
                    mViewPager.setCurrentItem(position);
                }

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i=0;i<mImgs.length;i++){
            Item item=new Item();
            item.setImg(mImgs[i]);
            item.setName(i+"km");
            mlist.add(item);
        }

    }

    /**
     *  初始化view
     */
    private void initView() {
        mViewPager_Bg=(RelativeLayout)findViewById(R.id.mViewPager_Bg);
        mViewPager=(SlidingBallViewPager)findViewById(R.id.mViewPager);
        adapter=new MyViewpagerAdapter(this,mlist);
        mViewPager.setAdapter(adapter);
        //设置缓存数为展示的数目
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        //设置切换动画
        mViewPager.setPageTransformer(true, new SlidingBallPageTransformer(0.7f,0.6f));
    }
}
