package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class QLKhoaActivity extends AppCompatActivity {
    ListView listViewKhoa;
    ArrayList<Khoa> arrayListKhoa;
    KhoaAdapter khoaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlkhoa);

        AnhXa();
        khoaAdapter = new KhoaAdapter(this,R.layout.list_khoa,arrayListKhoa);
        listViewKhoa.setAdapter(khoaAdapter);
    }
    private void AnhXa(){
        listViewKhoa = (ListView) findViewById(R.id.lvkhoa);
        arrayListKhoa = new ArrayList<>();
        arrayListKhoa.add(new Khoa("Điện-Điện Tử"));
        arrayListKhoa.add(new Khoa("Sư phạm Công nghiệp"));
        arrayListKhoa.add(new Khoa("Cơ khí"));
        arrayListKhoa.add(new Khoa("Công nghệ Hóa học–Môi trường"));
        arrayListKhoa.add(new Khoa("Kỹ thuật Xây dựng"));

    }
}