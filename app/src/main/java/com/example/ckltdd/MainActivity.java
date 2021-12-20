package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ckltdd.RecycleViewAdapter.KhoaAdapter_R;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ListView listViewsinhvien;
    List<SinhVien> sinhVienArrayList;
    sinhvienAdapter adapter;
    Spinner danhsachNganh,danhsachKhoa;
    Button btnHuy,btnLoc;
    FloatingActionButton fab_them;
    APIServices mAPIService;

    KhoaAdapter_R khoaAdapter_r;
    RecyclerView rv_khoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setColorStatusBar();
        mAPIService = APIUtils.getAPIService();

        //them
        fab_them = (FloatingActionButton) findViewById(R.id.fAddBtn) ;
        fab_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ThemSua.class);
                startActivity(intent);
            }
        });


        LoadStudents();

    }

    private void LoadDSKhoa() {
        Call<List<Khoa>> call = mAPIService.LoadDSKhoa();
        call.enqueue(new Callback<List<Khoa>>() {
            @Override
            public void onResponse(Call<List<Khoa>> call, Response<List<Khoa>> response) {
                List<Khoa> list = response.body();
                list.add(0, new Khoa( 0,"Tất cả"));
                System.out.println(list.size());
                for (Khoa khoa : list) {
                    System.out.println(khoa);
                }
                khoaAdapter_r = new KhoaAdapter_R(list);
                khoaAdapter_r.setmAPIService(mAPIService);
                khoaAdapter_r.setListViewsinhvien(listViewsinhvien);
                khoaAdapter_r.setSvAdapter(adapter);
                rv_khoa = (RecyclerView) findViewById(R.id.list_khoa);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rv_khoa.setLayoutManager(linearLayoutManager);
                rv_khoa.setAdapter(khoaAdapter_r);
            }

            @Override
            public void onFailure(Call<List<Khoa>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });

    }

    private void LoadStudents(){
        listViewsinhvien = (ListView) findViewById(R.id.lvsinhvien);
        sinhVienArrayList = new ArrayList<>();

        Call<List<SinhVien>> call = mAPIService.LoadStudents();
        call.enqueue(new Callback<List<SinhVien>>() {
            @Override
            public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                sinhVienArrayList = response.body();

                adapter = new sinhvienAdapter(MainActivity.this,R.layout.item_sinhvien, sinhVienArrayList);
                listViewsinhvien.setAdapter(adapter);
                listViewsinhvien.setTranscriptMode(0);
                LoadDSKhoa();
            }

            @Override
            public void onFailure(Call<List<SinhVien>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.white));
    }

    private void getStudents() {

    }
}