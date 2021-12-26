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

import com.example.ckltdd.Fragment.QLKhoa;
import com.example.ckltdd.Fragment.QLLop;
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
    private Spinner nganhSpinner;
    private boolean firstTime = true;

    public KhoaAdapter_R getKhoaAdapter_r() {
        return khoaAdapter_r;
    }

    public HandleLoadEmtpy getHandleLoadEmtpy() {
        return handleLoadEmtpy;
    }

    public void setHandleLoadEmtpy(HandleLoadEmtpy handleLoadEmtpy) {
        this.handleLoadEmtpy = handleLoadEmtpy;
    }

    public Spinner getNganhSpinner() {
        return nganhSpinner;
    }

    public void setNganhSpinner(Spinner nganhSpinner) {
        this.nganhSpinner = nganhSpinner;
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
            sua.setContentView(R.layout.dialog_them_lop);

            Window window = sua.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowsAttributes = window.getAttributes();
            windowsAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowsAttributes);

            sua.show();

            Spinner danhsachkhoa = sua.findViewById(R.id.spinnerkhoa);
            Spinner danhsachnganh = sua.findViewById(R.id.spinnernganh);
            EditText them_input = sua.findViewById(R.id.them_input);
            TextView valid_ten = sua.findViewById(R.id.valid_ten),
                    valid_khoa = sua.findViewById(R.id.valid_khoa),
                    valid_nganh = sua.findViewById(R.id.valid_nganh);
            Button btnHuy = sua.findViewById(R.id.btnHuy),
                    btnSua = sua.findViewById((R.id.btnThem));

            btnSua.setText("Cập nhật");
            them_input.setText(lop.getTenLop());

            initNganhSpinner(danhsachnganh);
            initKhoaSpinner(danhsachkhoa, danhsachnganh, valid_nganh, lop.getMaKhoa(), lop.getMaNganh());

            btnHuy.setOnClickListener(view1 -> {
                sua.cancel();
            });

            btnSua.setOnClickListener(view1 -> {
                String err1 = ValidData.IsEmpty(them_input.getText().toString());
                String err2 = ValidData.IsSpinnerEmpty(QLLop.khoaSelected);
                String err3 = ValidData.IsSpinnerEmpty(QLLop.nganhSelected);
                ValidAction(them_input, valid_ten, err1);
                ValidAction(danhsachkhoa, valid_khoa, err2);
                ValidAction(danhsachnganh, valid_nganh, err3);

                if (!err1.isEmpty() || !err2.isEmpty() || !err3.isEmpty()) return;

                lop.setTenLop(them_input.getText().toString());
                lop.setMaNganh(QLLop.nganhSelected);
                Update(lop, sua);
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
                QLLop.notification.Notify("Xoá thành công!", "success");
                Call<Integer> call = APIUtils.getAPIService().DeleteLop(lop.getId());
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

    private void Update(Lop lop, Dialog dialog) {
        Call<Lop> call = APIUtils.getAPIService().UpdateLop(lop);
        call.enqueue(new Callback<Lop>() {
            @Override
            public void onResponse(Call<Lop> call, Response<Lop> response) {
                LoadDSLopByNganhId_2(lop.getMaNganh());
                QLLop.notification.Notify("Sửa thành công!", "success");
                khoaAdapter_r.setSelected(QLLop.khoaSelected);
                khoaAdapter_r.notifyDataSetChanged();

                ArrayAdapter<Nganh> arrayAdapter = (ArrayAdapter<Nganh>) nganhSpinner.getAdapter();

                for (int j = 0; j < arrayAdapter.getCount(); j ++) {
                    if(arrayAdapter.getItem(j).getId() == lop.getMaNganh())
                        nganhSpinner.setSelection(j);
                }
            }

            @Override
            public void onFailure(Call<Lop> call, Throwable t) {
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

    private void initKhoaSpinner(Spinner danhsachkhoa, Spinner danhsachnganh, TextView tv_nganh, int id, int idNganh) {
        ArrayList<Khoa> list = new ArrayList<>();
        list.add(0, new Khoa( 0,"Chọn khoa"));
        ArrayAdapter<Khoa> arrayAdapter = new ArrayAdapter(context, R.layout.item_boloc_dialog, list);
        danhsachkhoa.setAdapter(arrayAdapter);

        Call<List<Khoa>> call = APIUtils.getAPIService().LoadDSKhoa();
        call.enqueue(new Callback<List<Khoa>>() {
            @Override
            public void onResponse(Call<List<Khoa>> call, Response<List<Khoa>> response) {
                ArrayList<Khoa> list = (ArrayList<Khoa>) response.body();
                list.add(0, new Khoa( 0,"Chọn khoa"));

                arrayAdapter.clear();
                arrayAdapter.addAll(list);

                for (int j = 0; j < arrayAdapter.getCount(); j ++) {
                    if(arrayAdapter.getItem(j).getId() == id)
                        danhsachkhoa.setSelection(j);
                }

                danhsachkhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Khoa khoa = (Khoa) adapterView.getItemAtPosition(i);
                        QLLop.khoaSelected = khoa.getId();

                        if (khoa.getId() != 0) {
                            if (firstTime)  {
                                LoadDSNGanhByKhoaId(id, danhsachnganh, idNganh, true);
                                firstTime = false;
                            }
                            else LoadDSNGanhByKhoaId(khoa.getId(), danhsachnganh, 0, false);
                            danhsachnganh.setEnabled(true);
                            if (!tv_nganh.getText().toString().isEmpty())
                                danhsachnganh.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner_invalid));
                            else
                                danhsachnganh.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner));
                        } else {
                            danhsachnganh.setEnabled(false);
                            if (tv_nganh.getText().toString().isEmpty())
                                danhsachnganh.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner_disabled));
                            else
                                danhsachnganh.setBackground(context.getResources().getDrawable(R.drawable.bg_spinner_disabled_invalid));
                        }

                        if (!firstTime) danhsachnganh.setSelection(0, true);
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

    private void LoadDSNGanhByKhoaId(int khoaId, Spinner danhsachnganh, int id, boolean isInit) {
        Call<ArrayList<Nganh>> call = APIUtils.getAPIService().LoadDSNganhByKhoaId(khoaId);
        call.enqueue(new Callback<ArrayList<Nganh>>() {
            @Override
            public void onResponse(Call<ArrayList<Nganh>> call, Response<ArrayList<Nganh>> response) {
                ArrayList<Nganh> list = response.body();
                list.add(0, new Nganh( 0,"Chọn ngành"));
                ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter<>(context, R.layout.item_boloc_dialog, list);
                danhsachnganh.setAdapter(arrayAdapter);

                if(isInit) {
                    for (int j = 0; j < arrayAdapter.getCount(); j ++) {
                        if(arrayAdapter.getItem(j).getId() == id)
                            danhsachnganh.setSelection(j);

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Nganh>> call, Throwable t) {

            }
        });
    }

    private void initNganhSpinner(Spinner danhsachnganh) {
        ArrayList<Nganh> list = new ArrayList<>();
        list.add(0, new Nganh( 0,"Chọn Ngành"));

        ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter(context, R.layout.item_boloc_dialog, list);
        danhsachnganh.setEnabled(false);
        danhsachnganh.setAdapter(arrayAdapter);
        danhsachnganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Nganh nganh = (Nganh) adapterView.getItemAtPosition(i);
                QLLop.nganhSelected = nganh.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void LoadDSLopByNganhId_2(int id) {
        handleLoadEmtpy.empty(1);
        handleLoadEmtpy.HandleLoadAnimation_r(true);
        Call<ArrayList<Lop>> call = APIUtils.getAPIService().LoadDSLopByNganhId(id);
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();

                setListLop(list);
                notifyDataSetChanged();
                handleLoadEmtpy.HandleLoadAnimation_r(false);
                handleLoadEmtpy.empty(list.size());
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {
                System.out.println(t.getMessage());
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
