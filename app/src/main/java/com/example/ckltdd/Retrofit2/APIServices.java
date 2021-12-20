package com.example.ckltdd.Retrofit2;

import com.example.ckltdd.Admin;
import com.example.ckltdd.Khoa;
import com.example.ckltdd.SinhVien;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIServices {
    @POST("admin/login")
    Call<Admin> Login(@Body Admin account);

    @GET("sinhvien")
    Call<List<SinhVien>> LoadStudents();

    @GET("khoa")
    Call<List<Khoa>> LoadDSKhoa();

    @POST("khoa/sinhvien")
    Call<List<SinhVien>> LoadStudentsByKhoaId(@Query("maKhoa") int maKhoa);
}
