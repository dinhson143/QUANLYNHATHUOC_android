package com.example.quanlynhathuoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


//LỚP XỬ LÍ DỮ LIỆU
public class database extends SQLiteOpenHelper {
    private static String DB_NAME = "dbNhaThuoc.db";
    private static int DB_VERSION =2;
    private static final String TB_NHATHUOCS = "tbNhaThuoc";
    private static final String COL_NHATHUOC_MANT = "NhaThuoc_MaNT";
    private static final String COL_NHATHUOC_TENNT = "NhaThuoc_TenNT";
    private static final String COL_NHATHUOC_DIACHI = "NhaThuoc_DiaChi";

    private static final String TB_HOADONS = "tbHoaDon";
    private static final String COL_HOADON_SOHD = "HoaDon_SoHD";
    private static final String COL_HOADON_NGAYHD = "HoaDon_NgayHD";
    private static final String COL_HOADON_MANT = "HoaDon_MaNT";

    public database(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ TB_HOADONS);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_NHATHUOCS);
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
        db.execSQL(scriptTB_NhaThuocs);
        db.execSQL(scriptTB_HoaDons);
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
        sql += " WHERE " + COL_NHATHUOC_MANT +"='"+hd.getMaHD()+"' ";
        db.execSQL(sql);
    }
    public Cursor checkHoaDon(String maHD)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql ="SELECT * FROM "+TB_HOADONS +" WHERE "+ COL_HOADON_SOHD + "=?";
        Cursor cursor = db.rawQuery(sql,new String[]{maHD});
        return cursor;
    }
}
