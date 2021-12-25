package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.ckltdd.Fragment.QLKhoa;
import com.example.ckltdd.Fragment.QLSinhVien;
import com.google.android.material.navigation.NavigationView;

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