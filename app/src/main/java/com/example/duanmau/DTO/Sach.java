package com.example.duanmau.DTO;

public class Sach {
    private String maSach;
    private String tenSach;
    private int gia;
    private int maLS;
    private String tenLoai;

    private int soLuongMuon;

    public Sach(String maSach, String tenSach, int gia, int maLS, String tenLoai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.gia = gia;
        this.maLS = maLS;
        this.tenLoai = tenLoai;
    }

    public Sach(String maSach, String tenSach, int gia, int maLS) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.gia = gia;
        this.maLS = maLS;
    }

    public Sach(String maSach, String tenSach, int soLuongMuon) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLuongMuon = soLuongMuon;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getSoLuongMuon() {
        return soLuongMuon;
    }

    public void setSoLuongMuon(int soLuongMuon) {
        this.soLuongMuon = soLuongMuon;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getMaLS() {
        return maLS;
    }

    public void setMaLS(int maLS) {
        this.maLS = maLS;
    }
}
