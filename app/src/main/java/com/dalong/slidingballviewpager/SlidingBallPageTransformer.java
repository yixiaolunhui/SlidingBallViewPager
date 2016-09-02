package com.dalong.slidingballviewpager;

import android.view.View;

import com.dalong.zwlviewpager.ViewPager;

/**
 *
 * Created by zwl on 16/6/22.
 */

public class SlidingBallPageTransformer implements ViewPager.PageTransformer {

    private  float mScale = 0.5f;//缩放比例

    private  float mAlpha = 0.7f;//左右透明度

    /**
     * 构造方法
     */
    public SlidingBallPageTransformer(){}

    /**
     * 构造方法
     * @param mScale 缩放比例
     * @param mAlpha 透明度
     */
    public SlidingBallPageTransformer(float mScale,float mAlpha){
        this.mAlpha=mAlpha;
        this.mScale=mScale;
    }

    @Override
    public void transformPage(View page, float position) {
        position-=1;
        if (position < -1) { // [-Infinity,-1) 此范围是停止滑动左边屏幕的部分
            page.setAlpha(mAlpha);
            page.setScaleX(mScale);
            page.setScaleY(mScale);
            page.findViewById(R.id.item_btn).setAlpha(0);
        } else if (position <= 1) { // [-1,1]  滑动过程中的设置view的缩放和通明度
            if (position < 0) {
                page.setAlpha(mAlpha);
                page.setScaleX(mScale);
                page.setScaleY(mScale);
                //设置按钮渐变显示
                page.findViewById(R.id.item_btn).setAlpha(0);
            }else{
                page.setAlpha(1);
                page.setScaleX(1);
                page.setScaleY(1);
                //设置按钮渐变显示
                page.findViewById(R.id.item_btn).setAlpha(1);
            }

        } else { // (1,+Infinity] 这个范围是停止滑动的右面部分
            page.setAlpha(mAlpha);
            page.setScaleX(mScale);
            page.setScaleY(mScale);
            page.findViewById(R.id.item_btn).setAlpha(0);
        }
    }
}
