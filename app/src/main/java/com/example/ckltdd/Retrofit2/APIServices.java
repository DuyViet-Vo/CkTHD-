package com.example.ckltdd.Retrofit2;

import com.example.ckltdd.Admin;
import com.example.ckltdd.Khoa;
import com.example.ckltdd.Lop;
import com.example.ckltdd.Nganh;
import com.example.ckltdd.SinhVien;

import java.util.ArrayList;
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

    @GET("nganh")
    Call<ArrayList<Nganh>> LoadDSNganh();

    @GET("lop")
    Call<ArrayList<Lop>> LoadDSLop();

    @POST("khoa/sinhvien")
    Call<List<SinhVien>> LoadStudentsByKhoaId(@Query("maKhoa") int maKhoa);

    @POST("khoa/nganh")
    Call<ArrayList<Nganh>> LoadDSNganhByKhoaId(@Query("maKhoa") int maKhoa);

    @POST("khoa/lop")
    Call<ArrayList<Lop>> LoadDSLopByKhoaId(@Query("maKhoa") int maKhoa);

    @POST("lop/sinhvien")
    Call<List<SinhVien>> LoadStudentsByClassId(@Query("maKhoa") int maKhoa, @Query("maNganh") int maNganh, @Query("maLop") int maLop);

    @POST("nganh/lop")
    Call<ArrayList<Lop>> LoadDSLopByNganhId(@Query("maNganh") int maNganh);

    @POST("sinhvien/id")
    Call<SinhVien> getStudentById(@Query("maSV") String maSV);

    @POST("sinhvien/insert")
    Call<SinhVien> InsertSV(@Body SinhVien sinhVien);

    @POST("sinhvien/update")
    Call<SinhVien> UpdateSV(@Body SinhVien sinhVien);

    @POST("sinhvien/delete")
    Call<Boolean> DeleteSV(@Query("id") String id);

}
