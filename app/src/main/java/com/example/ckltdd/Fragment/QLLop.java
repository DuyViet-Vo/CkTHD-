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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ckltdd.HandleLoadEmtpy;
import com.example.ckltdd.Khoa;
import com.example.ckltdd.Lop;
import com.example.ckltdd.Nganh;
import com.example.ckltdd.Notification;
import com.example.ckltdd.R;
import com.example.ckltdd.RecycleViewAdapter.KhoaAdapter_R;
import com.example.ckltdd.RecycleViewAdapter.QLLopAdapter_R;
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
 * Use the {@link QLLop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLLop extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIServices mAPIService = APIUtils.getAPIService();
    private QLLopAdapter_R qlLopAdapter_r;
    private RecyclerView qllop_rv;
    private View getView;
    private FloatingActionButton fAddBtn;
    private Dialog them;
    public static Notification notification;
    private KhoaAdapter_R khoaAdapter_r;
    private RecyclerView rv_khoa;
    private HandleLoadEmtpy handleLoadEmtpy;
    private Spinner danhsachnganh;
    private ArrayAdapter<Nganh> nganhSpinnerAdapter;
    public static int khoaSelected = 0;
    public static int nganhSelected = 0;

    public QLLop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLLop.
     */
    // TODO: Rename and change types and number of parameters
    public static QLLop newInstance(String param1, String param2) {
        QLLop fragment = new QLLop();
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
        getView = inflater.inflate(R.layout.fragment_q_l_lop, container, false);

        qllop_rv = getView.findViewById(R.id.rv_lop);
        fAddBtn = getView.findViewById(R.id.fAddBtn);
        rv_khoa = getView.findViewById(R.id.list_khoa);
        handleLoadEmtpy = new HandleLoadEmtpy(
                getView.findViewById(R.id.sv_load_sv),
                qllop_rv,
                getView.findViewById(R.id.sv_0)
        );
        danhsachnganh = getView.findViewById(R.id.spinnernganh);

        ArrayList<Nganh> list = new ArrayList<>();
        list.add(0, new Nganh( 0,"Chọn ngành"));
        nganhSpinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.item_boloc, list);
        danhsachnganh.setAdapter(nganhSpinnerAdapter);

        LoadDSLopByNganhId(0);
        LoadDSNGanhByKhoaId(0);

        notification = new Notification(
                getView.findViewById(R.id.notification),
                getView.findViewById(R.id.card_notification)
        );

        fAddBtn.setOnClickListener(view -> {
            ThemLopDialog();
        });

        return getView;
    }

    private void ThemLopDialog() {
        them = new Dialog(getContext());
        them.requestWindowFeature(Window.FEATURE_NO_TITLE);
        them.setContentView(R.layout.dialog_them_lop);

        Window window = them.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowsAttributes = window.getAttributes();
        windowsAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowsAttributes);

        them.show();

        Spinner danhsachkhoa = them.findViewById(R.id.spinnerkhoa);
        Spinner danhsachnganh = them.findViewById(R.id.spinnernganh);
        EditText them_input = them.findViewById(R.id.them_input);
        TextView valid_ten = them.findViewById(R.id.valid_ten),
                valid_khoa = them.findViewById(R.id.valid_khoa),
                valid_nganh = them.findViewById(R.id.valid_nganh);
        Button btnHuy = them.findViewById(R.id.btnHuy),
                btnThem = them.findViewById((R.id.btnThem));

        initKhoaSpinner(danhsachkhoa, danhsachnganh, valid_nganh);
        initNganhSpinner(danhsachnganh);

        btnHuy.setOnClickListener(view -> {
            them.cancel();
        });

        btnThem.setOnClickListener(view -> {
            String err1 = ValidData.IsEmpty(them_input.getText().toString());
            String err2 = ValidData.IsSpinnerEmpty(khoaSelected);
            String err3 = ValidData.IsSpinnerEmpty(nganhSelected);
            ValidAction(them_input, valid_ten, err1);
            ValidAction(danhsachkhoa, valid_khoa, err2);
            ValidAction(danhsachnganh, valid_nganh, err3);

            if (!err1.isEmpty() || !err2.isEmpty() || !err3.isEmpty()) return;

            Lop lop = new Lop(them_input.getText().toString());
            lop.setMaNganh(nganhSelected);
            Them(lop);
        });
    }

    private void Them(Lop lop) {
        Call<Lop> call = mAPIService.InsertLop(lop);
        call.enqueue(new Callback<Lop>() {
            @Override
            public void onResponse(Call<Lop> call, Response<Lop> response) {
                LoadDSLopByNganhId_2(nganhSelected);
                khoaAdapter_r.setSelected(khoaSelected);
                khoaAdapter_r.notifyDataSetChanged();
                notification.Notify("Thêm thành công!", "success");

                khoaSelected = 0;
                nganhSelected = 0;
            }

            @Override
            public void onFailure(Call<Lop> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        them.cancel();
    }

    private void initKhoaSpinner(Spinner danhsachkhoa, Spinner danhsachnganh, TextView tv_nganh) {
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

                arrayAdapter.clear();
                arrayAdapter.addAll(list);

                danhsachkhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Khoa khoa = (Khoa) adapterView.getItemAtPosition(i);
                        khoaSelected = khoa.getId();

                        if (khoa.getId() != 0) {
                            LoadDSNGanhByKhoaId(khoa.getId(), danhsachnganh);
                            danhsachnganh.setEnabled(true);
                            if (!tv_nganh.getText().toString().isEmpty())
                                danhsachnganh.setBackground(getResources().getDrawable(R.drawable.bg_spinner_invalid));
                            else
                                danhsachnganh.setBackground(getResources().getDrawable(R.drawable.bg_spinner));
                        } else {
                            danhsachnganh.setEnabled(false);
                            if (tv_nganh.getText().toString().isEmpty())
                                danhsachnganh.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled));
                            else
                                danhsachnganh.setBackground(getResources().getDrawable(R.drawable.bg_spinner_disabled_invalid));
                        }

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

    private void LoadDSNGanhByKhoaId(int khoaId, Spinner danhsachnganh) {
        Call<ArrayList<Nganh>> call = mAPIService.LoadDSNganhByKhoaId(khoaId);
        call.enqueue(new Callback<ArrayList<Nganh>>() {
            @Override
            public void onResponse(Call<ArrayList<Nganh>> call, Response<ArrayList<Nganh>> response) {
                ArrayList<Nganh> list = response.body();
                list.add(0, new Nganh( 0,"Chọn ngành"));
                ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_boloc_dialog, list);
                danhsachnganh.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Nganh>> call, Throwable t) {

            }
        });
    }

    private void initNganhSpinner(Spinner danhsachnganh) {
        ArrayList<Nganh> list = new ArrayList<>();
        list.add(0, new Nganh( 0,"Chọn Ngành"));

        ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter(getContext(), R.layout.item_boloc_dialog, list);
        danhsachnganh.setEnabled(false);
        danhsachnganh.setAdapter(arrayAdapter);
        danhsachnganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Nganh nganh = (Nganh) adapterView.getItemAtPosition(i);
                nganhSelected = nganh.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

    private void LoadDSLopByNganhId(int id) {
        Call<ArrayList<Lop>> call = mAPIService.LoadDSLopByNganhId(id);
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();

                qlLopAdapter_r = new QLLopAdapter_R(list, getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                qllop_rv.setLayoutManager(linearLayoutManager);
                qllop_rv.setAdapter(qlLopAdapter_r);
                qllop_rv.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
                LoadDSKhoa();
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void LoadDSLopByNganhId_2(int id) {
        handleLoadEmtpy.empty(1);
        handleLoadEmtpy.HandleLoadAnimation_r(true);
        Call<ArrayList<Lop>> call = mAPIService.LoadDSLopByNganhId(id);
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();

                qlLopAdapter_r.setListLop(list);
                qlLopAdapter_r.notifyDataSetChanged();
                handleLoadEmtpy.HandleLoadAnimation_r(false);
                handleLoadEmtpy.empty(list.size());
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {
                System.out.println(t.getMessage());
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
                nganhSpinnerAdapter.clear();
                nganhSpinnerAdapter.addAll(list);

                danhsachnganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Nganh nganh = (Nganh) adapterView.getItemAtPosition(i);
                        LoadDSLopByNganhId_2(nganh.getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
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
                khoaAdapter_r.setQlLopAdapter_r(qlLopAdapter_r);
                khoaAdapter_r.setNganhSpinnerAdapter(nganhSpinnerAdapter);
                khoaAdapter_r.setNganhSpinner(danhsachnganh);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                rv_khoa.setLayoutManager(linearLayoutManager);
                rv_khoa.setAdapter(khoaAdapter_r);

                qlLopAdapter_r.setKhoaAdapter_r(khoaAdapter_r);
                qlLopAdapter_r.setHandleLoadEmtpy(handleLoadEmtpy);
                qlLopAdapter_r.setNganhSpinner(danhsachnganh);
            }

            @Override
            public void onFailure(Call<List<Khoa>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }

}