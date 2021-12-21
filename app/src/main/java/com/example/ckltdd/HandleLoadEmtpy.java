package com.example.ckltdd;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class HandleLoadEmtpy {
    private GifImageView load;
    private ListView listView;
    private TextView empty;

    public HandleLoadEmtpy(GifImageView load, ListView listView, TextView empty) {
        this.load = load;
        this.listView = listView;
        this.empty = empty;
    }

    public GifImageView getLoad() {
        return load;
    }

    public void setLoad(GifImageView load) {
        this.load = load;
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void HandleLoadAnimation(boolean isLoad) {
        if (isLoad)  {
            load.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            listView.getLayoutParams().height = 0;
        }
        else {
            load.getLayoutParams().height = 0;
            listView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }
    }

    public void empty(int soLuong) {
        if (soLuong > 0) empty.getLayoutParams().height = 0;
        else empty.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
