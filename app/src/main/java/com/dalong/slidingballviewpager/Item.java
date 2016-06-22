package com.dalong.slidingballviewpager;

import java.io.Serializable;

/**
 * Created by zhouweilong on 15/12/14.
 */
public class Item  implements Serializable{
    private int position;
    private int img;
    private String name;
    private boolean isSelected;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
