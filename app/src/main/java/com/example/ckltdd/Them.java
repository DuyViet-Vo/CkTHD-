package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;

import java.io.IOException;
import java.text.ParseException;
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
    RadioButton them_nu, them_nam;
    ImageView them_avt;
    TextView tv_masv, tv_hoTen, tv_ngaySinh, tv_cccd, tv_khoa, tv_nganh, tv_lop, tv_sdt, tv_email, tv_diaChi;
    EditText et_msv, et_hoTen, et_ngaySinh, et_cccd, et_sdt, et_email, et_diaChi;
    Boolean isValid = false;
    ImageButton them_back;
    Button them_luu;
    LinearLayout load_insert;
    final int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;

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

        them_nu = findViewById(R.id.them_nu);
        them_nam = findViewById(R.id.them_nam);
        them_avt = findViewById(R.id.them_avt);
        them_back = findViewById(R.id.them_back);
        them_luu = findViewById(R.id.them_luu);

        load_insert = findViewById(R.id.load_insert);

        them_nu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) them_avt.setImageResource(R.drawable.avt_nu);
            }
        });

        them_nam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) them_avt.setImageResource(R.drawable.avatar_nam2);
            }
        });

        initKhoaSpinner();
        initNganhSpinner();
        initLopSpinner();

        initInvalidTextView();
        initInput();

        them_back.setOnClickListener(view -> {
            onBackPressed();
        });

        them_luu.setOnClickListener(view -> {
            try {
                ThemSV();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //date_pick
        editDate.setOnClickListener(view -> {
            ChonNgay();
        });

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ValidData();
    }

    private void ThemSV() throws IOException {


        Khoa khoa = (Khoa) danhsachkhoa.getSelectedItem();
        Lop lop = (Lop) danhsachlop.getSelectedItem();
        Nganh nganh = (Nganh) danhsachnganh.getSelectedItem();

        String[] errors = {
                CheckNumber(et_msv.getText().toString(), "Mã sinh viên", 13, et_msv, tv_masv),
                CheckNumber(et_cccd.getText().toString(), "Số CCCD", 12, et_cccd, tv_cccd),
                CheckNumber(et_sdt.getText().toString(), "Số điện thoại", 10, et_sdt, tv_sdt),
                CheckHoTen(et_hoTen.getText().toString()),
                CheckNgaySinh(et_ngaySinh.getText().toString()),
                CheckSpinner(danhsachkhoa, tv_khoa, khoa.getId()),
                CheckSpinner(danhsachlop, tv_lop, lop.getId()),
                CheckSpinner(danhsachnganh, tv_nganh, nganh.getId()),
                CheckEmail(et_email.getText().toString()),
                CheckDiaChi(et_diaChi.getText().toString())
        };

        for (int i = 0; i < errors.length ; i ++) {
            if (!errors[i].isEmpty()) return;
        }

        load_insert.getLayoutParams().height = matchParent;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        String ngaySinh = null;
        try {
            ngaySinh = sdf.format(sdf2.parse(et_ngaySinh.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        SinhVien sinhVien = new SinhVien(
                et_msv.getText().toString(),
                et_hoTen.getText().toString(),
                (byte) (them_nam.isChecked() ? 1 : 0),
                et_cccd.getText().toString(),
                et_sdt.getText().toString(),
                et_email.getText().toString(),
                et_diaChi.getText().toString(),
                lop.getId(),
                ngaySinh                
        );

        Call<SinhVien> call = mAPIService.InsertSV(sinhVien);
        call.execute();

        Intent intent = new Intent();
        intent.putExtra("idLop", lop.getId());
        intent.putExtra("idKhoa", khoa.getId());
        intent.putExtra("tenLop", lop.getTenLop());
        setResult(RESULT_OK, intent);
        finish();

    }

    private void initInvalidTextView() {
        tv_masv = findViewById(R.id.valid_msv);
        tv_hoTen = findViewById(R.id.valid_hoTen);
        tv_ngaySinh = findViewById(R.id.valid_ngaySinh);
        tv_cccd = findViewById(R.id.valid_cccd);
        tv_khoa = findViewById(R.id.valid_khoa);
        tv_nganh = findViewById(R.id.valid_nganh);
        tv_lop = findViewById(R.id.valid_lop);
        tv_sdt = findViewById(R.id.valid_sdt);
        tv_email = findViewById(R.id.valid_email);
        tv_diaChi = findViewById(R.id.valid_diachi);
    }

    private void initInput() {
        et_msv = findViewById(R.id.them_msv);
        et_hoTen = findViewById(R.id.them_hoTen);
        et_ngaySinh = findViewById(R.id.date_picker_actions);
        et_cccd = findViewById(R.id.them_cccd);
        et_sdt = findViewById(R.id.them_sdt);
        et_email = findViewById(R.id.them_email);
        et_diaChi = findViewById(R.id.them_diaChi);
    }

    //    ------------------------------- VALID DATA --------------------------------------------------------

    private void ValidData() {
        CheckMSV();
        CheckHoTen();
        CheckNgaySinh();
        CheckCCCD();
        CheckSDT();
        CheckEmail();
        CheckDiaChi();
    }

    private void ValidAction(EditText editText, TextView textView, String check) {
        if (!check.isEmpty()) {
            editText.setBackground(getResources().getDrawable(R.drawable.bg_invalid));
            textView.setText(check);
            isValid = false;
            return;
        }

        editText.setBackground(getResources().getDrawable(R.drawable.shape_thongtin));
        textView.setText("");
        isValid = true;
    }

    private void ValidAction(Spinner spinner, TextView textView, String check) {
        if (!check.isEmpty()) {
            if(spinner.isEnabled()) spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner_invalid));
            else spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled_invalid));
            textView.setText(check);
            if (spinner == danhsachlop) isValid = false;
            return;
        }

        if(spinner.isEnabled()) spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner));
        else spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled));
        textView.setText("");
        if (spinner == danhsachlop) isValid = true;
    }

    private String CheckSpinner(Spinner spinner, TextView textView, int id) {
        String check = ValidData.IsSpinnerEmpty(id);
        ValidAction(spinner, textView, check);
        return check;
    }

    private void CheckDiaChi() {
        et_diaChi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckDiaChi(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private String CheckDiaChi(String str) {
        String check = ValidData.IsEmpty(str);
        ValidAction(et_diaChi, tv_diaChi, check);
        return check;
    }

    private void CheckEmail() {
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private String CheckEmail(String str) {
        String check = ValidData.IsEmpty(str);
        check = !check.isEmpty() ? check : ValidData.IsEmail(str);
        ValidAction(et_email, tv_email, check);
        return check;
    }

    private void CheckSDT() {
        et_sdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckNumber(charSequence.toString(), "Số điện thoại", 10,  et_sdt, tv_sdt);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void CheckCCCD() {
        et_cccd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckNumber(charSequence.toString(), "Số CCCD", 12, et_cccd, tv_cccd);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void CheckNgaySinh() {
        et_ngaySinh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckNgaySinh(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private String CheckNgaySinh(String str) {
        System.out.println(str);
        String check = ValidData.IsEmpty(str);
        ValidAction(et_ngaySinh, tv_ngaySinh, check);
        return check;
    }

    private void CheckHoTen() {
        et_hoTen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckHoTen(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private String CheckHoTen(String str) {
        String check = ValidData.IsEmpty(str);
        check = !check.isEmpty() ? check : ValidData.IsName(str);
        ValidAction(et_hoTen, tv_hoTen, check);
        return check;
    }

    private void CheckMSV() {
        et_msv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String check = CheckNumber(charSequence.toString(), "Mã sinh viên", 13, et_msv, tv_masv);

                if(check.isEmpty() && charSequence.length() == 13)
                    try {
                        check = CheckMSVTonTai(charSequence.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                ValidAction(et_msv, tv_masv, check);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private String CheckMSVTonTai(String idSV) throws IOException {
        Call<SinhVien> call = mAPIService.getStudentById(idSV);
        SinhVien sinhVien = call.execute().body();
        System.out.println(sinhVien);
        return sinhVien.getHoTen() == null ? "" : "Mã sinh viên đã tồn tại!";
    }

    private String CheckNumber(String str, String msg, int lenght, EditText et, TextView tv) {
        String check = ValidData.IsEmpty(str);
        check = !check.isEmpty() ? check : ValidData.IsNumber(str, msg, lenght);
        ValidAction(et, tv, check);
        return check;
    }


//    ---------------------------------------------------------------------------------------

    private void initKhoaSpinner() {
        ArrayList<Khoa> list = new ArrayList<>();
        list.add(0, new Khoa( 0,"Chọn khoa"));
        ArrayAdapter<Khoa> arrayAdapter = new ArrayAdapter(Them.this, R.layout.item_boloc, list);
        danhsachkhoa.setAdapter(arrayAdapter);

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
                            if (tv_nganh.getText().toString().isEmpty())
                                danhsachnganh.setBackground(getResources().getDrawable(R.drawable.bg_spinner_invalid));
                        } else {
                            danhsachlop.setEnabled(false);
                            danhsachnganh.setEnabled(false);

                            if (tv_lop.getText().toString().isEmpty())
                                danhsachlop.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled));
                            else
                                danhsachlop.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled_invalid));
                            if (tv_nganh.getText().toString().isEmpty())
                                danhsachnganh.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled));
                            else
                                danhsachnganh.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled_invalid));
                        }

                        CheckSpinner(danhsachkhoa, tv_khoa, khoa.getId());
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
                    if (tv_lop.getText().toString().isEmpty())
                        danhsachlop.setBackground(getResources().getDrawable(R.drawable.bg_spinner_invalid));
                    LoadDSLopByNganhId(nganh.getId());
                } else {
                    danhsachlop.setEnabled(false);
                    if (tv_lop.getText().toString().isEmpty())
                        danhsachlop.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled));
                    else
                        danhsachlop.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled_invalid));
                }
                danhsachlop.setSelection(0, true);
                CheckSpinner(danhsachnganh, tv_nganh, nganh.getId());
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
                CheckSpinner(danhsachlop, tv_lop, lop.getId());
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