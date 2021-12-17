package com.example.ckltdd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class sinhvienAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<sinhvien> sinhvienList;

    public sinhvienAdapter(Context context, int layout, List<sinhvien> sinhvienList) {
        this.context = context;
        this.layout = layout;
        this.sinhvienList = sinhvienList;
    }

    @Override
    public int getCount() {
        return sinhvienList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        //anh xa view
        TextView txttensv=(TextView) convertView.findViewById(R.id.txtten);
        TextView txtmasinhvien=(TextView) convertView.findViewById(R.id.txtmasv);
        ImageView imagehinhcanhan = (ImageView) convertView.findViewById(R.id.imagehinh);
        //gán giá trị
        sinhvien sinhvien = sinhvienList.get(position);
        txttensv.setText(sinhvien.getTensv());
        txtmasinhvien.setText(sinhvien.getMasv());
        imagehinhcanhan.setImageResource(sinhvien.getHinh());

        return convertView;
    }
}
