package com.example.ckltdd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ckltdd.Fragment.QLKhoa;
import com.example.ckltdd.Fragment.QLSinhVien;
import com.example.ckltdd.RecycleViewAdapter.KhoaAdapter_R;
import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final int FRAGMENT_QLSV = 1;
    public static final int FRAGMENT_QLK = 2;
    public static final int FRAGMENT_QLL = 3;
    public static final int FRAGMENT_QLN = 4;

    private ImageButton searchBtn, menuBtn, menu_back;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    public int currentFragment = FRAGMENT_QLSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setColorStatusBar();

        replaceFragment(new QLSinhVien());
        searchBtn = findViewById(R.id.main_search);
        searchBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TimKiem.class);
            startActivity(intent);
        });

        Menu();
    }

    private void Menu() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawer(GravityCompat.START);

                        int id = menuItem.getItemId();

                        if (id == R.id.nav_khoa) {
                            if (FRAGMENT_QLK != currentFragment) {
                                replaceFragment(new QLKhoa());
                                currentFragment = FRAGMENT_QLK;
                            }
                        }

                        if (id == R.id.nav_sv) {
                            if (FRAGMENT_QLSV != currentFragment) {
                                replaceFragment(new QLSinhVien());
                                currentFragment = FRAGMENT_QLSV;
                            }
                        }

                        return true;
                    }
                });

        menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(view -> {
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) mDrawerLayout.closeDrawer(GravityCompat.START);
            else mDrawerLayout.openDrawer(GravityCompat.START);
        });

        View headerLayout = navigationView.getHeaderView(0);

        menu_back = headerLayout.findViewById(R.id.menu_back);
        menu_back.setOnClickListener(view -> {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.commit();
    }

    private void setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.white));
    }
}