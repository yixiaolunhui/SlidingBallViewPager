package com.dalong.slidingballviewpager;

import android.view.View;

import com.dalong.zwlviewpager.ViewPager;

/**
 *
 * Created by zwl on 16/6/22.
 */

public class SlidingBallPageTransformer implements ViewPager.PageTransformer {

    private  float mScale = 0.5f;//缩放比例

    private  float mAlpha = 0.5f;//左右透明度

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
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        page.setPivotY(pageHeight / 2);
        page.setPivotX(pageWidth / 2);

        if (position < -1) { // [-Infinity,-1) 此范围是停止滑动左边屏幕的部分
            page.setAlpha(mAlpha);
            page.setScaleX(mScale);
            page.setScaleY(mScale);
            page.findViewById(R.id.item_btn).setAlpha(0);
        } else if (position <= 1) { // [-1,1]  滑动过程中的设置view的缩放和通明度
            if (position < 0) {
                float scaleFactor = (1 + position) * (1 - mScale) + mScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

                float factor = mAlpha + (1 - mAlpha) * (1 + position);
                page.setAlpha(factor);

                //设置按钮渐变显示
                page.findViewById(R.id.item_btn).setAlpha(factor-mAlpha);
            }else{
                float scaleFactor = (1 - position) * (1 - mScale) + mScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

                float factor = mAlpha + (1 - mAlpha) * (1 - position);
                page.setAlpha(factor);
                //设置按钮渐变显示
                page.findViewById(R.id.item_btn).setAlpha(factor);
            }

        } else { // (1,+Infinity] 这个范围是停止滑动的右面部分
            page.setAlpha(mAlpha);
            page.setScaleX(mScale);
            page.setScaleY(mScale);
            page.findViewById(R.id.item_btn).setAlpha(0);
        }
    }
}
