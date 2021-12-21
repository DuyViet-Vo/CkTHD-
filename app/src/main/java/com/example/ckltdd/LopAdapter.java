package com.example.ckltdd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class LopAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Lop> lopList;

    public LopAdapter(Context context, int layout, List<Lop> lopList) {
        this.context = context;
        this.layout = layout;
        this.lopList = lopList;
    }

    @Override
    public int getCount() {
        return lopList.size();
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
        convertView = inflater.inflate(layout,null);
        TextView txtlop = (TextView) convertView.findViewById(R.id.txttenlop);
        Lop lop = lopList.get(position);
        txtlop.setText(lop.getTenLop());
        return convertView;
    }
}
