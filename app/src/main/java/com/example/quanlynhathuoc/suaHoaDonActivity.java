package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class suaHoaDonActivity extends AppCompatActivity {
    EditText edtSoHD, edtNgayHD;
    Spinner spnMaNT;
    Button btnSuaNT,btnReload,btnExit;
    String tempMaNT;
    ArrayList<String> allMaNT = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmsuahoadon);
        setControl();
        setEvent();
        edtSoHD.setEnabled(false);
    }

    private void setControl() {
        edtSoHD = findViewById(R.id.edtSoHoaDon);
        edtNgayHD = findViewById(R.id.edtNgayHoaDon);
        spnMaNT = findViewById(R.id.spnMaNT);
        btnSuaNT = findViewById(R.id.btnSuaHD);
        btnReload = findViewById(R.id.btnReloadformSua);
        btnExit = findViewById(R.id.btnExit);
        getAllMaNT();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        edtSoHD.setText(bundle.getString("soHD"));
        edtNgayHD.setText(bundle.getString("ngayHD"));
        String maNT = bundle.getString("maNT");
        for(int i=0;i<allMaNT.size();i++)
        {
            if(allMaNT.get(i).equals(maNT))
            {
                spnMaNT.setSelection(i);
                break;
            }
        }
        spnMaNT.setSelection(1);
    }

    private void setEvent() {

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,allMaNT);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnMaNT.setAdapter(adapter);
        spnMaNT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempMaNT = allMaNT.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(suaHoaDonActivity.this, hoaDonActivity.class);
                startActivity(intent);
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNgayHD.setText("");
            }
        });
        btnSuaNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NTsua();
            }
        });
    }
    private void NTsua() {
        database db = new database(this);
        hoaDon hd = getHoaDon();
        db.updateHoaDon(hd);
        thongbao("SỬA THÀNH CÔNG","Bạn có đồng ý thoát chương trình không?");
    }
    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(suaHoaDonActivity.this, hoaDonActivity.class);
                startActivity(intent);
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
    private hoaDon getHoaDon() {
        hoaDon hd = new hoaDon();
        hd.setMaHD(edtSoHD.getText().toString());
        hd.setNgayHD(edtNgayHD.getText().toString());
        hd.setMaNT(tempMaNT);
        return hd;
    }
    private void getAllMaNT() {
        database db = new database(this);
        allMaNT.clear();
        db.getAllMaNhaThuoc(allMaNT);
        if(allMaNT.size()>0)
            tempMaNT=allMaNT.get(0);
    }

}
