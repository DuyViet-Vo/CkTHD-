package com.example.ckltdd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class KhoaAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Khoa> khoaList;

    public KhoaAdapter(Context context, int layout, List<Khoa> khoaList) {
        this.context = context;
        this.layout = layout;
        this.khoaList = khoaList;
    }

    @Override
    public int getCount() {
        return khoaList.size();
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
        //ánh xạ view
        TextView txtKhoa= (TextView) convertView.findViewById(R.id.txttenkhoa);
        //gán giá trị
        Khoa khoa= khoaList.get(position);
        txtKhoa.setText(khoa.getTenkhoa());

        return convertView;
    }
}
