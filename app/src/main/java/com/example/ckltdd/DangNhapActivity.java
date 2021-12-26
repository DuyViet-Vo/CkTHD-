package com.example.ckltdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ckltdd.Retrofit2.APIServices;
import com.example.ckltdd.Retrofit2.APIUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapActivity extends AppCompatActivity {
    private Button btnDangNhap;
    private EditText userName, password;
    private APIServices mAPIService;
    private HandleLoadEmtpy handleLoadEmtpy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        setColorStatusBar();
        handleLoadEmtpy = new HandleLoadEmtpy(findViewById(R.id.login_load));

        init();
    }

    private void init() {
        btnDangNhap = (Button) findViewById(R.id.btndangnhap);
        btnDangNhap.setOnClickListener(view -> {
            loginRequest();
        });

        userName = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);
        mAPIService = APIUtils.getAPIService();
    }

    private void setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(DangNhapActivity.this,R.color.white));
    }

    private void loginRequest() {
        Admin admin = new Admin(userName.getText() + "", password.getText()+ "");

        Call<Admin> call = mAPIService.Login(admin);
        call.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if(response.body().getTaiKhoan() != null) {
                    Intent intent = new Intent(DangNhapActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DangNhapActivity.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                Toast.makeText(DangNhapActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}