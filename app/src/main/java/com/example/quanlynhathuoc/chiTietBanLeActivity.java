package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class chiTietBanLeActivity  extends AppCompatActivity {
    private ListView lvCTBL;
    Button NT_btnThem, NT_btnSua,NT_btnXoa,NT_btnXemDS,NT_btnTroVe,NT_btnSearch;
    ArrayList<chiTietBanLe> data = new ArrayList<>();
    chitietbanleAdapter adapter = null;
    EditText search;
    TextView txtTieuDeCTBL;
    chiTietBanLe temp =null;
    String soHD="",maThuoc = "",tempSoHD="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmchitietbanle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.formactionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.AB_xemDS:
            {
                return true;
            }
            case R.id.Ab_them:
            {
                Intent intent = new Intent(chiTietBanLeActivity.this, themChiTietBanLeActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.AB_xoa:
            {
                thongbaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa chi tiết bán lẻ "+ temp.getSoHD()+" "+ temp.getSoLuong()+"?");
                return true;
            }
            case R.id.AB_sua:
            {
                Intent intent = new Intent(chiTietBanLeActivity.this, suaChiTietBanLeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("soHD",temp.getSoHD());
                bundle.putString("maThuoc",temp.getMaThuoc());
                bundle.putInt("soLuong",temp.getSoLuong());
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
            case R.id.AB_troVe:
            {
                if(tempSoHD=="")
                {
                    Intent intent = new Intent(chiTietBanLeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(chiTietBanLeActivity.this, hoaDonActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setControl() {
        NT_btnThem = findViewById(R.id.NT_btnThem);
        NT_btnXoa = findViewById(R.id.NT_btnXoa);
        NT_btnSua = findViewById(R.id.NT_btnSua);
        lvCTBL = findViewById(R.id.lvChiTietBanLe);
        NT_btnTroVe = findViewById(R.id.NT_btnTroVe);
        txtTieuDeCTBL= findViewById(R.id.tieuDeCTBL);
        search = findViewById(R.id.editTextTextPersonName);
        NT_btnSearch = findViewById(R.id.btnSearch);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            tempSoHD = bundle.getString("soHD");
        }
    }

    private void setEvent() {
        adapter = new chitietbanleAdapter(this,R.layout.listviewchitietbanle,data);
        lvCTBL.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(tempSoHD=="")
        {
            loadData();
        }
        else
        {
            loadDataSoHD();
            txtTieuDeCTBL.setText(txtTieuDeCTBL.getText()+" "+tempSoHD);
        }

        lvCTBL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                temp = data.get(position);
            }
        });
        NT_btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chiTietBanLeActivity.this, themChiTietBanLeActivity.class);
                startActivity(intent);
            }
        });
        NT_btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongbaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa chi tiết bán lẻ "+ temp.getSoHD()+" "+ temp.getSoLuong()+"?");
            }
        });
        NT_btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chiTietBanLeActivity.this, suaChiTietBanLeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("soHD",temp.getSoHD());
                bundle.putString("maThuoc",temp.getMaThuoc());
                bundle.putInt("soLuong",temp.getSoLuong());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        NT_btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tempSoHD=="")
                {
                    Intent intent = new Intent(chiTietBanLeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(chiTietBanLeActivity.this, hoaDonActivity.class);
                    startActivity(intent);
                }
            }
        });
        NT_btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = search.getText().toString().trim();
                Log.i("aaa",s);
                if(s.length()>0)
                {
                    lvCTBL.setAdapter(null);
                    lvCTBL.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    getSearch(s);
                }
                else
                {
                    lvCTBL.setAdapter(null);
                    lvCTBL.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    loadData();
                }
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s,int start, int count, int after){
            }

            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count){
                //                nhaThuocActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged (Editable s){
                if (s.length() > 0) {
                    lvCTBL.setAdapter(null);
                    lvCTBL.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    getSearch(s.toString());
                } else {
                    lvCTBL.setAdapter(null);
                    lvCTBL.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    loadData();
                }
            }
        });
    }
    private void loadData() {
        database db = new database(this);
        data.clear();
        db.getAllDataCTBL(data);
        adapter.notifyDataSetChanged();
        if(data.size()>0)
        {
            temp=data.get(0);
        }
    }
    private void loadDataSoHD() {
        database db = new database(this);
        data.clear();
        db.getAllDataCTBLCuaHoaDon(data,tempSoHD);
        adapter.notifyDataSetChanged();
        if(data.size()>0)
        {
            temp=data.get(0);
        }

    }
    public void thongbaoXoa(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                database db = new database(chiTietBanLeActivity.this);
                db.deleteCTBL(temp.getSoHD(),temp.getMaThuoc());
                loadData();
            }
        });
        //Nút Cancel
        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //Tạo dialog
        AlertDialog al = b.create();
        //Hiển thị
        al.show();
    }
    private void getSearch(String maThuoc)
    {
        database db = new database(this);
        data.clear();
        db.searchCTBL(data,maThuoc);
        adapter.notifyDataSetChanged();
        if(data.size()>0)
            temp=data.get(0);
    }
}
