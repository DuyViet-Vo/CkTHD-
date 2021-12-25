package com.example.ckltdd.RecycleViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ckltdd.Khoa;
import com.example.ckltdd.R;

import java.util.List;

public class QLKhoaAdapter_R extends RecyclerView.Adapter<QLKhoaAdapter_R.QLKhoaHolder>{
    private List<Khoa> listKhoa;

    public QLKhoaAdapter_R(List<Khoa> listKhoa) {
        this.listKhoa = listKhoa;
    }

    @NonNull
    @Override
    public QLKhoaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ql_khoa, parent,false);
        return new QLKhoaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLKhoaHolder holder, int position) {
        Khoa khoa = listKhoa.get(position);
        if(khoa == null)
            return;

        holder.tv_ten.setText(khoa.getTenkhoa());
    }

    @Override
    public int getItemCount() {
        if(listKhoa != null) {
            return listKhoa.size();
        }
        return 0;
    }

    public class QLKhoaHolder extends RecyclerView.ViewHolder {
        private TextView tv_ten;
        private ImageButton btnSua, btnXoa;

        public QLKhoaHolder(@NonNull View itemView) {
            super(itemView);

            tv_ten = itemView.findViewById(R.id.tv_ten);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
