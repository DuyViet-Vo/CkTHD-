package com.example.ckltdd.RecycleViewAdapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ckltdd.Fragment.QLNganh;
import com.example.ckltdd.HandleLoadEmtpy;
import com.example.ckltdd.Khoa;
import com.example.ckltdd.Lop;
import com.example.ckltdd.Nganh;
import com.example.ckltdd.R;
import com.example.ckltdd.Retrofit2.APIUtils;
import com.example.ckltdd.ValidData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLLopAdapter_R extends RecyclerView.Adapter<QLLopAdapter_R.QLLopHolder>{
    private List<Lop> listLop;
    private Context context;
    private KhoaAdapter_R khoaAdapter_r;
    private HandleLoadEmtpy handleLoadEmtpy;

    public KhoaAdapter_R getKhoaAdapter_r() {
        return khoaAdapter_r;
    }

    public HandleLoadEmtpy getHandleLoadEmtpy() {
        return handleLoadEmtpy;
    }

    public void setHandleLoadEmtpy(HandleLoadEmtpy handleLoadEmtpy) {
        this.handleLoadEmtpy = handleLoadEmtpy;
    }

    public void setKhoaAdapter_r(KhoaAdapter_R khoaAdapter_r) {
        this.khoaAdapter_r = khoaAdapter_r;
    }

    public QLLopAdapter_R(List<Lop> listLop) {
        this.listLop = listLop;
    }

    public QLLopAdapter_R(List<Lop> listLop, Context context) {
        this.listLop = listLop;
        this.context = context;
    }

    public List<Lop> getListLop() {
        return listLop;
    }

    public void setListLop(List<Lop> listLop) {
        this.listLop = listLop;
    }

    @NonNull
    @Override
    public QLLopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ql_khoa, parent,false);
        return new QLLopHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLLopHolder holder, int position) {
        Lop lop = listLop.get(position);
        if(lop == null)
            return;

        holder.tv_ten.setText(lop.getTenLop());

        holder.btnSua.setOnClickListener(view -> {

            Dialog sua = new Dialog(context);
            sua.requestWindowFeature(Window.FEATURE_NO_TITLE);
            sua.setContentView(R.layout.dialog_them_nganh);

            Window window = sua.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowsAttributes = window.getAttributes();
            windowsAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowsAttributes);

            sua.show();

            Spinner danhsachkhoa = sua.findViewById(R.id.spinnerkhoa);
            EditText them_input = sua.findViewById(R.id.them_input);
            TextView valid_ten = sua.findViewById(R.id.valid_ten),
                    valid_khoa = sua.findViewById(R.id.valid_khoa);
            Button btnHuy = sua.findViewById(R.id.btnHuy),
                    btnThem = sua.findViewById((R.id.btnThem));

            them_input.setText(lop.getTenLop());
            btnThem.setText("Cập nhật");
            initKhoaSpinner(danhsachkhoa, lop.getMaKhoa());

            btnHuy.setOnClickListener(view1 -> {
                sua.cancel();
            });

            btnThem.setOnClickListener(view1 -> {
                String err1 = ValidData.IsEmpty(them_input.getText().toString());
                String err2 = ValidData.IsSpinnerEmpty(QLNganh.khoaSelected);
                ValidAction(them_input, valid_ten, err1);
                ValidAction(danhsachkhoa, valid_khoa, err2);

                if (!err1.isEmpty() || !err2.isEmpty()) return;

                lop.setTenLop(them_input.getText().toString());
                lop.setMaNganh(QLNganh.khoaSelected);

//                Update(lop, sua);
            });
        });

        holder.btnXoa.setOnClickListener(view -> {
            Dialog xoa = new Dialog(context);
            xoa.requestWindowFeature(Window.FEATURE_NO_TITLE);
            xoa.setContentView(R.layout.dialog_xoa);

            Window window = xoa.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowsAttributes = window.getAttributes();
            windowsAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowsAttributes);

            xoa.show();

            Button huy = xoa.findViewById(R.id.d_huy);
            Button xoaBtn = xoa.findViewById(R.id.d_xoa);

            huy.setOnClickListener(view1 -> {
                xoa.cancel();
            });

            xoaBtn.setOnClickListener(view1 -> {
                listLop.remove(lop);
                notifyDataSetChanged();
                QLNganh.notification.Notify("Xoá thành công!", "success");
                Call<Integer> call = APIUtils.getAPIService().DeleteNganh(lop.getId());
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
                xoa.cancel();
            });
        });
    }

    private void Update(Nganh nganh, Dialog dialog) {
        Call<Nganh> call = APIUtils.getAPIService().UpdateNganh(nganh);
        call.enqueue(new Callback<Nganh>() {
            @Override
            public void onResponse(Call<Nganh> call, Response<Nganh> response) {
//                LoadDSNGanhByKhoaId2(QLNganh.khoaSelected);
                QLNganh.notification.Notify("Sửa thành công!", "success");
                khoaAdapter_r.setSelected(QLNganh.khoaSelected);
                khoaAdapter_r.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Nganh> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        dialog.cancel();
    }

    private void ValidAction(Spinner spinner, TextView textView, String check) {
        if (!check.isEmpty()) {
            if(spinner.isEnabled()) spinner.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner_invalid));
            else spinner.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner_disabled_invalid));
            textView.setText(check);
            return;
        }

        if(spinner.isEnabled()) spinner.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner));
        else spinner.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner_disabled));
        textView.setText("");
    }

    private void ValidAction(EditText editText, TextView textView, String check) {
        if (!check.isEmpty()) {
            editText.setBackground(context.getResources().getDrawable(R.drawable.bg_invalid));
            textView.setText(check);
            return;
        }

        editText.setBackground(context.getResources().getDrawable(R.drawable.shape_thongtin));
        textView.setText("");
    }

    private void initKhoaSpinner(Spinner danhsachkhoa, int id) {
        ArrayList<Khoa> list = new ArrayList<>();
        list.add(0, new Khoa( 0,"Chọn khoa"));
        ArrayAdapter<Khoa> arrayAdapter = new ArrayAdapter(context, R.layout.item_boloc, list);
        danhsachkhoa.setAdapter(arrayAdapter);

        Call<List<Khoa>> call = APIUtils.getAPIService().LoadDSKhoa();
        call.enqueue(new Callback<List<Khoa>>() {
            @Override
            public void onResponse(Call<List<Khoa>> call, Response<List<Khoa>> response) {
                ArrayList<Khoa> list = (ArrayList<Khoa>) response.body();
                list.add(0, new Khoa( 0,"Chọn khoa"));
                ArrayAdapter<Khoa> arrayAdapter = new ArrayAdapter(context, R.layout.item_boloc, list);
                danhsachkhoa.setAdapter(arrayAdapter);

                for (int j = 0; j < arrayAdapter.getCount(); j ++) {
                    if(arrayAdapter.getItem(j).getId() == id)
                        danhsachkhoa.setSelection(j);
                    System.out.println(arrayAdapter.getItem(j));
                }
                danhsachkhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Khoa khoa = (Khoa) adapterView.getItemAtPosition(i);
                        QLNganh.khoaSelected = khoa.getId();
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


    @Override
    public int getItemCount() {
        if(listLop != null) {
            return listLop.size();
        }
        return 0;
    }

    public class QLLopHolder extends RecyclerView.ViewHolder {
        private TextView tv_ten;
        private ImageButton btnSua, btnXoa;

        public QLLopHolder(@NonNull View itemView) {
            super(itemView);

            tv_ten = itemView.findViewById(R.id.tv_ten);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }


}
