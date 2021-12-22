package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Them extends AppCompatActivity {
    EditText editDate;
    Spinner danhsachkhoa,danhsachnganh,danhsachlop;
    APIServices mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them);
        setColorStatusBar();
        mAPIService = APIUtils.getAPIService();

        editDate=(EditText) findViewById(R.id.date_picker_actions);
        danhsachkhoa = (Spinner) findViewById(R.id.spinnerkhoa);
        danhsachnganh=(Spinner)findViewById(R.id.spinnernganh);
        danhsachlop = (Spinner) findViewById(R.id.spinnerlop);

        initKhoaSpinner();
        initNganhSpinner();
        initLopSpinner();

        //date_pick
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
    }

    private void initKhoaSpinner() {
        Call<List<Khoa>> call = mAPIService.LoadDSKhoa();
        call.enqueue(new Callback<List<Khoa>>() {
            @Override
            public void onResponse(Call<List<Khoa>> call, Response<List<Khoa>> response) {
                ArrayList<Khoa> list = (ArrayList<Khoa>) response.body();
                list.add(0, new Khoa( 0,"Chọn khoa"));

                ArrayAdapter<Khoa> arrayAdapter = new ArrayAdapter(Them.this, R.layout.item_boloc, list);
                danhsachkhoa.setAdapter(arrayAdapter);
                danhsachkhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Khoa khoa = (Khoa) adapterView.getItemAtPosition(i);
                        if (khoa.getId() != 0) {
                            LoadDSNGanhByKhoaId(khoa.getId());
                            danhsachnganh.setEnabled(true);
                            danhsachnganh.setBackground(getResources().getDrawable(R.drawable.bg_spinner));
                        } else {
                            danhsachlop.setEnabled(false);
                            danhsachlop.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled));
                            danhsachnganh.setEnabled(false);
                            danhsachnganh.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled));
                        }

                        danhsachlop.setSelection(0, true);
                        danhsachnganh.setSelection(0, true);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Khoa>> call, Throwable t) {

            }
        });
    }

    private void initNganhSpinner() {
        ArrayList<Nganh> list = new ArrayList<>();
        list.add(0, new Nganh( 0,"Chọn Ngành"));

        ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter(Them.this, R.layout.item_boloc, list);
        danhsachnganh.setEnabled(false);
        danhsachnganh.setAdapter(arrayAdapter);
        danhsachnganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Nganh nganh = (Nganh) adapterView.getItemAtPosition(i);
                if (nganh.getId() != 0) {
                    danhsachlop.setEnabled(true);
                    danhsachlop.setBackground(getResources().getDrawable(R.drawable.bg_spinner));
                    LoadDSLopByNganhId(nganh.getId());
                } else {
                    danhsachlop.setEnabled(false);
                    danhsachlop.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled));
                }
                danhsachlop.setSelection(0, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void LoadDSNGanhByKhoaId(int khoaId) {
        Call<ArrayList<Nganh>> call = mAPIService.LoadDSNganhByKhoaId(khoaId);
        call.enqueue(new Callback<ArrayList<Nganh>>() {
            @Override
            public void onResponse(Call<ArrayList<Nganh>> call, Response<ArrayList<Nganh>> response) {
                ArrayList<Nganh> list = response.body();
                list.add(0, new Nganh( 0,"Chọn ngành"));
                ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter<>(Them.this, R.layout.item_boloc, list);
                danhsachnganh.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Nganh>> call, Throwable t) {

            }
        });
    }

    private void LoadDSLopByNganhId(int id) {
        Call<ArrayList<Lop>> call = mAPIService.LoadDSLopByNganhId(id);
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();
                list.add(0, new Lop( 0,"Chọn lớp"));
                ArrayAdapter<Lop> lopAdapter = new ArrayAdapter<>(Them.this, R.layout.item_boloc, list);
                danhsachlop.setAdapter(lopAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void initLopSpinner() {
        ArrayList<Lop> list = new ArrayList<>();
        list.add(0, new Lop( 0,"Chọn lớp"));

        ArrayAdapter<Lop> arrayAdapter = new ArrayAdapter(Them.this, R.layout.item_boloc, list);
        danhsachlop.setEnabled(false);
        danhsachlop.setAdapter(arrayAdapter);
        danhsachlop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Lop lop = (Lop) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void ChonNgay(){
        Calendar calendar =Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang= calendar.get(Calendar.MONTH);
        int nam= calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private void setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(Them.this,R.color.white));
    }

}