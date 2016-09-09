package com.dalong.slidingballviewpager.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dalong.slidingballviewpager.R;
import com.dalong.slidingballviewpager.entity.Item;
import com.dalong.zwlviewpager.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    public Context mContext;

    public List<Item> mData;
    private float mBaseElevation;
    public CardPagerAdapter(Context context, List<Item> list) {
        mContext = context;
        mViews = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            mViews.add(null);
        }
        this.mData = list;
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_fancycoverflow, container, false);
        container.addView(view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        CircleImageView mIcon=(CircleImageView)view.findViewById(R.id.profile_image);
        TextView mName= (TextView) view.findViewById(R.id.name);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener!=null)mOnItemClickListener.onClick(position);
            }
        });
        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);

        final Item item=mData.get(position);
        mIcon.setImageResource(item.getImg());
        mName.setText(item.getName());
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }
    OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }
    public interface OnItemClickListener{
        void onClick(int position);
    }
}
