package com.example.ckltdd.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
        LoadDSNGanhByKhoaId(0);
        LoadDSKhoa();

        notification = new Notification(
                getView.findViewById(R.id.notification),
                getView.findViewById(R.id.card_notification)
        );

        fAddBtn.setOnClickListener(view -> {
//            ThemKhoaDialog();
        });

        return getView;
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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                rv_khoa.setLayoutManager(linearLayoutManager);
                rv_khoa.setAdapter(khoaAdapter_r);
            }

            @Override
            public void onFailure(Call<List<Khoa>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}