package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SimpleTimeZone;

public class ThemSua extends AppCompatActivity {
    EditText editDate;
    Spinner danhsachkhoa,danhsachnganh,danhsachlop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua);
        editDate=(EditText) findViewById(R.id.date_picker_actions);
        danhsachkhoa = (Spinner) findViewById(R.id.spinnerkhoa);
        danhsachnganh=(Spinner)findViewById(R.id.spinnernganh);
        danhsachlop = (Spinner) findViewById(R.id.spinnerlop);

        //spinner khoa
        ArrayList<String> arrayListkhoa = new ArrayList<String>();
        arrayListkhoa.add("Điện-Điện Tử");
        arrayListkhoa.add("Sư phạm Công nghiệp");
        arrayListkhoa.add("Cơ khí");
        arrayListkhoa.add("Công nghệ Hóa học–Môi trường");
        arrayListkhoa.add("Kỹ thuật Xây dựng");
        ArrayAdapter adapterkhoa= new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayListkhoa);
        danhsachkhoa.setAdapter(adapterkhoa);

        //spinner nganh
        ArrayList<String> arrayListnganh = new ArrayList<String>();
        arrayListnganh.add("Công Nghệ Thông Tin");
        arrayListnganh.add("Công nghệ Kỹ thuật Điện tử – Viễn thông");
        arrayListnganh.add("Công nghệ Kỹ thuật Điện, Điện tử");
        arrayListnganh.add("Công nghệ Kỹ thuật Điều khiển và Tự động hóa");
        ArrayAdapter adapternganh= new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayListnganh);
        danhsachnganh.setAdapter(adapternganh);

        //spinner lop
        ArrayList<String> arrayListlop = new ArrayList<String>();
        arrayListlop.add("19T1");
        arrayListlop.add("19T2");
        arrayListlop.add("20T1");
        arrayListlop.add("20T2");
        ArrayAdapter adapterlop= new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayListlop);
        danhsachlop.setAdapter(adapterlop);

        //date_pick
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
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

}