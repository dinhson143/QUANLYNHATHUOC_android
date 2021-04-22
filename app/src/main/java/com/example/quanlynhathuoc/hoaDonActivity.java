package com.example.quanlynhathuoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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
            temp=data.get(0);
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
    }
}
