package com.example.ckltdd;

public class sinhvien {
    private int id, gioiTinh;
    private String hoTen, ngaySinh, email, sdt, diaChi, CCCD , andDaiDien, lop;

    public sinhvien(int id, int gioiTinh, String hoTen, String ngaySinh, String email, String sdt, String diaChi, String CCCD, String andDaiDien, String lop) {
        this.id = id;
        this.gioiTinh = gioiTinh;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.CCCD = CCCD;
        this.andDaiDien = andDaiDien;
        this.lop = lop;
    }

    public sinhvien(int id, String hoTen, String andDaiDien) {
        this.id = id;
        this.hoTen = hoTen;
        this.andDaiDien = andDaiDien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getAndDaiDien() {
        return andDaiDien;
    }

    public void setAndDaiDien(String andDaiDien) {
        this.andDaiDien = andDaiDien;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    //    private String tensv, masv;
//    private int hinh;
//
//    public String getTensv() {
//        return tensv;
//    }
//
//    public void setTensv(String tensv) {
//        this.tensv = tensv;
//    }
//
//    public String getMasv() {
//        return masv;
//    }
//
//    public void setMasv(String masv) {
//        this.masv = masv;
//    }
//
//    public int getHinh() {
//        return hinh;
//    }
//
//    public void setHinh(int hinh) {
//        this.hinh = hinh;
//    }
//
//    public sinhvien(String tensv, String masv, int hinh) {
//        this.tensv = tensv;
//        this.masv = masv;
//        this.hinh = hinh;
//    }

}
