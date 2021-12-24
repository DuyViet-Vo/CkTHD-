package com.example.ckltdd;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SinhVien {
    private String id, hoTen, ngaySinh, email, sdt, diaChi, CCCD, anhDaiDien, tenLop, tenNganh;
    private int maLop, maKhoa, maNganh;
    private byte gioiTinh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public int getMaLop() {
        return maLop;
    }

    public void setMaLop(int maLop) {
        this.maLop = maLop;
    }

    public byte getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(byte gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public int getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(int maKhoa) {
        this.maKhoa = maKhoa;
    }

    public int getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(int maNganh) {
        this.maNganh = maNganh;
    }

    public SinhVien() {
    }

    public SinhVien(String id, String hoTen, String ngaySinh, String email, String sdt, String diaChi, String CCCD, String anhDaiDien, int maLop, byte gioiTinh) {
        this.id = id;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.CCCD = CCCD;
        this.anhDaiDien = anhDaiDien;
        this.maLop = maLop;
        this.gioiTinh = gioiTinh;
    }

    public SinhVien(String id, String hoTen, byte gioiTinh, String CCCD, String sdt, String email, String diaChi,  int maLop, String ngaySinh) {
        this.id = id;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.CCCD = CCCD;
        this.anhDaiDien = anhDaiDien;
        this.maLop = maLop;
        this.gioiTinh = gioiTinh;
    }

    public SinhVien(String id, String hoTen, String ngaySinh, String email, String sdt, String diaChi, String CCCD, String anhDaiDien, String tenLop, String tenNganh, int maLop, byte gioiTinh) {
        this.id = id;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.CCCD = CCCD;
        this.anhDaiDien = anhDaiDien;
        this.tenLop = tenLop;
        this.tenNganh = tenNganh;
        this.maLop = maLop;
        this.gioiTinh = gioiTinh;
    }

    public SinhVien(String id, String hoTen, String ngaySinh, String email, String sdt, String diaChi, String CCCD, String anhDaiDien, String tenLop, String tenNganh, int maLop, int maKhoa, int maNganh, byte gioiTinh) {
        this.id = id;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.CCCD = CCCD;
        this.anhDaiDien = anhDaiDien;
        this.tenLop = tenLop;
        this.tenNganh = tenNganh;
        this.maLop = maLop;
        this.maKhoa = maKhoa;
        this.maNganh = maNganh;
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "id='" + id + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", email='" + email + '\'' +
                ", sdt='" + sdt + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", CCCD='" + CCCD + '\'' +
                ", anhDaiDien='" + anhDaiDien + '\'' +
                ", tenLop='" + tenLop + '\'' +
                ", tenNganh='" + tenNganh + '\'' +
                ", maLop=" + maLop +
                ", gioiTinh=" + gioiTinh +
                '}';
    }
}
