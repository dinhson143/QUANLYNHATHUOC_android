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

public class hoaDonActivity extends AppCompatActivity {
    private ListView lvHoaDon;
    ArrayList<hoaDon> data = new ArrayList<>();
    hoaDonAdapter adapter = null;
    Button NT_btnThem, NT_btnSua,NT_btnXoa,NT_btnXemDS,NT_btnTroVe,NT_btnSearch;
    TextView txtTieuDeHoaDon;
    EditText search;
    hoaDon temp =null;
    String maNT="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmhoadon);
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
                Intent intent = new Intent(hoaDonActivity.this, themHoaDonActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.AB_xoa:
            {
                thongbaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa hóa đơn"+ temp.getMaHD()+"?");
                return true;
            }
            case R.id.AB_sua:
            {
                Intent intent = new Intent(hoaDonActivity.this, suaHoaDonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("soHD",temp.getMaHD());
                bundle.putString("ngayHD",temp.getNgayHD());
                bundle.putString("maNT",temp.getMaNT());
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
            case R.id.AB_troVe:
            {
                if(maNT=="")
                {
                    Intent intent = new Intent(hoaDonActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(hoaDonActivity.this, nhaThuocActivity.class);
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
        lvHoaDon = findViewById(R.id.lvHoaDon);
        NT_btnTroVe = findViewById(R.id.NT_btnTroVe);
        txtTieuDeHoaDon = findViewById(R.id.tieuDeHoaDon);
        search = findViewById(R.id.editTextTextPersonName);
        NT_btnSearch = findViewById(R.id.btnSearch);
        NT_btnXemDS = findViewById(R.id.NT_btnXemDS);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            maNT = bundle.getString("maNT");
        }
    }

    private void getSearch(String soHD)
    {
        database db = new database(this);
        data.clear();
        db.searchHoaDon(data,soHD);
        adapter.notifyDataSetChanged();
        if(data.size()>0)
            temp=data.get(0);
    }
    private void loadData() {
        database db = new database(this);
        data.clear();
        db.getAllDataHoaDon(data);
        adapter.notifyDataSetChanged();
        if(data.size()>0)
        {
            temp=data.get(0);
        }
    }
    private void loadDataMaNT() {
        database db = new database(this);
        data.clear();
        db.getAllDataHoaDonCuaNhaThuoc(data,maNT);
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
                database db = new database(hoaDonActivity.this);
                db.deleteHoaDon(temp.getMaHD());
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
    private void setEvent() {
        adapter = new hoaDonAdapter(this,R.layout.listviewhoadon,data);
        lvHoaDon.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(maNT=="") // load tat ca
        {
            loadData();
        }
        else // load hoa don cua nha thuoc
        {
            loadDataMaNT();
            txtTieuDeHoaDon.setText(txtTieuDeHoaDon.getText()+ " "+ maNT);
        }
        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                temp = data.get(position);
            }
        });
        NT_btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hoaDonActivity.this, themHoaDonActivity.class);
                startActivity(intent);
            }
        });
        NT_btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(maNT=="")
                {
                    Intent intent = new Intent(hoaDonActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(hoaDonActivity.this, nhaThuocActivity.class);
                    startActivity(intent);
                }
            }
        });
        NT_btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongbaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa hóa đơn"+ temp.getMaHD()+"?");
            }
        });
        NT_btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hoaDonActivity.this, suaHoaDonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("soHD",temp.getMaHD());
                bundle.putString("ngayHD",temp.getNgayHD());
                bundle.putString("maNT",temp.getMaNT());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        NT_btnXemDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hoaDonActivity.this, chiTietBanLeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("soHD", temp.getMaHD());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        NT_btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = search.getText().toString().trim();
                Log.i("aaa",s);
                if(s.length()>0)
                {
                    lvHoaDon.setAdapter(null);
                    lvHoaDon.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    getSearch(s);
                }
                else
                {
                    lvHoaDon.setAdapter(null);
                    lvHoaDon.setAdapter(adapter);
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
                    lvHoaDon.setAdapter(null);
                    lvHoaDon.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    getSearch(s.toString());
                } else {
                    lvHoaDon.setAdapter(null);
                    lvHoaDon.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    loadData();
                }
            }
        });
    }
}
