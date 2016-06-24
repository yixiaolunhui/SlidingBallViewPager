package com.dalong.slidingballviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dalong.zwlviewpager.SlidingBallViewPager;
import com.dalong.zwlviewpager.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mHorizontalViewPager_Bg;

    private SlidingBallViewPager mHorizontalViewPager;

    private RelativeLayout mVerticalViewPager_Bg;

    private SlidingBallViewPager mVerticalViewPager;

    public List<Item> mlist=new ArrayList<>();

    private int[] mImgs ={R.mipmap.meinv1,R.mipmap.meinv2,R.mipmap.meinv3,R.mipmap.meinv4,R.mipmap.meinv5};

    private MyViewpagerAdapter adapter;
    private MyViewpagerAdapter adapter2;

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
        mHorizontalViewPager_Bg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mHorizontalViewPager.dispatchTouchEvent(event);
            }
        });
        mVerticalViewPager_Bg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mVerticalViewPager.dispatchTouchEvent(event);
            }
        });
        /**
         * viewpager的页面切换回调
         */
        mHorizontalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (mHorizontalViewPager_Bg != null) {
                    mHorizontalViewPager_Bg.invalidate();//viewpagr滑动的父布局刷新   部分手机滑动bug
                }
            }
            @Override
            public void onPageSelected(int i) {}
            @Override
            public void onPageScrollStateChanged(int i) {}
        });
        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (mVerticalViewPager_Bg != null) {
                    mVerticalViewPager_Bg.invalidate();//viewpagr滑动的父布局刷新   部分手机滑动bug
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
                if(position==mHorizontalViewPager.getCurrentItem()){//中间位置
                    Toast.makeText(MainActivity.this,"你和她相隔:"+mlist.get(position).getName(),Toast.LENGTH_LONG).show();
                    //do something....
                }else{//两边位置  点击切换到中间
                    mHorizontalViewPager.setCurrentItem(position);
                }

            }
        });
        adapter2.setOnItemClickListener(new MyViewpagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if(position==mVerticalViewPager.getCurrentItem()){//中间位置
                    Toast.makeText(MainActivity.this,"你和她相隔:"+mlist.get(position).getName(),Toast.LENGTH_LONG).show();
                    //do something....
                }else{//两边位置  点击切换到中间
                    mVerticalViewPager.setCurrentItem(position);
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
        mHorizontalViewPager_Bg=(RelativeLayout)findViewById(R.id.mHorizontalViewPager_Bg);
        mVerticalViewPager_Bg=(RelativeLayout)findViewById(R.id.mVerticalViewPager_Bg);
        mHorizontalViewPager=(SlidingBallViewPager)findViewById(R.id.mHorizontalViewPager);
        mVerticalViewPager=(SlidingBallViewPager)findViewById(R.id.mVerticalViewPager);

        adapter=new MyViewpagerAdapter(this,mlist);
        adapter2=new MyViewpagerAdapter(this,mlist);

        /**
         * 水平
         */
        mHorizontalViewPager.setAdapter(adapter);
        //设置缓存数为展示的数目
        mHorizontalViewPager.setOffscreenPageLimit(4);
        mHorizontalViewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        //设置切换动画
        mHorizontalViewPager.setPageTransformer(true, new SlidingBallPageTransformer(0.7f,0.6f));
        /**
         *  竖直
         */

        mVerticalViewPager.setAdapter(adapter2);
        //设置缓存数为展示的数目
        mVerticalViewPager.setOffscreenPageLimit(4);
        mVerticalViewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        //设置切换动画
        mVerticalViewPager.setPageTransformer(true, new SlidingBallPageTransformer2(0.5f,0.6f));

        int height=DensityUtil.dip2px(this,300);
        Log.v("000000","height:"+height);
        mVerticalViewPager.setPadding(0,height/3,0,height/3);

    }
}
