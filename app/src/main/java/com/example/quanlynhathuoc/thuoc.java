package com.example.quanlynhathuoc;

public class thuoc {
    String maThuoc,tenThuoc,DVT;
    float donGia;
    byte[] imageMedical;

    public thuoc(String maThuoc, String tenThuoc, String DVT, float donGia, byte[] imageMedical) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.DVT = DVT;
        this.donGia = donGia;
    }

    public thuoc() {
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getDVT() {
        return DVT;
    }

    public void setDVT(String DVT) {
        this.DVT = DVT;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public byte[] getImageMedical() {
        return imageMedical;
    }

    public void setImageMedical(byte[] imageMedical) {
        this.imageMedical = imageMedical;
    }
}
