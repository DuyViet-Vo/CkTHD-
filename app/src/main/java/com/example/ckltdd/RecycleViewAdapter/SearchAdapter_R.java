package com.example.ckltdd.RecycleViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ckltdd.ChiTietActivity;
import com.example.ckltdd.HandleLoadEmtpy;
import com.example.ckltdd.Khoa;
import com.example.ckltdd.MainActivity;
import com.example.ckltdd.R;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.SinhVien;
import com.example.ckltdd.sinhVienAdapter;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAdapter_R extends RecyclerView.Adapter<SearchAdapter_R.SearchHolder> {
    private List<SinhVien> listSV;
    private Context context;

    public SearchAdapter_R(List<SinhVien> listSV) {
        this.listSV = listSV;
    }

    public SearchAdapter_R(List<SinhVien> listSV, Context context) {
        this.listSV = listSV;
        this.context = context;
    }

    public List<SinhVien> getListSV() {
        return listSV;
    }

    public void setListSV(List<SinhVien> listSV) {
        this.listSV = listSV;
    }

    @NonNull
    @Override
    public SearchAdapter_R.SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent,false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter_R.SearchHolder holder, int position) {
        SinhVien sinhVien = listSV.get(position);
        if(sinhVien == null)
            return;
        holder.tv_ten.setText(sinhVien.getHoTen());
        holder.tv_msv.setText(sinhVien.getId());
        holder.tv_lop_nganh.setText(sinhVien.getTenLop() + " - " + sinhVien.getTenNganh());
        holder.relativeLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, ChiTietActivity.class);
            intent.putExtra("idSV", sinhVien.getId());
            context.startActivity(intent);
        });

        if (sinhVien.getAnhDaiDien() == null || sinhVien.getAnhDaiDien().isEmpty()) {
            holder.avt.setImageResource(sinhVien.getGioiTinh() == 1 ? R.drawable.avatar_nam2 : R.drawable.avt_nu);
        } else {
            Glide.with(context).load("https://app-quanlysv.herokuapp.com/img/" + sinhVien.getAnhDaiDien()).into(holder.avt);
        }
        holder.avt_gifLoad.getLayoutParams().height = 0;

    }

    @Override
    public int getItemCount() {
        if(listSV != null) {
            return listSV.size();
        }
        return 0;
    }

    public class SearchHolder extends RecyclerView.ViewHolder {

        private TextView tv_ten, tv_msv, tv_lop_nganh;
        private RelativeLayout relativeLayout;
        private ImageView avt;
        private GifImageView avt_gifLoad;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten = itemView.findViewById(R.id.txtten);
            tv_msv = itemView.findViewById(R.id.txtmasv);
            tv_lop_nganh =  itemView.findViewById(R.id.txtlopNganh);
            relativeLayout = itemView.findViewById(R.id.item_sv);
            avt = itemView.findViewById(R.id.avt);
            avt_gifLoad = itemView.findViewById(R.id.avt_gifLoad);
        }
    }


}
