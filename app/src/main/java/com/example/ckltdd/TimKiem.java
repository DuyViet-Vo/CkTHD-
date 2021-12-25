package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ckltdd.RecycleViewAdapter.SearchAdapter_R;
import com.example.ckltdd.Retrofit2.APIUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiem extends AppCompatActivity {
    SearchAdapter_R searchAdapter_r;
    RecyclerView rv_search;
    List<SinhVien> listSV;
    EditText input_search;
    ImageButton tk_back;
    TextView sv_empty;
    HandleLoadEmtpy handleLoadEmtpy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        setColorStatusBar();

        listSV = new ArrayList<>();
        input_search = findViewById(R.id.search_ip);
        rv_search = findViewById(R.id.search_rv);
        searchAdapter_r = new SearchAdapter_R(listSV, this);
        tk_back = findViewById(R.id.tk_back);
        sv_empty = findViewById(R.id.sv_empty);
        handleLoadEmtpy = new HandleLoadEmtpy(rv_search, sv_empty);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TimKiem.this, LinearLayoutManager.VERTICAL, false);
        rv_search.setLayoutManager(linearLayoutManager);
        rv_search.setAdapter(searchAdapter_r);

        tk_back.setOnClickListener(view -> {
            onBackPressed();
        });

        input_search.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.showSoftInput(input_search, InputMethodManager.SHOW_IMPLICIT);

        input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if (!charSequence.toString().trim().isEmpty())  TimKiem(charSequence.toString().trim());
                System.out.println(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void TimKiem(String str) {
        handleLoadEmtpy.changeRV(false);
        Call<List<SinhVien>> call = APIUtils.getAPIService().TimKiem(str);
        call.enqueue(new Callback<List<SinhVien>>() {
            @Override
            public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                listSV = response.body();
                searchAdapter_r.setListSV(listSV);
                searchAdapter_r.notifyDataSetChanged();
                handleLoadEmtpy.empty(listSV.size());
                handleLoadEmtpy.changeRV(true);
            }

            @Override
            public void onFailure(Call<List<SinhVien>> call, Throwable t) {

            }
        });
    }

    private void setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(TimKiem.this,R.color.white));
    }
}