package com.example.ckltdd.RecycleViewAdapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ckltdd.Khoa;
import com.example.ckltdd.MainActivity;
import com.example.ckltdd.R;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.SinhVien;
import com.example.ckltdd.sinhvienAdapter;

import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhoaAdapter_R extends RecyclerView.Adapter<KhoaAdapter_R.KhoaHolder> {
    private List<Khoa> listKhoa;
    private int selected;
    private ListView listViewsinhvien;
    private APIServices mAPIService;
    private sinhvienAdapter svAdapter;

    public KhoaAdapter_R(List<Khoa> listKhoa) {
        this.listKhoa = listKhoa;
        selected = listKhoa.get(0).getId();
    }

    public ListView getListViewsinhvien() {
        return listViewsinhvien;
    }

    public void setListViewsinhvien(ListView listViewsinhvien) {
        this.listViewsinhvien = listViewsinhvien;
    }

    public sinhvienAdapter getSvAdapter() {
        return svAdapter;
    }

    public void setSvAdapter(sinhvienAdapter svAdapter) {
        this.svAdapter = svAdapter;
    }

    public APIServices getmAPIService() {
        return mAPIService;
    }

    public void setmAPIService(APIServices mAPIService) {
        this.mAPIService = mAPIService;
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

        if(selected == khoa.getId()) {
            holder.khoaBtn.setBackgroundColor(Color.parseColor("#FF77A7"));
            holder.khoaBtn.setTextSize(20);
            holder.khoaBtn.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.khoaBtn.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.khoaBtn.setTextSize(16);
            holder.khoaBtn.setTextColor(Color.parseColor("#90374D6B"));
        }

        holder.khoaBtn.setText(khoa.getTenkhoa());
        holder.khoaBtn.setOnClickListener(view -> {
            selected = khoa.getId();
            notifyDataSetChanged();

            changeListStudents(khoa.getId());
        });
    }

    private void changeListStudents(int idKhoa) {
        Call<List<SinhVien>> call = mAPIService.LoadStudentsByKhoaId(idKhoa);
        call.enqueue(new Callback<List<SinhVien>>() {
            @Override
            public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                List<SinhVien> list = response.body();
                svAdapter.setSinhVienList(list);
                svAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SinhVien>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listKhoa != null) {
            return listKhoa.size();
        }
        return 0;
    }

    public class KhoaHolder extends RecyclerView.ViewHolder {
        private Button khoaBtn;
        public KhoaHolder(@NonNull View itemView) {
            super(itemView);
            khoaBtn = itemView.findViewById(R.id.khoaBtn);
        }
    }
}
