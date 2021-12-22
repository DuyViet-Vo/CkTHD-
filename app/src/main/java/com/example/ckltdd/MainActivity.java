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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ckltdd.RecycleViewAdapter.KhoaAdapter_R;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListView listViewsinhvien;
    private List<SinhVien> sinhVienArrayList;
    private sinhVienAdapter svAdapter;
    private Spinner danhsachNganh,danhsachKhoa;
    private Button btnHuy;
    private ImageButton button_loc;
    private FloatingActionButton fab_them;
    private APIServices mAPIService;
    private TextView txtLop;

    private KhoaAdapter_R khoaAdapter_r;
    private RecyclerView rv_khoa;

    private Dialog locDialog;

    private AutoCompleteTextView lopSpinner, nganhSpinner;
    private HandleLoadEmtpy handleLoadEmtpy;

    public static int khoaId = 0, nganhId = 0, lopId = 0;
    public static String nganhLoc, lopLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setColorStatusBar();
        mAPIService = APIUtils.getAPIService();
        handleLoadEmtpy = new HandleLoadEmtpy(findViewById(R.id.sv_load), findViewById(R.id.lvsinhvien),findViewById(R.id.sv_0));
        txtLop = findViewById(R.id.txtlop);

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

        nganhSpinner = locDialog.findViewById(R.id.nganhSpinner);
        lopSpinner = locDialog.findViewById(R.id.lopSpinner);

        nganhSpinner.setText(nganhLoc);
        lopSpinner.setText(lopLoc);

        LoadDSNganh(nganhSpinner);
        LoadDSLop(lopSpinner);

        Button huyBtn = locDialog.findViewById(R.id.btnHuy),
                locBtn = locDialog.findViewById(R.id.btnLoc);

        huyBtn.setOnClickListener(view -> {
            locDialog.cancel();
        });

        locBtn.setOnClickListener(view -> {
            LoadStudentsByClassId(khoaId, nganhId, lopId);
            locDialog.cancel();


            if (lopId != 0)  {
                txtLop.setText(lopLoc);
                return;
            }

            if (nganhId != 0) {
                txtLop.setText(nganhLoc);
                return;
            }

            if (khoaId != 0) {
                txtLop.setText("Tất cả");
                return;
            }

            txtLop.setText("");
        });
    }

    private void LoadStudentsByClassId(int khoaId, int nganhId, int lopId) {
        handleLoadEmtpy.empty(1);
        handleLoadEmtpy.HandleLoadAnimation(true);
        Call<List<SinhVien>> call = mAPIService.LoadStudentsByClassId(khoaId, nganhId, lopId);
        call.enqueue(new Callback<List<SinhVien>>() {
            @Override
            public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                List<SinhVien> list = response.body();
                svAdapter.setSinhVienList(list);
                svAdapter.notifyDataSetChanged();
                handleLoadEmtpy.HandleLoadAnimation(false);
                handleLoadEmtpy.empty(list.size());

                System.out.println(khoaId + "__" +nganhId + "__" +lopId);
                System.out.println(list.size());
            }

            @Override
            public void onFailure(Call<List<SinhVien>> call, Throwable t) {

            }
        });
    }

    private void LoadDSLop(AutoCompleteTextView spinner) {
        if(nganhId != 0) {
            LoadDSLopByNganhId(nganhId);
            return;
        }

        if (khoaId != 0) {
            LoadDSLopByKhoaId(khoaId);
            return;
        }

        Call<ArrayList<Lop>> call = mAPIService.LoadDSLop();
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();
                list.add(0, new Lop( 0,"Tất cả"));

                ArrayAdapter<Lop> lopAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.item_boloc, list);
                spinner.setAdapter(lopAdapter);
                spinner.setOnItemClickListener((adapterView, view, i, l) -> {
                    Lop lop = (Lop) adapterView.getItemAtPosition(i);
                    lopId = lop.getId();
                    lopLoc = lop.getTenLop();
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {

            }
        });
    }

    private void LoadDSLopByKhoaId(int khoaId) {
        Call<ArrayList<Lop>> call = mAPIService.LoadDSLopByKhoaId(khoaId);
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();
                if(list.size() == 0) lopSpinner.setText("");
                else list.add(0, new Lop( 0,"Tất cả"));

                ArrayAdapter<Lop> lopAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.item_boloc, list);
                lopSpinner.setAdapter(lopAdapter);
                lopSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
                    Lop lop = (Lop) adapterView.getItemAtPosition(i);
                    lopId = lop.getId();
                    lopLoc = lop.getTenLop();
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void LoadDSLopByNganhId(int id) {
        Call<ArrayList<Lop>> call = mAPIService.LoadDSLopByNganhId(id);
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();
                if(list.size() == 0) lopSpinner.setText("");
                else list.add(0, new Lop( 0,"Tất cả"));

                ArrayAdapter<Lop> lopAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.item_boloc, list);
                lopSpinner.setAdapter(lopAdapter);
                lopSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
                    Lop lop = (Lop) adapterView.getItemAtPosition(i);
                    lopId = lop.getId();
                    lopLoc = lop.getTenLop();
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void LoadDSNganh(AutoCompleteTextView spinner) {
        if(khoaId != 0) {
            LoadDSNGanhByKhoaId(khoaId);
            return;
        }

        Call<ArrayList<Nganh>> call = mAPIService.LoadDSNganh();
        call.enqueue(new Callback<ArrayList<Nganh>>() {
            @Override
            public void onResponse(Call<ArrayList<Nganh>> call, Response<ArrayList<Nganh>> response) {
                ArrayList<Nganh> list = response.body();
                list.add(0, new Nganh( 0,"Tất cả"));

                ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.item_boloc, list);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemClickListener((adapterView, view, i, l) -> {
                    Nganh n = (Nganh) adapterView.getItemAtPosition(i);
                    LoadDSLopByNganhId(n.getId());
                    nganhId = n.getId();
                    nganhLoc = n.getTenNganh();
                    lopId = 0;
                    lopLoc = "";
                    lopSpinner.setText("");
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Nganh>> call, Throwable t) {

            }
        });
    }

    private void LoadDSNGanhByKhoaId(int khoaId) {
        Call<ArrayList<Nganh>> call = mAPIService.LoadDSNganhByKhoaId(khoaId);
        call.enqueue(new Callback<ArrayList<Nganh>>() {
            @Override
            public void onResponse(Call<ArrayList<Nganh>> call, Response<ArrayList<Nganh>> response) {
                ArrayList<Nganh> list = response.body();
                list.add(0, new Nganh( 0,"Tất cả"));

                ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.item_boloc, list);
                nganhSpinner.setAdapter(arrayAdapter);
                nganhSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
                    Nganh n = (Nganh) adapterView.getItemAtPosition(i);
                    LoadDSLopByNganhId(n.getId());
                    nganhId = n.getId();
                    nganhLoc = n.getTenNganh();
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
                khoaAdapter_r.setSvAdapter(svAdapter);
                khoaAdapter_r.setHandleLoadEmtpy(handleLoadEmtpy);
                khoaAdapter_r.setTxtLop(txtLop);
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

                svAdapter = new sinhVienAdapter(MainActivity.this,R.layout.item_sinhvien, sinhVienArrayList);
                listViewsinhvien.setAdapter(svAdapter);
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
}