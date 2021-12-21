package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class QLNganhActivity extends AppCompatActivity {
        ListView listViewNganh;
        ArrayList<Nganh> nganhArrayList;
        NganhAdapter nganhAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlnganh);
        AnhXa();
        nganhAdapter = new NganhAdapter(this, R.layout.list_nganh,nganhArrayList);
        listViewNganh.setAdapter(nganhAdapter);
    }
    private void AnhXa(){
        listViewNganh = (ListView) findViewById(R.id.lvnganh);
        nganhArrayList = new ArrayList<>();
    }

}