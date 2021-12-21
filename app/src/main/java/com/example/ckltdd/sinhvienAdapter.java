package com.example.ckltdd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class sinhvienAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<SinhVien> sinhVienList;

    public sinhvienAdapter(Context context, int layout, List<SinhVien> sinhVienList) {
        this.context = context;
        this.layout = layout;
        this.sinhVienList = sinhVienList;
    }

    public List<SinhVien> getSinhVienList() {
        return sinhVienList;
    }

    public void setSinhVienList(List<SinhVien> sinhVienList) {
        this.sinhVienList = sinhVienList;
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
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
        GifImageView avt_gifLoad = convertView.findViewById(R.id.avt_gifLoad);

        //gán giá trị
        SinhVien sinhvien = sinhVienList.get(position);
        txttensv.setText(sinhvien.getHoTen());
        txtmasinhvien.setText(sinhvien.getId());

        Glide.with(context).load("https://app-quanlysv.herokuapp.com/img/" + sinhvien.getAnhDaiDien()).into(imagehinhcanhan);
        avt_gifLoad.getLayoutParams().width = 0;

        return convertView;
    }


}
