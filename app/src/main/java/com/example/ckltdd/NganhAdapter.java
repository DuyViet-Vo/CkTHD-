package com.example.ckltdd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NganhAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Nganh> nganhList;

    public NganhAdapter(Context context, int layout, List<Nganh> nganhList) {
        this.context = context;
        this.layout = layout;
        this.nganhList = nganhList;
    }

    @Override
    public int getCount() {
        return nganhList.size();
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

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(layout,null);
        TextView txtNganh=(TextView) convertView.findViewById(R.id.txttennganh);
        Nganh nganh = nganhList.get(position);
        txtNganh.setText(nganh.getTenNganh());

        return convertView;
    }
}
