package com.example.duanmau.DTO;

public class PhieuMuon {
    private String maPM;
    private String maTV;
    private String tenTV;
    private  String maTT;
    private String tenTT;
    private String maSach;
    private String tenSach;
    private String ngay;
    private String trangThai;
    int tien;

    public PhieuMuon(String maPM, String maTV, String maTT, String maSach, String ngay, String trangThai, int tien) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maTT = maTT;
        this.maSach = maSach;
        this.ngay = ngay;
        this.trangThai = trangThai;
        this.tien = tien;
    }

    public PhieuMuon(String maPM, String maTV, String tenTV, String maTT, String tenTT, String maSach, String tenSach, String ngay, String trangThai, int tien) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.tenTV = tenTV;
        this.maTT = maTT;
        this.tenTT = tenTT;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.ngay = ngay;
        this.trangThai = trangThai;
        this.tien = tien;
    }

    public String getMaPM() {
        return maPM;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String  maTV) {
        this.maTV = maTV;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
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

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }
}
