package com.example.ckltdd.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ckltdd.HandleLoadEmtpy;
import com.example.ckltdd.Khoa;
import com.example.ckltdd.Lop;
import com.example.ckltdd.MainActivity;
import com.example.ckltdd.Nganh;
import com.example.ckltdd.Notification;
import com.example.ckltdd.R;
import com.example.ckltdd.RecycleViewAdapter.KhoaAdapter_R;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;
import com.example.ckltdd.SinhVien;
import com.example.ckltdd.Them;
import com.example.ckltdd.TimKiem;
import com.example.ckltdd.sinhVienAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLSinhVien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLSinhVien extends Fragment {

    private ListView listViewsinhvien;
    private List<SinhVien> sinhVienArrayList;
    private sinhVienAdapter svAdapter;
    private ImageButton button_loc;
    private FloatingActionButton fab_them;
    private APIServices mAPIService = APIUtils.getAPIService();
    private TextView txtLop;
    private Button notification;
    private CardView card_notification;
    public static Notification mNotification;

    private KhoaAdapter_R khoaAdapter_r;
    private RecyclerView rv_khoa;
    private HandleLoadEmtpy handleLoadEmtpy;

    private Dialog locDialog;

    private AutoCompleteTextView lopSpinner, nganhSpinner;

    public static int khoaId = 0, nganhId = 0, lopId = 0;
    public static String nganhLoc, lopLoc;
    private int REQUEST_CODE = 111;
    private int REQUEST_CODE_EDIT = 112;

    private View getView;
    private Fragment fragment = this;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLSinhVien() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLSinhVien.
     */
    // TODO: Rename and change types and number of parameters
    public static QLSinhVien newInstance(String param1, String param2) {
        QLSinhVien fragment = new QLSinhVien();
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
        View rootView = inflater.inflate(R.layout.fragment_q_l_sinh_vien, container, false);
        getView = rootView;

        handleLoadEmtpy = new HandleLoadEmtpy(
                rootView.findViewById(R.id.sv_load_sv),
                (ListView) rootView.findViewById(R.id.lvsinhvien),
                rootView.findViewById(R.id.sv_0)
        );
        txtLop = rootView.findViewById(R.id.txtlop);
        notification = rootView.findViewById(R.id.notification);
        card_notification = rootView.findViewById(R.id.card_notification);

        mNotification = new Notification(notification, card_notification);

        //them
        fab_them = (FloatingActionButton) rootView.findViewById(R.id.fAddBtn) ;
        fab_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), Them.class), REQUEST_CODE);
            }
        });
        button_loc = rootView.findViewById(R.id.button_loc);
        button_loc.setOnClickListener(view -> {
            LocDialog();
        });

        LoadStudents();
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE && data != null) {
            lopId = data.getIntExtra("idLop", 0);
            lopLoc = data.getStringExtra("tenLop");
            txtLop.setText(lopLoc);
            khoaAdapter_r.setSelected(data.getIntExtra("idKhoa", 0));
            khoaAdapter_r.notifyDataSetChanged();
            LoadStudentsByClassId(khoaId, nganhId, lopId);

            mNotification.Notify("Thêm thành công!", "success");
        }

        if(requestCode == REQUEST_CODE_EDIT &&  data != null) {
            lopId = data.getIntExtra("idLop", 0);
            lopLoc = data.getStringExtra("tenLop");
            txtLop.setText(lopLoc);
            khoaAdapter_r.setSelected(data.getIntExtra("idKhoa", 0));
            khoaAdapter_r.notifyDataSetChanged();
            LoadStudentsByClassId(khoaId, nganhId, lopId);

            mNotification.Notify("Sửa thành công!", "success");
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void LocDialog() {
        locDialog = new Dialog(getContext());
        locDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        locDialog.setContentView(R.layout.dialog_loc);

        Window window = locDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowsAttributes = window.getAttributes();
        windowsAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowsAttributes);

        locDialog.show();

        nganhSpinner = locDialog.findViewById(R.id.nganhSpinner);
        lopSpinner = locDialog.findViewById(R.id.lopSpinner);

        nganhSpinner.setText(nganhLoc);
        lopSpinner.setText(lopLoc);

        LoadDSNganh(nganhSpinner);
        LoadDSLop(lopSpinner);

        Button huyBtn = locDialog.findViewById(R.id.btnHuy),
                locBtn = locDialog.findViewById(R.id.btnLoc);

        huyBtn.setOnClickListener(view -> {
            locDialog.cancel();
        });

        locBtn.setOnClickListener(view -> {
            LoadStudentsByClassId(khoaId, nganhId, lopId);
            locDialog.cancel();


            if (lopId != 0)  {
                txtLop.setText(lopLoc);
                return;
            }

            if (nganhId != 0) {
                txtLop.setText(nganhLoc);
                return;
            }

            if (khoaId != 0) {
                txtLop.setText("Tất cả");
                return;
            }

            txtLop.setText("");
        });
    }



    private void LoadStudentsByClassId(int khoaId, int nganhId, int lopId) {
        handleLoadEmtpy.empty(1);
        handleLoadEmtpy.HandleLoadAnimation(true);
        Call<List<SinhVien>> call = mAPIService.LoadStudentsByClassId(khoaId, nganhId, lopId);
        call.enqueue(new Callback<List<SinhVien>>() {
            @Override
            public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                List<SinhVien> list = response.body();
                svAdapter.setSinhVienList(list);
                svAdapter.notifyDataSetChanged();
                handleLoadEmtpy.HandleLoadAnimation(false);
                handleLoadEmtpy.empty(list.size());
            }

            @Override
            public void onFailure(Call<List<SinhVien>> call, Throwable t) {

            }
        });
    }

    private void LoadDSLop(AutoCompleteTextView spinner) {
        if(nganhId != 0) {
            LoadDSLopByNganhId(nganhId);
            return;
        }

        if (khoaId != 0) {
            LoadDSLopByKhoaId(khoaId);
            return;
        }

        Call<ArrayList<Lop>> call = mAPIService.LoadDSLop();
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();
                list.add(0, new Lop( 0,"Tất cả"));

                ArrayAdapter<Lop> lopAdapter = new ArrayAdapter<>(getContext(), R.layout.item_boloc, list);
                spinner.setAdapter(lopAdapter);
                spinner.setOnItemClickListener((adapterView, view, i, l) -> {
                    Lop lop = (Lop) adapterView.getItemAtPosition(i);
                    lopId = lop.getId();
                    lopLoc = lop.getTenLop();
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {

            }
        });
    }

    private void LoadDSLopByKhoaId(int khoaId) {
        Call<ArrayList<Lop>> call = mAPIService.LoadDSLopByKhoaId(khoaId);
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();
                if(list.size() == 0) lopSpinner.setText("");
                else list.add(0, new Lop( 0,"Tất cả"));

                ArrayAdapter<Lop> lopAdapter = new ArrayAdapter<>(getContext(), R.layout.item_boloc, list);
                lopSpinner.setAdapter(lopAdapter);
                lopSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
                    Lop lop = (Lop) adapterView.getItemAtPosition(i);
                    lopId = lop.getId();
                    lopLoc = lop.getTenLop();
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void LoadDSLopByNganhId(int id) {
        Call<ArrayList<Lop>> call = mAPIService.LoadDSLopByNganhId(id);
        call.enqueue(new Callback<ArrayList<Lop>>() {
            @Override
            public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                ArrayList<Lop> list = response.body();
                if(list.size() == 0) lopSpinner.setText("");
                else list.add(0, new Lop( 0,"Tất cả"));

                ArrayAdapter<Lop> lopAdapter = new ArrayAdapter<>(getContext(), R.layout.item_boloc, list);
                lopSpinner.setAdapter(lopAdapter);
                lopSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
                    Lop lop = (Lop) adapterView.getItemAtPosition(i);
                    lopId = lop.getId();
                    lopLoc = lop.getTenLop();
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void LoadDSNganh(AutoCompleteTextView spinner) {
        if(khoaId != 0) {
            LoadDSNGanhByKhoaId(khoaId);
            return;
        }

        Call<ArrayList<Nganh>> call = mAPIService.LoadDSNganh();
        call.enqueue(new Callback<ArrayList<Nganh>>() {
            @Override
            public void onResponse(Call<ArrayList<Nganh>> call, Response<ArrayList<Nganh>> response) {
                ArrayList<Nganh> list = response.body();
                list.add(0, new Nganh( 0,"Tất cả"));

                ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_boloc, list);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemClickListener((adapterView, view, i, l) -> {
                    Nganh n = (Nganh) adapterView.getItemAtPosition(i);
                    LoadDSLopByNganhId(n.getId());
                    nganhId = n.getId();
                    nganhLoc = n.getTenNganh();
                    lopId = 0;
                    lopLoc = "";
                    lopSpinner.setText("");
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Nganh>> call, Throwable t) {

            }
        });
    }

    private void LoadDSNGanhByKhoaId(int khoaId) {
        Call<ArrayList<Nganh>> call = mAPIService.LoadDSNganhByKhoaId(khoaId);
        call.enqueue(new Callback<ArrayList<Nganh>>() {
            @Override
            public void onResponse(Call<ArrayList<Nganh>> call, Response<ArrayList<Nganh>> response) {
                ArrayList<Nganh> list = response.body();
                list.add(0, new Nganh( 0,"Tất cả"));

                ArrayAdapter<Nganh> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_boloc, list);
                nganhSpinner.setAdapter(arrayAdapter);
                nganhSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
                    Nganh n = (Nganh) adapterView.getItemAtPosition(i);
                    LoadDSLopByNganhId(n.getId());
                    nganhId = n.getId();
                    nganhLoc = n.getTenNganh();
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
                list.add(0, new Khoa( 0,"Tất cả"));

                khoaAdapter_r = new KhoaAdapter_R(list);
                khoaAdapter_r.setmAPIService(mAPIService);
                khoaAdapter_r.setListViewsinhvien(listViewsinhvien);
                khoaAdapter_r.setSvAdapter(svAdapter);
                khoaAdapter_r.setHandleLoadEmtpy(handleLoadEmtpy);
                khoaAdapter_r.setTxtLop(txtLop);
                rv_khoa = (RecyclerView) getView.findViewById(R.id.list_khoa);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                rv_khoa.setLayoutManager(linearLayoutManager);
                rv_khoa.setAdapter(khoaAdapter_r);

                svAdapter.setHandleLoadEmtpy(handleLoadEmtpy);
                svAdapter.setKhoaAdapter_r(khoaAdapter_r);
            }

            @Override
            public void onFailure(Call<List<Khoa>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });

    }

    private void LoadStudents(){
        listViewsinhvien = getView.findViewById(R.id.lvsinhvien);
        sinhVienArrayList = new ArrayList<>();

        Call<List<SinhVien>> call = mAPIService.LoadStudents();
        call.enqueue(new Callback<List<SinhVien>>() {
            @Override
            public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                sinhVienArrayList = response.body();

                svAdapter = new sinhVienAdapter(getContext(),R.layout.item_sinhvien, sinhVienArrayList);
                svAdapter.setFragment(fragment);
                listViewsinhvien.setAdapter(svAdapter);
                listViewsinhvien.setTranscriptMode(0);
                LoadDSKhoa();
            }

            @Override
            public void onFailure(Call<List<SinhVien>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}