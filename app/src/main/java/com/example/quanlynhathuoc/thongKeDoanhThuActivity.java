package com.example.quanlynhathuoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class thongKeDoanhThuActivity extends AppCompatActivity {
    private ListView lvTKDT;
    ArrayList<doanhThu> data = new ArrayList<>();
    doanhThuAdapter adapter = null;
    Button btnTroVe,btnBieuDo;
    String thoiGian="",thang="",nam="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_thong_ke_doanh_thu);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
    }
    private void setControl() {
        btnTroVe = findViewById(R.id.NT_btnTroVe);
        btnBieuDo = findViewById(R.id.NT_btnBieuDo);
        lvTKDT = findViewById(R.id.lvDoanhThu);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            thang = bundle.getString("thang");
            nam = bundle.getString("nam");
            thoiGian += thang;
            thoiGian += "/";
            thoiGian += nam;
        }
    }
    private void setEvent() {
        adapter = new doanhThuAdapter(this,R.layout.listview_doanh_thu,data);
        lvTKDT.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadData();

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thongKeDoanhThuActivity.this, doanhThuActivity.class);
                startActivity(intent);
            }
        });
        btnBieuDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thongKeDoanhThuActivity.this, bieuDoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("thang",thang);
                bundle.putString("nam",nam);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    private void loadData() {
        database db = new database(this);
        data.clear();
        db.thongKeDoanhThu(data,thoiGian);
        adapter.notifyDataSetChanged();
    }

}
