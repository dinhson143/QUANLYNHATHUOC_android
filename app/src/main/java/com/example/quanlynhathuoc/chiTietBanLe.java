package com.example.quanlynhathuoc;

public class chiTietBanLe {
    String soHD,maThuoc;
    int soLuong;
    public chiTietBanLe(String soHD, String maThuoc, int soLuong) {
        this.soHD = soHD;
        this.maThuoc = maThuoc;
        this.soLuong = soLuong;
    }

    public chiTietBanLe() {
    }

    public String getSoHD() {
        return soHD;
    }

    public void setSoHD(String soHD) {
        this.soHD = soHD;
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
