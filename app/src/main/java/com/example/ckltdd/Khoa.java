package com.example.ckltdd;

public class Khoa {
    private int id;
    private String tenKhoa;


    public String getTenkhoa() {
        return tenKhoa;
    }

    public void setTenkhoa(String tenkhoa) {
        this.tenKhoa = tenkhoa;
    }

    public Khoa(String tenkhoa) {
        this.tenKhoa = tenkhoa;
    }

    public Khoa(int id, String tenkhoa) {
        this.id = id;
        this.tenKhoa = tenkhoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Khoa{" +
                "id=" + id +
                ", tenkhoa='" + tenKhoa + '\'' +
                '}';
    }
}
