package com.example.ckltdd;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.ckltdd.RecycleViewAdapter.KhoaAdapter_R;
import com.example.ckltdd.Retrofit2.APIUtils;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sinhVienAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<SinhVien> sinhVienList;
    private int REQUEST_CODE_EDIT = 112;
    private HandleLoadEmtpy handleLoadEmtpy;
    private Button notification;
    private CardView card_notification;
    private KhoaAdapter_R khoaAdapter_r;
    private TextView txtLop;

    public KhoaAdapter_R getKhoaAdapter_r() {
        return khoaAdapter_r;
    }

    public void setKhoaAdapter_r(KhoaAdapter_R khoaAdapter_r) {
        this.khoaAdapter_r = khoaAdapter_r;
    }

    public CardView getCard_notification() {
        return card_notification;
    }

    public void setCard_notification(CardView card_notification) {
        this.card_notification = card_notification;
    }

    public TextView getTxtLop() {
        return txtLop;
    }

    public void setTxtLop(TextView txtLop) {
        this.txtLop = txtLop;
    }

    public sinhVienAdapter(Context context, int layout, List<SinhVien> sinhVienList) {
        this.context = context;
        this.layout = layout;
        this.sinhVienList = sinhVienList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public HandleLoadEmtpy getHandleLoadEmtpy() {
        return handleLoadEmtpy;
    }

    public void setHandleLoadEmtpy(HandleLoadEmtpy handleLoadEmtpy) {
        this.handleLoadEmtpy = handleLoadEmtpy;
    }

    public Button getNotification() {
        return notification;
    }

    public void setNotification(Button notification) {
        this.notification = notification;
    }

    public List<SinhVien> getSinhVienList() {
        return sinhVienList;
    }

    public void setSinhVienList(List<SinhVien> sinhVienList) {
        this.sinhVienList = sinhVienList;
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        //anh xa view
        TextView txttensv=(TextView) convertView.findViewById(R.id.txtten);
        TextView txtmasinhvien=(TextView) convertView.findViewById(R.id.txtmasv);
        ImageView imagehinhcanhan = (ImageView) convertView.findViewById(R.id.imagehinh);
        GifImageView avt_gifLoad = convertView.findViewById(R.id.avt_gifLoad);
        RelativeLayout relativeLayout = convertView.findViewById(R.id.item_sv);
        ImageButton btnSua = convertView.findViewById(R.id.btnsua);
        ImageButton btnXoa = convertView.findViewById(R.id.btnxoa);

        //gán giá trị
        SinhVien sinhvien = sinhVienList.get(position);
        txttensv.setText(sinhvien.getHoTen());
        txtmasinhvien.setText(sinhvien.getId());

        if (sinhvien.getAnhDaiDien() == null || sinhvien.getAnhDaiDien().isEmpty()) {
            imagehinhcanhan.setImageResource(sinhvien.getGioiTinh() == 1 ? R.drawable.avatar_nam2 : R.drawable.avt_nu);
        } else {
            Glide.with(context).load("https://app-quanlysv.herokuapp.com/img/" + sinhvien.getAnhDaiDien()).into(imagehinhcanhan);
        }

        avt_gifLoad.getLayoutParams().width = 0;

        relativeLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, ChiTietActivity.class);
            intent.putExtra("idSV", sinhvien.getId());
            context.startActivity(intent);
        });

        btnSua.setOnClickListener(view -> {
            Intent intent = new Intent(context, sua.class);
            intent.putExtra("idSV", sinhvien.getId());
            ((Activity) context).startActivityForResult(intent,REQUEST_CODE_EDIT);
        });

        btnXoa.setOnClickListener(view -> {
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
                Call<Boolean> call = APIUtils.getAPIService().DeleteSV(sinhvien.getId());
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                txtLop.setText(MainActivity.lopLoc);
                khoaAdapter_r.setSelected(MainActivity.khoaId);
                khoaAdapter_r.notifyDataSetChanged();
                LoadStudentsByClassId(MainActivity.khoaId, MainActivity.nganhId, MainActivity.lopId);

                notification.setText("Xóa thành công!");
                notification.setBackgroundColor(Color.parseColor("#6CD06A"));
                card_notification.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                card_notification.getLayoutParams().height = 0;
                            }
                        }, 3000);

                xoa.cancel();
            });
        });

        return convertView;
    }

    private void LoadStudentsByClassId(int khoaId, int nganhId, int lopId) {
        handleLoadEmtpy.empty(1);
        handleLoadEmtpy.HandleLoadAnimation(true);
        Call<List<SinhVien>> call = APIUtils.getAPIService().LoadStudentsByClassId(khoaId, nganhId, lopId);
        call.enqueue(new Callback<List<SinhVien>>() {
            @Override
            public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                List<SinhVien> list = response.body();
                setSinhVienList(list);
                notifyDataSetChanged();
                handleLoadEmtpy.HandleLoadAnimation(false);
                handleLoadEmtpy.empty(list.size());
            }

            @Override
            public void onFailure(Call<List<SinhVien>> call, Throwable t) {

            }
        });
    }

}
