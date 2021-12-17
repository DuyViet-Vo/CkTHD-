package com.example.ckltdd;

public class sinhvien {
    private String tensv;
    private String masv;
    private int hinh;

    public String getTensv() {
        return tensv;
    }

    public void setTensv(String tensv) {
        this.tensv = tensv;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public sinhvien(String tensv, String masv, int hinh) {
        this.tensv = tensv;
        this.masv = masv;
        this.hinh = hinh;
    }
    
}
