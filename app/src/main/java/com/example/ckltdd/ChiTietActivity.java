package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietActivity extends AppCompatActivity {
    private APIServices mAPIService;
    TextView ten, msv, lop, nganh, gioiTinh, ngaySinh, CCCD, sdt, email, diaChi;
    ImageView ct_avt;
    RelativeLayout ct_content;
    ImageButton ct_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        setColorStatusBar();
        mAPIService = APIUtils.getAPIService();

        init();
    }

    private void init() {
        Intent intent = getIntent();
        String idSV = intent.getStringExtra("idSV");

        ten = findViewById(R.id.ct_ten);
        msv = findViewById(R.id.ct_msv);
        lop = findViewById(R.id.ct_lop);
        nganh = findViewById(R.id.ct_nganh);
        gioiTinh = findViewById(R.id.ct_gioiTinh);
        ngaySinh = findViewById(R.id.ct_ngaySinh);
        CCCD = findViewById(R.id.ct_cccd);
        sdt = findViewById(R.id.ct_sdt);
        email = findViewById(R.id.ct_email);
        diaChi = findViewById(R.id.ct_diaChi);
        ct_avt = findViewById(R.id.ct_avt);
        ct_content = findViewById(R.id.ct_content);
        ct_back = findViewById(R.id.ct_back);

        ct_back.setOnClickListener(view -> {
            onBackPressed();
        });

       StudentRequest(idSV);
    }

    private void StudentRequest(String idSV) {
        Call<SinhVien> call = mAPIService.getStudentById(idSV);
        call.enqueue(new Callback<SinhVien>() {
            @Override
            public void onResponse(Call<SinhVien> call, Response<SinhVien> response) {
                SinhVien sinhVien = response.body();

                ten.setText(sinhVien.getHoTen());
                msv.setText(sinhVien.getId());
                lop.setText(sinhVien.getTenLop());
                nganh.setText(sinhVien.getTenNganh());
                gioiTinh.setText(sinhVien.getGioiTinh() == 1 ? "Nam" : "Nữ");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    ngaySinh.setText(sdf2.format(sdf.parse(sinhVien.getNgaySinh())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                CCCD.setText(sinhVien.getCCCD());
                sdt.setText(sinhVien.getSdt());
                email.setText(sinhVien.getEmail());
                diaChi.setText(sinhVien.getDiaChi());
                if (sinhVien.getAnhDaiDien() == null || sinhVien.getAnhDaiDien().isEmpty()) {
                    ct_avt.setImageResource(sinhVien.getGioiTinh() == 1 ? R.drawable.avatar_nam2 : R.drawable.avt_nu);
                } else {
                    Glide.with(ChiTietActivity.this).load("https://app-quanlysv.herokuapp.com/img/" + sinhVien.getAnhDaiDien()).into(ct_avt);
                }
                ct_content.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            }

            @Override
            public void onFailure(Call<SinhVien> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(ChiTietActivity.this,R.color.white));
    }
}