package com.example.ckltdd.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ckltdd.HandleLoadEmtpy;
import com.example.ckltdd.Khoa;
import com.example.ckltdd.Nganh;
import com.example.ckltdd.Notification;
import com.example.ckltdd.R;
import com.example.ckltdd.RecycleViewAdapter.KhoaAdapter_R;
import com.example.ckltdd.RecycleViewAdapter.QLKhoaAdapter_R;
import com.example.ckltdd.RecycleViewAdapter.QLNganhAdapter_R;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;
import com.example.ckltdd.Them;
import com.example.ckltdd.ValidData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLNganh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLNganh extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIServices mAPIService = APIUtils.getAPIService();
    private QLNganhAdapter_R qlNganhAdapter_r;
    private RecyclerView qlnganh_rv;
    private View getView;
    private FloatingActionButton fAddBtn;
    private Dialog them;
    public static Notification notification;
    private KhoaAdapter_R khoaAdapter_r;
    private RecyclerView rv_khoa;
    private HandleLoadEmtpy handleLoadEmtpy;
    public static int khoaSelected = 0;

    public QLNganh() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLNganh.
     */
    // TODO: Rename and change types and number of parameters
    public static QLNganh newInstance(String param1, String param2) {
        QLNganh fragment = new QLNganh();
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
        getView = inflater.inflate(R.layout.fragment_q_l_nganh, container, false);

        qlnganh_rv = getView.findViewById(R.id.rv_nganh);
        fAddBtn = getView.findViewById(R.id.fAddBtn);
        rv_khoa = getView.findViewById(R.id.list_khoa);
        handleLoadEmtpy = new HandleLoadEmtpy(
                getView.findViewById(R.id.sv_load_sv),
                qlnganh_rv,
                getView.findViewById(R.id.sv_0)
        );
        LoadDSNGanhByKhoaId(0);

        notification = new Notification(
                getView.findViewById(R.id.notification),
                getView.findViewById(R.id.card_notification)
        );

        fAddBtn.setOnClickListener(view -> {
            ThemNganhDialog();
        });

        return getView;
    }

    private void initKhoaSpinner(Spinner danhsachkhoa) {
        ArrayList<Khoa> list = new ArrayList<>();
        list.add(0, new Khoa( 0,"Chọn khoa"));
        ArrayAdapter<Khoa> arrayAdapter = new ArrayAdapter(getContext(), R.layout.item_boloc_dialog, list);
        danhsachkhoa.setAdapter(arrayAdapter);

        Call<List<Khoa>> call = mAPIService.LoadDSKhoa();
        call.enqueue(new Callback<List<Khoa>>() {
            @Override
            public void onResponse(Call<List<Khoa>> call, Response<List<Khoa>> response) {
                ArrayList<Khoa> list = (ArrayList<Khoa>) response.body();
                list.add(0, new Khoa( 0,"Chọn khoa"));

                ArrayAdapter<Khoa> arrayAdapter = new ArrayAdapter(getContext(), R.layout.item_boloc_dialog, list);
                danhsachkhoa.setAdapter(arrayAdapter);
                danhsachkhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Khoa khoa = (Khoa) adapterView.getItemAtPosition(i);
                        khoaSelected = khoa.getId();
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

    private void ThemNganhDialog() {
        them = new Dialog(getContext());
        them.requestWindowFeature(Window.FEATURE_NO_TITLE);
        them.setContentView(R.layout.dialog_them_nganh);

        Window window = them.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowsAttributes = window.getAttributes();
        windowsAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowsAttributes);

        them.show();

        Spinner danhsachkhoa = them.findViewById(R.id.spinnerkhoa);
        EditText them_input = them.findViewById(R.id.them_input);
        TextView valid_ten = them.findViewById(R.id.valid_ten),
                valid_khoa = them.findViewById(R.id.valid_khoa);
        Button btnHuy = them.findViewById(R.id.btnHuy),
                btnThem = them.findViewById((R.id.btnThem));

        initKhoaSpinner(danhsachkhoa);

        btnHuy.setOnClickListener(view -> {
            them.cancel();
        });

        btnThem.setOnClickListener(view -> {
            String err1 = ValidData.IsEmpty(them_input.getText().toString());
            String err2 = ValidData.IsSpinnerEmpty(khoaSelected);
            ValidAction(them_input, valid_ten, err1);
            ValidAction(danhsachkhoa, valid_khoa, err2);

            if (!err1.isEmpty() || !err2.isEmpty()) return;

            Nganh nganh = new Nganh(them_input.getText().toString());
            nganh.setMaKhoa(khoaSelected);
            Them(nganh);
        });
    }

    private void Them(Nganh nganh) {
        Call<Nganh> call = mAPIService.InsertNganh(nganh);
        call.enqueue(new Callback<Nganh>() {
            @Override
            public void onResponse(Call<Nganh> call, Response<Nganh> response) {
                LoadDSNGanhByKhoaId2(khoaSelected);
                khoaAdapter_r.setSelected(khoaSelected);
                khoaAdapter_r.notifyDataSetChanged();
                notification.Notify("Thêm thành công!", "success");

                khoaSelected = 0;
            }

            @Override
            public void onFailure(Call<Nganh> call, Throwable t) {
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

    private void ValidAction(Spinner spinner, TextView textView, String check) {
        if (!check.isEmpty()) {
            if(spinner.isEnabled()) spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner_invalid));
            else spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled_invalid));
            textView.setText(check);
            return;
        }

        if(spinner.isEnabled()) spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner));
        else spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled));
        textView.setText("");
    }

    private void LoadDSNGanhByKhoaId(int khoaId) {
        Call<ArrayList<Nganh>> call = mAPIService.LoadDSNganhByKhoaId(khoaId);
        call.enqueue(new Callback<ArrayList<Nganh>>() {
            @Override
            public void onResponse(Call<ArrayList<Nganh>> call, Response<ArrayList<Nganh>> response) {
                List<Nganh> list = response.body();

                qlNganhAdapter_r = new QLNganhAdapter_R(list, getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                qlnganh_rv.setLayoutManager(linearLayoutManager);
                qlnganh_rv.setAdapter(qlNganhAdapter_r);
                qlnganh_rv.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                LoadDSKhoa();
            }

            @Override
            public void onFailure(Call<ArrayList<Nganh>> call, Throwable t) {

            }
        });
    }

    private void LoadDSNGanhByKhoaId2(int khoaId) {
        handleLoadEmtpy.empty(1);
        handleLoadEmtpy.HandleLoadAnimation_r(true);
        Call<ArrayList<Nganh>> call = mAPIService.LoadDSNganhByKhoaId(khoaId);
        call.enqueue(new Callback<ArrayList<Nganh>>() {
            @Override
            public void onResponse(Call<ArrayList<Nganh>> call, Response<ArrayList<Nganh>> response) {
                List<Nganh> list = response.body();
                qlNganhAdapter_r.setListNganh(list);
                qlNganhAdapter_r.notifyDataSetChanged();
                handleLoadEmtpy.HandleLoadAnimation_r(false);
                handleLoadEmtpy.empty(list.size());
            }

            @Override
            public void onFailure(Call<ArrayList<Nganh>> call, Throwable t) {

            }
        });
    }

    private void LoadDSKhoa() {
        Call<List<Khoa>> call = mAPIService.LoadDSKhoa();
        call.enqueue(new Callback<List<Khoa>>() {
            @Override
            public void onResponse(Call<List<Khoa>> call, Response<List<Khoa>> response) {
                List<Khoa> list = response.body();
                list.add(0, new Khoa(0, "Tất cả"));

                khoaAdapter_r = new KhoaAdapter_R(list);
                khoaAdapter_r.setmAPIService(mAPIService);
                khoaAdapter_r.setHandleLoadEmtpy(handleLoadEmtpy);
                khoaAdapter_r.setQlNganhAdapter_r(qlNganhAdapter_r);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                rv_khoa.setLayoutManager(linearLayoutManager);
                rv_khoa.setAdapter(khoaAdapter_r);

                qlNganhAdapter_r.setKhoaAdapter_r(khoaAdapter_r);
                qlNganhAdapter_r.setHandleLoadEmtpy(handleLoadEmtpy);
            }

            @Override
            public void onFailure(Call<List<Khoa>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}