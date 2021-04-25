package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class themChiTietBanLeActivity extends AppCompatActivity {
    Spinner spnSoHD,spnMaThuoc;
    EditText edtSoLuong;
    Button btnXacNhanThemNT,btnTroVeNT;
    String tempSoHD,tempMaThuoc;
    ArrayList<String> allSoHD = new ArrayList<>();
    ArrayList<String> allMaThuoc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmthemchitietbanle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.themactionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.AB_xacNhan:
            {
                if (tempSoHD.length() == 0)
                {
                    Toast.makeText(themChiTietBanLeActivity.this, "Vui lòng chọn số HD!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (tempMaThuoc.length() == 0)
                {
                    Toast.makeText(themChiTietBanLeActivity.this, "Vui lòng chọn Mã Thuốc!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (edtSoLuong.getText().toString().length() == 0 )
                {
                    Toast.makeText(themChiTietBanLeActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (Integer.parseInt(edtSoLuong.getText().toString()) <= 0 )
                {
                    Toast.makeText(themChiTietBanLeActivity.this, "Vui lòng nhập số lượng lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return true;
                }
                CTBLNhap();
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(themChiTietBanLeActivity.this, chiTietBanLe.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setControl() {
        btnXacNhanThemNT = findViewById(R.id.btnXacNhanThemNT);
        spnSoHD = findViewById(R.id.spnSoHD);
        spnMaThuoc = findViewById(R.id.spnMaThuoc);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        btnTroVeNT = findViewById(R.id.btnTroVeNT);
        getAllSoHD();
        getallMaThuoc();
    }


    private void setEvent() {
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,allSoHD);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnSoHD.setAdapter(adapter);
        spnSoHD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempSoHD = allSoHD.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,allMaThuoc);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnMaThuoc.setAdapter(adapter1);
        spnMaThuoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempMaThuoc = allMaThuoc.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnXacNhanThemNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempSoHD.length() == 0)
                {
                    Toast.makeText(themChiTietBanLeActivity.this, "Vui lòng chọn số HD!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tempMaThuoc.length() == 0)
                {
                    Toast.makeText(themChiTietBanLeActivity.this, "Vui lòng chọn Mã Thuốc!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtSoLuong.getText().toString().length() == 0 )
                {
                    Toast.makeText(themChiTietBanLeActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(edtSoLuong.getText().toString()) <= 0 )
                {
                    Toast.makeText(themChiTietBanLeActivity.this, "Vui lòng nhập số lượng lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                CTBLNhap();
            }
        });
        btnTroVeNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(themChiTietBanLeActivity.this, chiTietBanLeActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getAllSoHD() {
        database db = new database(this);
        allSoHD.clear();
        db.getAllSoHD(allSoHD);
        if(allSoHD.size()>0)
            tempSoHD=allSoHD.get(0);
    }
    private void getallMaThuoc() {
        database db = new database(this);
        allMaThuoc.clear();
        db.getAllMaThuoc(allMaThuoc);
        if(allMaThuoc.size()>0)
            tempMaThuoc=allMaThuoc.get(0);
    }
    private void CTBLNhap() {
        database db = new database(this);
        chiTietBanLe ctbl = getCTBL();
        Cursor cursor = db.checkCTBL(ctbl.getSoHD(),ctbl.getMaThuoc());
        int result=-1;
        while (cursor.moveToNext())
        {
            result = cursor.getInt(0);
            break;
        }
        if(result<0) {
            db.saveCTBL(ctbl);
            thongbao("THÊM THÀNH CÔNG","Bạn có đồng ý thoát chương trình không?");
        }
        else if(result>=0)
        {
            thongbao("ĐÃ TỒN TẠI CHI TIẾT BÁN LẺ TUONG TỰ","Bạn có đồng ý thoát chương trình không?");
        }
    }
    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(themChiTietBanLeActivity.this, chiTietBanLeActivity.class);
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
    private chiTietBanLe getCTBL() {
        chiTietBanLe ctbl = new chiTietBanLe();
       ctbl.setSoHD(tempSoHD);
       ctbl.setMaThuoc(tempMaThuoc);
       ctbl.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString()));
        return ctbl;
    }

}
