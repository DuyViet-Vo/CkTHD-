package com.example.ckltdd;

public class Lop {
    private int id, maNganh, maKhoa;
    private String tenLop;

    public Lop(int id, int maNganh, String tenLop) {
        this.id = id;
        this.maNganh = maNganh;
        this.tenLop = tenLop;
    }

    public Lop(int id, int maNganh, int maKhoa, String tenLop) {
        this.id = id;
        this.maNganh = maNganh;
        this.maKhoa = maKhoa;
        this.tenLop = tenLop;
    }

    public Lop(String tenLop) {
        this.tenLop = tenLop;
    }

    public Lop(int id, String tenLop) {
        this.id = id;
        this.tenLop = tenLop;
    }

    public int getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(int maKhoa) {
        this.maKhoa = maKhoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(int maNganh) {
        this.maNganh = maNganh;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    @Override
    public String toString() {
        return tenLop;
    }
}
