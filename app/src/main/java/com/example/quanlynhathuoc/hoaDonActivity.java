package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class hoaDonActivity extends AppCompatActivity {
    private ListView lvHoaDon;
    ArrayList<hoaDon> data = new ArrayList<>();
    hoaDonAdapter adapter = null;
    Button NT_btnThem, NT_btnSua,NT_btnXoa,NT_btnXemDS,NT_btnTroVe,NT_btnSearch;
    hoaDon temp =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmhoadon);
        setControl();
        setEvent();
    }

    private void setControl() {
        NT_btnThem = findViewById(R.id.NT_btnThem);
        NT_btnXoa = findViewById(R.id.NT_btnXoa);
        NT_btnSua = findViewById(R.id.NT_btnSua);
        lvHoaDon = findViewById(R.id.lvHoaDon);
        NT_btnTroVe = findViewById(R.id.NT_btnTroVe);
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

        loadData();
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
                Intent intent = new Intent(hoaDonActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        NT_btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongbaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa nhà thuốc?");
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
    }
}