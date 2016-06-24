package com.dalong.slidingballviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dalong.zwlviewpager.PagerAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zwl on 16/6/22.
 */

public class MyViewpagerAdapter extends PagerAdapter {

    public Context mContext;

    public List<Item> list;

    public MyViewpagerAdapter(Context context, List<Item> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final Item item=list.get(position);
        final View view= LayoutInflater.from(mContext).inflate(R.layout.item_fancycoverflow,null);
        CircleImageView  mIcon=(CircleImageView)view.findViewById(R.id.profile_image);
        TextView mName= (TextView) view.findViewById(R.id.name);
        Button mBtn= (Button) view.findViewById(R.id.item_btn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null)mOnItemClickListener.onClick(position);
            }
        });
        mIcon.setImageResource(item.getImg());
        mName.setText(item.getName());
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, final int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }
    public interface OnItemClickListener{
        void onClick(int position);
    }
}
