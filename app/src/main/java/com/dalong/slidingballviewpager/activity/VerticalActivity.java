package com.dalong.slidingballviewpager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.dalong.slidingballviewpager.R;
import com.dalong.slidingballviewpager.ShadowTransformer;
import com.dalong.slidingballviewpager.adapter.CardPagerAdapter;
import com.dalong.slidingballviewpager.entity.Item;
import com.dalong.zwlviewpager.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class VerticalActivity extends AppCompatActivity {

    private List<Item> mlist=new ArrayList<>();
    private int[] mImgs ={R.mipmap.meinv1,R.mipmap.meinv2,R.mipmap.meinv3,R.mipmap.meinv4,R.mipmap.meinv5};

    private SeekBar alplaSeekBar;
    private SeekBar scaleSeekBar;
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    private float mAlplaNum;
    private float mScaleNum;
    private CheckBox isAlpla;
    private CheckBox isScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);
        initData();
        initView();
        initLintener();
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


    private void initView() {
        alplaSeekBar=(SeekBar)findViewById(R.id.alpla_num);
        scaleSeekBar=(SeekBar)findViewById(R.id.scale_num);
        mViewPager=(ViewPager)findViewById(R.id.viewPager);
        isAlpla=(CheckBox)findViewById(R.id.is_alpla);
        isScale=(CheckBox)findViewById(R.id.is_scale);
        mCardAdapter = new CardPagerAdapter(this,mlist);
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(4);
    }



    private void initLintener() {
        isAlpla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCardShadowTransformer.setCanAlpha(b);
            }
        });
        isScale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCardShadowTransformer.setCanScale(b);
            }
        });
        mCardAdapter.setOnItemClickListener(new CardPagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mViewPager.setCurrentItem(position);
            }
        });
        alplaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mAlplaNum=1- (float) ((i/100f)*0.6+0.1f);
                mCardShadowTransformer.setAlpha(mAlplaNum,isAlpla.isChecked());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        scaleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mScaleNum= (float) ((i/100f)*0.5);
                mCardShadowTransformer.setScale(mScaleNum,isScale.isChecked());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
