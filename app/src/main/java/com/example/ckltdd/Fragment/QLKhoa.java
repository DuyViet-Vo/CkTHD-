package com.example.ckltdd.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ckltdd.Khoa;
import com.example.ckltdd.Notification;
import com.example.ckltdd.R;
import com.example.ckltdd.RecycleViewAdapter.KhoaAdapter_R;
import com.example.ckltdd.RecycleViewAdapter.QLKhoaAdapter_R;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;
import com.example.ckltdd.ValidData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLKhoa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLKhoa extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIServices mAPIService = APIUtils.getAPIService();
    private QLKhoaAdapter_R qlKhoaAdapter_r;
    private RecyclerView qlkhoa_rv;
    private View getView;
    private FloatingActionButton fAddBtn;
    private Dialog them;
    public static Notification notification;
    public QLKhoa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLKhoa.
     */
    // TODO: Rename and change types and number of parameters
    public static QLKhoa newInstance(String param1, String param2) {
        QLKhoa fragment = new QLKhoa();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getView = inflater.inflate(R.layout.fragment_q_l_khoa, container, false);

        qlkhoa_rv = getView.findViewById(R.id.rv_qlkhoa);
        fAddBtn = getView.findViewById(R.id.fAddBtn);
        LoadDSKhoa();

        notification = new Notification(
                getView.findViewById(R.id.notification),
                getView.findViewById(R.id.card_notification)
        );

        fAddBtn.setOnClickListener(view -> {
            ThemKhoaDialog();
        });

        return getView;
    }

    private void ThemKhoaDialog() {
        them = new Dialog(getContext());
        them.requestWindowFeature(Window.FEATURE_NO_TITLE);
        them.setContentView(R.layout.dialog_them_khoa);

        Window window = them.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowsAttributes = window.getAttributes();
        windowsAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowsAttributes);

        them.show();

        EditText them_khoa = them.findViewById(R.id.them_khoa);
        TextView valid_ten = them.findViewById(R.id.valid_ten);
        Button btnHuy = them.findViewById(R.id.btnHuy),
                btnThem = them.findViewById((R.id.btnThem));

        btnHuy.setOnClickListener(view -> {
            them.cancel();
        });

        btnThem.setOnClickListener(view -> {
            String err = ValidData.IsEmpty(them_khoa.getText().toString());
            ValidAction(them_khoa, valid_ten, err);

            if (!err.isEmpty()) return;

            Khoa khoa = new Khoa(them_khoa.getText().toString());
            Them(khoa);
        });
    }

    private void Them(Khoa khoa) {
        Call<Khoa> call = mAPIService.InsertKhoa(khoa);
        call.enqueue(new Callback<Khoa>() {
            @Override
            public void onResponse(Call<Khoa> call, Response<Khoa> response) {
                LoadDSKhoa();
                notification.Notify("Thêm thành công!", "success");
            }

            @Override
            public void onFailure(Call<Khoa> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        them.cancel();
    }



    private void ValidAction(EditText editText, TextView textView, String check) {
        if (!check.isEmpty()) {
            editText.setBackground(getResources().getDrawable(R.drawable.bg_invalid));
            textView.setText(check);
            return;
        }

        editText.setBackground(getResources().getDrawable(R.drawable.shape_thongtin));
        textView.setText("");
    }


    private void LoadDSKhoa() {
        Call<List<Khoa>> call = mAPIService.LoadDSKhoa();
        call.enqueue(new Callback<List<Khoa>>() {
            @Override
            public void onResponse(Call<List<Khoa>> call, Response<List<Khoa>> response) {
                List<Khoa> list = response.body();

                qlKhoaAdapter_r = new QLKhoaAdapter_R(list, getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                qlkhoa_rv.setLayoutManager(linearLayoutManager);
                qlkhoa_rv.setAdapter(qlKhoaAdapter_r);
                qlkhoa_rv.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
            }

            @Override
            public void onFailure(Call<List<Khoa>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}