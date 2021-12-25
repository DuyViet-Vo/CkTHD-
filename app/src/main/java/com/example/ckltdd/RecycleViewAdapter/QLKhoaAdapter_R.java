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
import com.example.ckltdd.R;
import com.example.ckltdd.Retrofit2.APIUtils;
import com.example.ckltdd.ValidData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLKhoaAdapter_R extends RecyclerView.Adapter<QLKhoaAdapter_R.QLKhoaHolder>{
    private List<Khoa> listKhoa;
    private Context context;

    public QLKhoaAdapter_R(List<Khoa> listKhoa) {
        this.listKhoa = listKhoa;
    }

    public QLKhoaAdapter_R(List<Khoa> listKhoa, Context context) {
        this.listKhoa = listKhoa;
        this.context = context;
    }

    @NonNull
    @Override
    public QLKhoaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ql_khoa, parent,false);
        return new QLKhoaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLKhoaHolder holder, int position) {
        Khoa khoa = listKhoa.get(position);
        if(khoa == null)
            return;

        holder.tv_ten.setText(khoa.getTenkhoa());

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

            ten_khoa.setText(khoa.getTenkhoa());
            btnSua.setText("Cập nhật");

            btnHuy.setOnClickListener(view1 -> {
                sua.cancel();
            });

            btnSua.setOnClickListener(view1 -> {
                String err = ValidData.IsEmpty(ten_khoa.getText().toString());
                ValidAction(ten_khoa, valid_ten, err);

                if (!err.isEmpty()) return;

                khoa.setTenkhoa(ten_khoa.getText().toString());
                Update(khoa, sua);
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
                Call<Integer> call = APIUtils.getAPIService().DeleteKhoa(khoa.getId());
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        LoadDSKhoa();
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

    private void Update(Khoa khoa, Dialog dialog) {
        Call<Khoa> call = APIUtils.getAPIService().UpdateKhoa(khoa);
        call.enqueue(new Callback<Khoa>() {
            @Override
            public void onResponse(Call<Khoa> call, Response<Khoa> response) {
                LoadDSKhoa();
                QLKhoa.notification.Notify("Sửa thành công!", "success");
            }

            @Override
            public void onFailure(Call<Khoa> call, Throwable t) {
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
        if(listKhoa != null) {
            return listKhoa.size();
        }
        return 0;
    }

    public class QLKhoaHolder extends RecyclerView.ViewHolder {
        private TextView tv_ten;
        private ImageButton btnSua, btnXoa;

        public QLKhoaHolder(@NonNull View itemView) {
            super(itemView);

            tv_ten = itemView.findViewById(R.id.tv_ten);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
    private void LoadDSKhoa() {
        Call<List<Khoa>> call = APIUtils.getAPIService().LoadDSKhoa();
        call.enqueue(new Callback<List<Khoa>>() {
            @Override
            public void onResponse(Call<List<Khoa>> call, Response<List<Khoa>> response) {
                listKhoa = response.body();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Khoa>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}
