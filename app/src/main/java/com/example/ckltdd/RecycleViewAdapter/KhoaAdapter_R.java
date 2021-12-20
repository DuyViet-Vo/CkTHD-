package com.example.ckltdd.RecycleViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ckltdd.Khoa;
import com.example.ckltdd.R;

import java.util.List;

public class KhoaAdapter_R extends RecyclerView.Adapter<KhoaAdapter_R.KhoaHolder> {
    private List<Khoa> listKhoa;

    public KhoaAdapter_R(List<Khoa> listKhoa) {
        this.listKhoa = listKhoa;
    }

    @NonNull
    @Override
    public KhoaAdapter_R.KhoaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khoa, parent,false);
        return new KhoaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhoaAdapter_R.KhoaHolder holder, int position) {
        Khoa khoa = listKhoa.get(position);
        if(khoa == null)
            return;
        holder.tenKhoa.setText(khoa.getTenkhoa());
    }

    @Override
    public int getItemCount() {
        if(listKhoa != null) {
            return listKhoa.size();
        }
        return 0;
    }

    public class KhoaHolder extends RecyclerView.ViewHolder {
        private Button tenKhoa;
        public KhoaHolder(@NonNull View itemView) {
            super(itemView);
            tenKhoa = itemView.findViewById(R.id.khoaBtn);
        }
    }
}
