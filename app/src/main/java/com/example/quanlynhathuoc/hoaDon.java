package com.example.quanlynhathuoc;

public class hoaDon {
    String maHD,ngayHD,maNT;

    public hoaDon(String maHD, String ngayHD, String maNT) {
        this.maHD = maHD;
        this.ngayHD = ngayHD;
        this.maNT = maNT;
    }

    public hoaDon() {
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getNgayHD() {
        return ngayHD;
    }

    public void setNgayHD(String ngayHD) {
        this.ngayHD = ngayHD;
    }

    public String getMaNT() {
        return maNT;
    }

    public void setMaNT(String maNT) {
        this.maNT = maNT;
    }
}
