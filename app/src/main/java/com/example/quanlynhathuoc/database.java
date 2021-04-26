package com.example.quanlynhathuoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


//LỚP XỬ LÍ DỮ LIỆU
public class database extends SQLiteOpenHelper {
    private static String DB_NAME = "dbNhaThuoc.db";
    private static int DB_VERSION = 8;
    private static final String TB_NHATHUOCS = "tbNhaThuoc";
    private static final String COL_NHATHUOC_MANT = "NhaThuoc_MaNT";
    private static final String COL_NHATHUOC_TENNT = "NhaThuoc_TenNT";
    private static final String COL_NHATHUOC_DIACHI = "NhaThuoc_DiaChi";

    private static final String TB_HOADONS = "tbHoaDon";
    private static final String COL_HOADON_SOHD = "HoaDon_SoHD";
    private static final String COL_HOADON_NGAYHD = "HoaDon_NgayHD";
    private static final String COL_HOADON_MANT = "HoaDon_MaNT";

    private static final String TB_THUOCS = "tbThuoc";
    private static final String COL_THUOC_MATHUOC = "Thuoc_MaThuoc";
    private static final String COL_THUOC_TENTHUOC = "Thuoc_TenThuoc";
    private static final String COL_THUOC_DVT = "Thuoc_DVT";
    private static final String COL_THUOC_DONGIA = "Thuoc_DonGia";
    private static final String COL_THUOC_HINHANH = "Thuoc_HinhAnh";

    private static final String TB_CTBLS = "tbCTBL";
    private static final String COL_CTBL_SOHD = "CTBL_SoHD";
    private static final String COL_CTBL_MATHUOC = "CTBL_MaThuoc";
    private static final String COL_CTBL_SOlUONG = "CTBL_SoLuong";

    public database(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ TB_HOADONS);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_NHATHUOCS);
        db.execSQL("DROP TABLE IF EXISTS " + TB_THUOCS);
        db.execSQL("DROP TABLE IF EXISTS " + TB_CTBLS);
        onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String scriptTB_NhaThuocs = "CREATE TABLE "+ TB_NHATHUOCS +"("+ COL_NHATHUOC_MANT + " TEXT PRIMARY KEY,"
                + COL_NHATHUOC_TENNT + " TEXT," + COL_NHATHUOC_DIACHI + " TEXT)";
        String scriptTB_HoaDons = "CREATE TABLE "+ TB_HOADONS + "(" + COL_HOADON_SOHD + " TEXT PRIMARY KEY,"
                + COL_HOADON_NGAYHD + " TEXT," + COL_HOADON_MANT + " TEXT,"
                + "FOREIGN KEY("+ COL_HOADON_MANT +") REFERENCES "+ TB_NHATHUOCS +"("+ COL_NHATHUOC_MANT +"))";
        String scriptTB_Thuocs = "CREATE TABLE " + TB_THUOCS + "(" + COL_THUOC_MATHUOC + " TEXT PRIMARY KEY,"
                + COL_THUOC_TENTHUOC + " TEXT," + COL_THUOC_DVT + " TEXT," + COL_THUOC_DONGIA + " TEXT, "
                + COL_THUOC_HINHANH + " BLOB)";

        String scriptTB_CTBLs = "CREATE TABLE " + TB_CTBLS + "(" + COL_CTBL_SOHD + " TEXT, " + COL_CTBL_MATHUOC
                + " TEXT, " + COL_CTBL_SOlUONG + " INTEGER, PRIMARY KEY(" + COL_CTBL_SOHD + ", " + COL_CTBL_MATHUOC + "), "
                + "FOREIGN KEY(" + COL_CTBL_SOHD + ") REFERENCES " + TB_HOADONS + "(" + COL_HOADON_SOHD + "), "
                + "FOREIGN KEY(" + COL_CTBL_MATHUOC + ") REFERENCES " + TB_THUOCS + "(" + COL_THUOC_MATHUOC + "))";
        db.execSQL(scriptTB_NhaThuocs);
        db.execSQL(scriptTB_HoaDons);
        db.execSQL(scriptTB_Thuocs);
        db.execSQL(scriptTB_CTBLs);
    }
    public void getAllDataNhaThuoc(ArrayList<nhaThuoc> nhaThuocs)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TB_NHATHUOCS, new String[]{
                COL_NHATHUOC_MANT,COL_NHATHUOC_TENNT,COL_NHATHUOC_DIACHI},null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                nhaThuoc nt = new nhaThuoc();
                nt.setMaNT(cursor.getString(cursor.getColumnIndex(COL_NHATHUOC_MANT)));
                nt.setTenNT(cursor.getString(cursor.getColumnIndex(COL_NHATHUOC_TENNT)));
                nt.setDiaChi(cursor.getString(cursor.getColumnIndex(COL_NHATHUOC_DIACHI)));
                nhaThuocs.add(nt);
            } while (cursor.moveToNext());

        }
    }
    public void searchNhaThuoc(ArrayList<nhaThuoc> nhaThuocs,String tenNT)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql ="SELECT * FROM "+TB_NHATHUOCS +" WHERE " + COL_NHATHUOC_TENNT +" LIKE "+ "'%'||"+"? || '%'";
        Cursor cursor = db.rawQuery(sql,new String[]{tenNT.trim()});
//        Log.i("sql",sql);
        if(cursor.moveToFirst()) {
            do {
                nhaThuoc nt = new nhaThuoc();
                nt.setMaNT(cursor.getString(cursor.getColumnIndex(COL_NHATHUOC_MANT)));
                nt.setTenNT(cursor.getString(cursor.getColumnIndex(COL_NHATHUOC_TENNT)));
                nt.setDiaChi(cursor.getString(cursor.getColumnIndex(COL_NHATHUOC_DIACHI)));
                nhaThuocs.add(nt);
            } while (cursor.moveToNext());
        }
    }

    public void saveNhaThuoc(nhaThuoc nt)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NHATHUOC_MANT,nt.getMaNT());
        values.put(COL_NHATHUOC_TENNT,nt.getTenNT());
        values.put(COL_NHATHUOC_DIACHI,nt.getDiaChi());
        db.insert(TB_NHATHUOCS,null,values);
        db.close();
    }
    public void deleteNhaThuoc(String maNT)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TB_NHATHUOCS + " WHERE " + COL_NHATHUOC_MANT + "= '" + maNT+ "'";
        db.execSQL(query);
    }
    public void updateNhaThuoc(nhaThuoc nt)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql ="UPDATE "+TB_NHATHUOCS +" set ";
        sql += COL_NHATHUOC_TENNT +"='"+nt.getTenNT()+"',";
        sql += COL_NHATHUOC_DIACHI +"='"+nt.getDiaChi()+"' ";
        sql += " WHERE " + COL_NHATHUOC_MANT +"='"+nt.getMaNT()+"' ";
        db.execSQL(sql);
    }
    public Cursor checkNhaThuoc(String maNT,String tenNT)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql ="SELECT * FROM "+TB_NHATHUOCS +" WHERE "+ COL_NHATHUOC_MANT + "=?" +" OR " + COL_NHATHUOC_TENNT+ "=?";
        Cursor cursor = db.rawQuery(sql,new String[]{maNT,tenNT});
        return cursor;
    }
    // Hóa đơn
    public void getAllMaNhaThuoc(ArrayList<String> maNTs)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TB_NHATHUOCS, new String[]{
                COL_NHATHUOC_MANT,COL_NHATHUOC_TENNT,COL_NHATHUOC_DIACHI},null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                maNTs.add(cursor.getString(cursor.getColumnIndex(COL_NHATHUOC_MANT)));
            } while (cursor.moveToNext());
        }
    }
    public void getAllDataHoaDon(ArrayList<hoaDon> hoaDons)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TB_HOADONS, new String[]{
                COL_HOADON_SOHD,COL_HOADON_NGAYHD,COL_HOADON_MANT},null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                hoaDon hd = new hoaDon();
                hd.setMaHD(cursor.getString(cursor.getColumnIndex(COL_HOADON_SOHD)));
                hd.setNgayHD(cursor.getString(cursor.getColumnIndex(COL_HOADON_NGAYHD)));
                hd.setMaNT(cursor.getString(cursor.getColumnIndex(COL_HOADON_MANT)));
                hoaDons.add(hd);
            } while (cursor.moveToNext());

        }
    }
    public void getAllDataHoaDonCuaNhaThuoc(ArrayList<hoaDon> hoaDons,String MaNT)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql ="SELECT * FROM "+TB_HOADONS +" WHERE "+ COL_HOADON_MANT +"=?";
        Cursor cursor = db.rawQuery(sql,new String[]{MaNT});
        if(cursor.moveToFirst()){
            do {
                hoaDon hd = new hoaDon();
                hd.setMaHD(cursor.getString(cursor.getColumnIndex(COL_HOADON_SOHD)));
                hd.setNgayHD(cursor.getString(cursor.getColumnIndex(COL_HOADON_NGAYHD)));
                hd.setMaNT(cursor.getString(cursor.getColumnIndex(COL_HOADON_MANT)));
                hoaDons.add(hd);
            } while (cursor.moveToNext());
        }
    }
    public void saveHoaDon(hoaDon hd)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_HOADON_SOHD,hd.getMaHD());
        values.put(COL_HOADON_NGAYHD,hd.getNgayHD());
        values.put(COL_HOADON_MANT,hd.getMaNT());
        db.insert(TB_HOADONS,null,values);
        db.close();
    }
    public void deleteHoaDon(String maHD)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TB_HOADONS + " WHERE " + COL_HOADON_SOHD + "= '" + maHD+ "'";
        db.execSQL(query);
    }
    public void updateHoaDon(hoaDon hd)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql ="UPDATE "+TB_HOADONS +" set ";
        sql += COL_HOADON_NGAYHD +"='"+hd.getNgayHD()+"',";
        sql += COL_HOADON_MANT +"='"+hd.getMaNT()+"' ";
        sql += " WHERE " + COL_HOADON_SOHD +"='"+hd.getMaHD()+"' ";
        db.execSQL(sql);
    }
    public Cursor checkHoaDon(String maHD)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql ="SELECT * FROM "+TB_HOADONS +" WHERE "+ COL_HOADON_SOHD + "=?";
        Cursor cursor = db.rawQuery(sql,new String[]{maHD});
        return cursor;
    }
    public void searchHoaDon(ArrayList<hoaDon> hoadons,String soHD)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql ="SELECT * FROM "+TB_HOADONS +" WHERE " + COL_HOADON_SOHD +" LIKE "+ "'%'||"+"? || '%'";
        Cursor cursor = db.rawQuery(sql,new String[]{soHD.trim()});
//        Log.i("sql",sql);
        if(cursor.moveToFirst()) {
            do {
                hoaDon hd = new hoaDon();
                hd.setMaHD(cursor.getString(cursor.getColumnIndex(COL_HOADON_SOHD)));
                hd.setNgayHD(cursor.getString(cursor.getColumnIndex(COL_HOADON_NGAYHD)));
                hd.setMaNT(cursor.getString(cursor.getColumnIndex(COL_HOADON_MANT)));
                hoadons.add(hd);
            } while (cursor.moveToNext());
        }
    }
    //Chi tiết bán lẻ
    public void getAllDataCTBL(ArrayList<chiTietBanLe> CTBL)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TB_CTBLS, new String[]{
                COL_CTBL_SOHD, COL_CTBL_MATHUOC, COL_CTBL_SOlUONG},null,null,null,null,null);
        if (cursor.moveToFirst())
        {
            do {
                chiTietBanLe c = new chiTietBanLe();
                c.setSoHD(cursor.getString(cursor.getColumnIndex(COL_CTBL_SOHD)));
                c.setMaThuoc(cursor.getString(cursor.getColumnIndex(COL_CTBL_MATHUOC)));
                c.setSoLuong(cursor.getInt(cursor.getColumnIndex(COL_CTBL_SOlUONG)));
                CTBL.add(c);
            }while (cursor.moveToNext());
        }
    }
    public void getAllDataCTBLCuaHoaDon(ArrayList<chiTietBanLe> CTBL,String soHD)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql ="SELECT * FROM "+TB_CTBLS +" WHERE "+ COL_CTBL_SOHD +"=?";
        Cursor cursor = db.rawQuery(sql,new String[]{soHD});
        if(cursor.moveToFirst()){
            do {
                chiTietBanLe c = new chiTietBanLe();
                c.setSoHD(cursor.getString(cursor.getColumnIndex(COL_CTBL_SOHD)));
                c.setMaThuoc(cursor.getString(cursor.getColumnIndex(COL_CTBL_MATHUOC)));
                c.setSoLuong(cursor.getInt(cursor.getColumnIndex(COL_CTBL_SOlUONG)));
                CTBL.add(c);
            } while (cursor.moveToNext());
        }
    }
    public void saveCTBL(chiTietBanLe c)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CTBL_SOHD, c.getSoHD());
        values.put(COL_CTBL_MATHUOC, c.getMaThuoc());
        values.put(COL_CTBL_SOlUONG, c.getSoLuong());
        db.insert(TB_CTBLS, null, values);
        db.close();
    }

    public void deleteCTBL(String soHD, String maThuoc)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TB_CTBLS + " WHERE " + COL_CTBL_SOHD + "= '" + soHD + "' AND " + COL_CTBL_MATHUOC + "='" + maThuoc  + "'";
        db.execSQL(query);
    }

    public void updateCTBL(chiTietBanLe c)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE " + TB_CTBLS + " SET ";
        sql += COL_CTBL_SOlUONG + " = '" + c.getSoLuong() +"' ";
        sql += "WHERE " + COL_CTBL_SOHD + "= '" + c.getSoHD() + "' AND " + COL_CTBL_MATHUOC + " = '" + c.getMaThuoc() + "'";
        db.execSQL(sql);
    }

    public Cursor checkCTBL(String soHD, String maThuoc)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + TB_CTBLS + " WHERE " + COL_CTBL_SOHD + "=? AND " + COL_CTBL_MATHUOC + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{soHD, maThuoc});
        return cursor;
    }
    public void getAllSoHD(ArrayList<String> soHDs)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TB_HOADONS, new String[]{
                COL_HOADON_SOHD},null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                soHDs.add(cursor.getString(cursor.getColumnIndex(COL_HOADON_SOHD)));
            } while (cursor.moveToNext());
        }
    }
    public void getAllMaThuoc(ArrayList<String> maThuocs)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TB_THUOCS, new String[]{
                COL_THUOC_MATHUOC},null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                maThuocs.add(cursor.getString(cursor.getColumnIndex(COL_THUOC_MATHUOC)));
            } while (cursor.moveToNext());
        }
    }
    public void searchCTBL(ArrayList<chiTietBanLe> chiTietBanLes,String maThuoc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql ="SELECT * FROM "+TB_CTBLS +" WHERE " + COL_CTBL_MATHUOC +" LIKE "+ "'%'||"+"? || '%'";
        Cursor cursor = db.rawQuery(sql,new String[]{maThuoc.trim()});
//        Log.i("sql",sql);
        if(cursor.moveToFirst()) {
            do {
                chiTietBanLe ctbl = new chiTietBanLe();
                ctbl.setSoHD(cursor.getString(cursor.getColumnIndex(COL_CTBL_SOHD)));
                ctbl.setMaThuoc(cursor.getString(cursor.getColumnIndex(COL_CTBL_MATHUOC)));
                ctbl.setSoLuong(cursor.getInt(cursor.getColumnIndex(COL_CTBL_SOlUONG)));
                chiTietBanLes.add(ctbl);
            } while (cursor.moveToNext());
        }
    }
    //Thuốc
    public void getAllDataThuoc(ArrayList<thuoc> thuocs)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TB_THUOCS, new String[]{
                COL_THUOC_MATHUOC,COL_THUOC_TENTHUOC,COL_THUOC_DVT,COL_THUOC_DONGIA, COL_THUOC_HINHANH},null,null,null,null,null);
        if (cursor.moveToFirst())
        {
            do {
                thuoc t = new thuoc();
                t.setMaThuoc(cursor.getString(cursor.getColumnIndex(COL_THUOC_MATHUOC)));
                t.setTenThuoc(cursor.getString(cursor.getColumnIndex(COL_THUOC_TENTHUOC)));
                t.setDVT(cursor.getString(cursor.getColumnIndex(COL_THUOC_DVT)));
                t.setDonGia(cursor.getFloat(cursor.getColumnIndex(COL_THUOC_DONGIA)));
                t.setImageMedical(cursor.getBlob(cursor.getColumnIndex(COL_THUOC_HINHANH)));
                thuocs.add(t);
            }while (cursor.moveToNext());
        }
    }

    public void saveThuoc(thuoc t)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_THUOC_MATHUOC, t.getMaThuoc());
        values.put(COL_THUOC_TENTHUOC, t.getTenThuoc());
        values.put(COL_THUOC_DVT, t.getDVT());
        values.put(COL_THUOC_DONGIA, t.getDonGia());
        values.put(COL_THUOC_HINHANH, t.getImageMedical());
        db.insert(TB_THUOCS, null, values);
        db.close();
    }

    public void deleteThuoc(String maThuoc)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TB_THUOCS + " WHERE " + COL_THUOC_MATHUOC + "='" + maThuoc  + "'";
        db.execSQL(query);
    }

    public void updateThuoc(thuoc t)
    {
        SQLiteDatabase db = getWritableDatabase();
        /*String sql = "UPDATE " + TB_THUOCS + " SET ";
        sql += COL_THUOC_TENTHUOC + " = '" + t.getTenThuoc() +"', ";
        sql += COL_THUOC_DVT + " = '" + t.getDVT() +"', ";
        sql += COL_THUOC_DONGIA + " = '" + t.getDonGia() +"', ";
        sql += COL_THUOC_HINHANH + " = '" + t.getImageMedical() + "' ";
        sql += "WHERE " + COL_THUOC_MATHUOC + " = '" + t.getMaThuoc() + "'";
        db.execSQL(sql);*/

        ContentValues values = new ContentValues();
        values.put(COL_THUOC_MATHUOC, t.getMaThuoc());
        values.put(COL_THUOC_TENTHUOC, t.getTenThuoc());
        values.put(COL_THUOC_DVT, t.getDVT());
        values.put(COL_THUOC_DONGIA, t.getDonGia());
        values.put(COL_THUOC_HINHANH, t.getImageMedical());
        db.update(TB_THUOCS, values, COL_THUOC_MATHUOC + " = '" + t.getMaThuoc() + "'", null);
        db.close();
    }

    public Cursor checkThuoc(String maThuoc)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + TB_THUOCS + " WHERE " + COL_THUOC_MATHUOC + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{maThuoc});
        return cursor;
    }
    public void searchThuoc(ArrayList<thuoc> thuocs,String maThuoc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql ="SELECT * FROM "+TB_THUOCS +" WHERE " + COL_THUOC_MATHUOC +" LIKE "+ "'%'||"+"? || '%'";
        Cursor cursor = db.rawQuery(sql,new String[]{maThuoc.trim()});
//        Log.i("sql",sql);
        if(cursor.moveToFirst()) {
            do {
                thuoc t = new thuoc();
                t.setMaThuoc(cursor.getString(cursor.getColumnIndex(COL_THUOC_MATHUOC)));
                t.setTenThuoc(cursor.getString(cursor.getColumnIndex(COL_THUOC_TENTHUOC)));
                t.setDVT(cursor.getString(cursor.getColumnIndex(COL_THUOC_DVT)));
                t.setDonGia(cursor.getFloat(cursor.getColumnIndex(COL_THUOC_DONGIA)));
                t.setImageMedical(cursor.getBlob(cursor.getColumnIndex(COL_THUOC_HINHANH)));
                thuocs.add(t);
            } while (cursor.moveToNext());
        }
    }

    public void thongKe(ArrayList<thuoc> thuocs) {
        int tk = 0;
        SQLiteDatabase db = getWritableDatabase();
        //String query = "SELECT * FROM " + TB_THUOCS + " JOIN (SELECT " + COL_CTBL_MATHUOC + ", SUM (" + COL_CTBL_SOlUONG + ") AS SL FROM " + TB_CTBLS + " ORDER BY (SL) DESC) ON " + COL_THUOC_MATHUOC + " = " + COL_CTBL_MATHUOC + " GROUP BY " + COL_THUOC_MATHUOC;
        String query = "SELECT * FROM " + TB_THUOCS + " JOIN (SELECT " + COL_CTBL_MATHUOC + ", SUM ("
                        + COL_CTBL_SOlUONG + ") AS SL FROM " + TB_CTBLS + ", " + TB_THUOCS + " WHERE "
                        + COL_THUOC_MATHUOC + " = " + COL_CTBL_MATHUOC + " ORDER BY (SL) DESC) ON "
                        + COL_THUOC_MATHUOC + " = " + COL_CTBL_MATHUOC + " GROUP BY " + COL_THUOC_MATHUOC;
        /*String query = "SELECT " + COL_CTBL_MATHUOC + ", " + COL_THUOC_TENTHUOC + ", " + COL_THUOC_DVT + ", "
                + COL_THUOC_HINHANH + ", " + COL_THUOC_DONGIA + " = " + " SUM (" + COL_CTBL_SOlUONG + ") AS SL " +
                "FROM " + TB_CTBLS + ", " + TB_THUOCS + " WHERE " + COL_THUOC_MATHUOC + " = " + COL_CTBL_MATHUOC
                + " GROUP BY " + COL_THUOC_MATHUOC
                + " ORDER BY (SL) DESC ";*/
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                thuoc t = new thuoc();
                t.setMaThuoc(cursor.getString(cursor.getColumnIndex(COL_THUOC_MATHUOC)));
                t.setTenThuoc(cursor.getString(cursor.getColumnIndex(COL_THUOC_TENTHUOC)));
                t.setDVT(cursor.getString(cursor.getColumnIndex(COL_THUOC_DVT)));
                t.setDonGia(cursor.getFloat(cursor.getColumnIndex(COL_THUOC_DONGIA)));
                t.setImageMedical(cursor.getBlob(cursor.getColumnIndex(COL_THUOC_HINHANH)));
                thuocs.add(t);
            } while (cursor.moveToNext());
        }
    }
}
