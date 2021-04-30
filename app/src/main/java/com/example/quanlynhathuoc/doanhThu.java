package com.example.quanlynhathuoc;

public class doanhThu {
    String maNT,tenNT,thoiGian;
    float doanhThu;
    public doanhThu() {
    }

    public doanhThu(String maNT, String tenNT, String thoiGian, float doanhThu) {
        this.maNT = maNT;
        this.tenNT = tenNT;
        this.thoiGian = thoiGian;
        this.doanhThu = doanhThu;
    }

    public String getMaNT() {
        return maNT;
    }

    public void setMaNT(String maNT) {
        this.maNT = maNT;
    }

    public String getTenNT() {
        return tenNT;
    }

    public void setTenNT(String tenNT) {
        this.tenNT = tenNT;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public float getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(float doanhThu) {
        this.doanhThu = doanhThu;
    }
}
