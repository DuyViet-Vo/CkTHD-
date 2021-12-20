package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listViewsinhvien;
    ArrayList<sinhvien> sinhvienArrayList;
    sinhvienAdapter adapter;
    Spinner danhsachNganh,danhsachKhoa;
    Button btnHuy,btnLoc;
    FloatingActionButton fab_them;
    ListView listViewSV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setColorStatusBar();
        //dialog


        //them
        fab_them = (FloatingActionButton) findViewById(R.id.fAddBtn) ;
        fab_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ThemSua.class);
                startActivity(intent);
            }
        });
////intenr listview bị lỗi
//        listViewSV = (ListView)findViewById(R.id.lvsinhvien) ;
//        listViewSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this,ChiTietActivity.class);
//                startActivity(intent);
//            }
//        });
        AnhXa();
        adapter = new sinhvienAdapter(this,R.layout.list_sinh_vien,sinhvienArrayList);
        listViewsinhvien.setAdapter(adapter);
        listViewsinhvien.setTranscriptMode(0);
    }

    private void AnhXa(){
        listViewsinhvien = (ListView) findViewById(R.id.lvsinhvien);
        sinhvienArrayList   = new ArrayList<>();
//        sinhvienArrayList.add(new sinhvien("Đặng Văn Thiện","1911505310261", ));
//        sinhvienArrayList.add(new sinhvien("Đặng Văn Thiện","1911505310261",R.drawable.anhnguoidung));
//        sinhvienArrayList.add(new sinhvien("Đặng Văn Thiện","1911505310261",R.drawable.anhnguoidung));
//        sinhvienArrayList.add(new sinhvien("Đặng Văn Thiện","1911505310261",R.drawable.anhnguoidung));
//        sinhvienArrayList.add(new sinhvien("Đặng Văn Thiện","1911505310261",R.drawable.anhnguoidung));
//        sinhvienArrayList.add(new sinhvien("Đặng Văn Thiện","1911505310261",R.drawable.anhnguoidung));
//        sinhvienArrayList.add(new sinhvien("Đặng Văn Thiện","1911505310261",R.drawable.anhnguoidung));
//        sinhvienArrayList.add(new sinhvien("Đặng Văn Thiện","1911505310261",R.drawable.anhnguoidung));
//        sinhvienArrayList.add(new sinhvien("Đặng Văn Thiện","1911505310261",R.drawable.anhnguoidung));
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