package com.example.ckltdd.Retrofit2;

import com.example.ckltdd.Admin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIServices {
    @POST("admin/login")
    Call<Admin> Login(@Body Admin account);
}
