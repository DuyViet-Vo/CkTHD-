package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DanhNhapActivity extends AppCompatActivity {
Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        btnDangNhap = (Button) findViewById(R.id.btndangnhap);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhNhapActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}