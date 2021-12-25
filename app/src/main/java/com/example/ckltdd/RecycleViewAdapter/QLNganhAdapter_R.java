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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ckltdd.Fragment.QLKhoa;
import com.example.ckltdd.Khoa;
import com.example.ckltdd.Nganh;
import com.example.ckltdd.R;
import com.example.ckltdd.Retrofit2.APIUtils;
import com.example.ckltdd.ValidData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLNganhAdapter_R extends RecyclerView.Adapter<QLNganhAdapter_R.QLNganhHolder>{
    private List<Nganh> listNganh;
    private Context context;

    public QLNganhAdapter_R(List<Nganh> listNganh) {
        this.listNganh = listNganh;
    }

    public QLNganhAdapter_R(List<Nganh> listNganh, Context context) {
        this.listNganh = listNganh;
        this.context = context;
    }

    @NonNull
    @Override
    public QLNganhHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ql_khoa, parent,false);
        return new QLNganhHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLNganhHolder holder, int position) {
        Nganh nganh = listNganh.get(position);
        if(nganh == null)
            return;

        holder.tv_ten.setText(nganh.getTenNganh());

        holder.btnSua.setOnClickListener(view -> {

            Dialog sua = new Dialog(context);
            sua.requestWindowFeature(Window.FEATURE_NO_TITLE);
            sua.setContentView(R.layout.dialog_them_khoa);

            Window window = sua.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowsAttributes = window.getAttributes();
            windowsAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowsAttributes);

            sua.show();

            EditText ten_khoa = sua.findViewById(R.id.them_khoa);
            TextView valid_ten = sua.findViewById(R.id.valid_ten);
            Button btnHuy = sua.findViewById(R.id.btnHuy),
                    btnSua = sua.findViewById((R.id.btnThem));

            ten_khoa.setText(nganh.getTenNganh());
            btnSua.setText("Cập nhật");

            btnHuy.setOnClickListener(view1 -> {
                sua.cancel();
            });

            btnSua.setOnClickListener(view1 -> {
                String err = ValidData.IsEmpty(ten_khoa.getText().toString());
                ValidAction(ten_khoa, valid_ten, err);

                if (!err.isEmpty()) return;

                nganh.setTenNganh(ten_khoa.getText().toString());
                Update(nganh, sua);
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
                Call<Integer> call = APIUtils.getAPIService().DeleteNganh(nganh.getId());
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        QLKhoa.notification.Notify("Xóa thành công", "success");
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
                QLKhoa.notification.Notify("Sửa thành công!", "success");
            }

            @Override
            public void onFailure(Call<Nganh> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        dialog.cancel();
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

    @Override
    public int getItemCount() {
        if(listNganh != null) {
            return listNganh.size();
        }
        return 0;
    }

    public class QLNganhHolder extends RecyclerView.ViewHolder {
        private TextView tv_ten;
        private ImageButton btnSua, btnXoa;

        public QLNganhHolder(@NonNull View itemView) {
            super(itemView);

            tv_ten = itemView.findViewById(R.id.tv_ten);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }


}
