package com.example.ckltdd;

public class Nganh {
    private int id, maKhoa;
    private String tenNganh;

    public Nganh(int id, int maKhoa, String tenNganh) {
        this.id = id;
        this.maKhoa = maKhoa;
        this.tenNganh = tenNganh;
    }

    public Nganh(int id, String tenNganh) {
        this.id = id;
        this.tenNganh = tenNganh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(int maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    @Override
    public String toString() {
        return  tenNganh;
    }
}
