package com.example.duanmau.DTO;

public class ThanhVien {
    String maTV;
    String tenTV;
    String NSinh;

    public ThanhVien(String maTV, String tenTV, String NSinh) {
        this.maTV = maTV;
        this.tenTV = tenTV;
        this.NSinh = NSinh;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getNSinh() {
        return NSinh;
    }

    public void setNSinh(String NSinh) {
        this.NSinh = NSinh;
    }
}
