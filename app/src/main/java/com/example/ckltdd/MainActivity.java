package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ckltdd.RecycleViewAdapter.KhoaAdapter_R;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
    Button btnHuy;
    ImageButton button_loc;
    FloatingActionButton fab_them;
    APIServices mAPIService;

    KhoaAdapter_R khoaAdapter_r;
    RecyclerView rv_khoa;

    Dialog locDialog;

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
        button_loc = findViewById(R.id.button_loc);
        button_loc.setOnClickListener(view -> {
            LocDialog();
        });


        LoadStudents();

    }

    private void LocDialog() {
        locDialog = new Dialog(this);
        locDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        locDialog.setContentView(R.layout.dialog_loc);

        Window window = locDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowsAttributes = window.getAttributes();
        windowsAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowsAttributes);

        locDialog.show();

        AutoCompleteTextView nganh = locDialog.findViewById(R.id.nganhSpinner);
        AutoCompleteTextView lop = locDialog.findViewById(R.id.lopSpinner);
        LoadDSNganh(nganh);
        LoadDSLop(lop);
    }

    private void LoadDSLop(AutoCompleteTextView spinner) {
        Call<ArrayList<Lop>> call = mAPIService.LoadDSLop();
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();
                list.add(0, new Lop( 0,"Tất cả"));

                ArrayAdapter<Lop> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.item_boloc, list);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Lop n = (Lop) adapterView.getItemAtPosition(i);
                        Toast.makeText(adapterView.getContext(), n.getId() + n.getTenLop(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {

            }
        });
    }

    private void LoadDSNganh(AutoCompleteTextView spinner) {
        Call<ArrayList<Nganh>> call = mAPIService.LoadDSNganh();
        call.enqueue(new Callback<ArrayList<Nganh>>() {
            @Override
            public void onResponse(Call<ArrayList<Nganh>> call, Response<ArrayList<Nganh>> response) {
                ArrayList<Nganh> list = response.body();
                list.add(0, new Nganh( 0,"Tất cả"));

                ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.item_boloc, list);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Nganh n = (Nganh) adapterView.getItemAtPosition(i);
                        Toast.makeText(adapterView.getContext(), n.getId() + n.getTenNganh(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Nganh>> call, Throwable t) {

            }
        });
    }

    private void LoadDSKhoa() {
        Call<List<Khoa>> call = mAPIService.LoadDSKhoa();
        call.enqueue(new Callback<List<Khoa>>() {
            @Override
            public void onResponse(Call<List<Khoa>> call, Response<List<Khoa>> response) {
                List<Khoa> list = response.body();
                list.add(0, new Khoa( 0,"Tất cả"));

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