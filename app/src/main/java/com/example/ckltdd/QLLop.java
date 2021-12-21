package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class QLLop extends AppCompatActivity {
Spinner danhsachnganh;
    ListView listViewLop;
    ArrayList<Lop> LopArrayList;
    LopAdapter LopAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qllop);

        //spinner nganh
        danhsachnganh=(Spinner)findViewById(R.id.spinnernganh);
        ArrayList<String> arrayListnganh = new ArrayList<String>();
        arrayListnganh.add("Công Nghệ Thông Tin");
        arrayListnganh.add("Công nghệ Kỹ thuật Điện tử – Viễn thông");
        arrayListnganh.add("Công nghệ Kỹ thuật Điện, Điện tử");
        arrayListnganh.add("Công nghệ Kỹ thuật Điều khiển và Tự động hóa");
        ArrayAdapter adapternganh= new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayListnganh);
        danhsachnganh.setAdapter(adapternganh);
        //list view
        AnhXa();
        LopAdapter = new LopAdapter(this, R.layout.list_lop,LopArrayList);
        listViewLop.setAdapter(LopAdapter);
    }
    private void AnhXa(){
        listViewLop = (ListView) findViewById(R.id.lvlop);
    }

}