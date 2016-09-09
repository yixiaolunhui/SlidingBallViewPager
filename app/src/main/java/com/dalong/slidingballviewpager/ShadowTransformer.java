package com.dalong.slidingballviewpager;

import android.support.v7.widget.CardView;
import android.view.View;

import com.dalong.slidingballviewpager.adapter.CardAdapter;
import com.dalong.zwlviewpager.ViewPager;


public class ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private  float mScale = 0f;//缩放比例  这里缩放是中间比两边大0.2  也就是说两边是你设定的值  中间缩放到了1.2
    private  float mAlpha = 0f;//左右透明度
    private ViewPager mViewPager;
    private CardAdapter mAdapter;
    private float mLastOffset;
    private boolean mScalingEnabled;
    private boolean mAlphaEnabled;

    public ShadowTransformer(ViewPager viewPager, CardAdapter adapter) {
        mViewPager = viewPager;
        mAdapter = adapter;
        viewPager.setOnPageChangeListener(this);
    }

    /**
     * 设置缩放
     * @param mScale
     * @param isScale
     */
    public void setScale(float mScale,boolean isScale){
        this.mScale=mScale;
        setCanScale(isScale);
    }

    /**
     * 设置透明度
     * @param mAlpha
     * @param isAlpha
     */
    public void setAlpha(float mAlpha,boolean isAlpha){
        this.mAlpha=mAlpha;
        setCanAlpha(isAlpha);
    }


    /**
     * 设置是否缩放
     * @param isCanScale
     */
    public void setCanScale(boolean isCanScale) {
        if (!isCanScale) {
            // 不设置缩放的时候就把中间的选择的设置为1
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1);
                currentCard.animate().scaleX(1);
            }
        }else{
            // 设置缩放就设置当前选择的缩放为1+缩放值
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1+mScale);
                currentCard.animate().scaleX(1+mScale);
            }
        }

        mScalingEnabled = isCanScale;
    }

    /**
     * 设置是否透明度
     * @param isCanAlpha
     */
    public void setCanAlpha(boolean isCanAlpha) {
        if (!isCanAlpha) {
            // 不设置透明度的时候吧所有的view都设置为1 不透明
            for(int i=0;i<mAdapter.getCount();i++){
                CardView cardView=mAdapter.getCardViewAt(i);
                if (cardView != null) {
                    cardView.setAlpha(1f);
                    cardView.findViewById(R.id.item_btn).setAlpha(1f);
                }
            }

        }else {
            // 当设置了透明度 把中间的选择的view设置为1,其他的设置指定的透明度
            for(int i=0;i<mAdapter.getCount();i++){
                CardView cardView=mAdapter.getCardViewAt(i);
                if(i==mViewPager.getCurrentItem()){
                    if (cardView != null) {
                        cardView.setAlpha(1f);
                        cardView.findViewById(R.id.item_btn).setAlpha(1f);
                    }
                }else{
                    if (cardView != null) {
                        cardView.setAlpha(mAlpha);
                        cardView.findViewById(R.id.item_btn).setAlpha(0f);
                    }
                }
            }
        }
        mAlphaEnabled = isCanAlpha;
    }


    @Override
    public void transformPage(View page, float position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realCurrentPosition;
        int nextPosition;
        float baseElevation = mAdapter.getBaseElevation();
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }
        // Avoid crash on overscroll
        if (nextPosition > mAdapter.getCount() - 1
                || realCurrentPosition > mAdapter.getCount() - 1) {
            return;
        }

        CardView currentCard = mAdapter.getCardViewAt(realCurrentPosition);
        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((float) (1 + mScale * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + mScale * (1 - realOffset)));
            }
            if(mAlphaEnabled){
                currentCard.setAlpha(1-realOffset+mAlpha);
                currentCard.findViewById(R.id.item_btn).setAlpha(1-realOffset);
            }
            currentCard.setCardElevation((baseElevation + baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }

        CardView nextCard = mAdapter.getCardViewAt(nextPosition);

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.setScaleX((float) (1 + mScale * (realOffset)));
                nextCard.setScaleY((float) (1+ mScale * (realOffset)));
            }
            if(mAlphaEnabled){
                nextCard.setAlpha(realOffset+mAlpha);
                nextCard.findViewById(R.id.item_btn).setAlpha(realOffset);
            }
            nextCard.setCardElevation((baseElevation + baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }
        mLastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
