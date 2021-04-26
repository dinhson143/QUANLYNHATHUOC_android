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

public class suaChiTietBanLeActivity extends AppCompatActivity {
    Spinner spnSoHD,spnMaThuoc;
    EditText edtSoLuong;
    Button btnXacNhanThemNT,btnTroVeNT;
    String tempSoHD,tempMaThuoc;
    ArrayList<String> allSoHD = new ArrayList<>();
    /*ArrayList<String> allMaThuoc = new ArrayList<>();*/
    ArrayList<thuoc> allMaThuoc = new ArrayList<>();
    thuoc tempThuoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmsuachitietbanle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        spnSoHD.setEnabled(false);
        spnMaThuoc.setEnabled(false);
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
                if (edtSoLuong.getText().toString().length() == 0 )
                {
                    Toast.makeText(suaChiTietBanLeActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (Integer.parseInt(edtSoLuong.getText().toString()) <= 0 )
                {
                    Toast.makeText(suaChiTietBanLeActivity.this, "Vui lòng nhập số lượng lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return true;
                }
                CTBLSua();
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(suaChiTietBanLeActivity.this, chiTietBanLeActivity.class);
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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        edtSoLuong.setText(String.valueOf(bundle.getInt("soLuong")));
        tempSoHD = bundle.getString("soHD");
        tempMaThuoc = bundle.getString("maThuoc");
    }
    private void setEvent() {
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,allSoHD);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnSoHD.setAdapter(adapter);
        for(int i=0;i<allSoHD.size();i++)
        {
            if(allSoHD.get(i).equals(tempSoHD))
            {
                spnSoHD.setSelection(i);
                break;
            }
        }
        //ArrayAdapter adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,allMaThuoc);
        /*ArrayAdapter adapter1 = new ArrayAdapter<thuoc>(this, android.R.layout.simple_spinner_item, allMaThuoc);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);*/
        CustomSpinnerAdapter adapter1 = new CustomSpinnerAdapter(getApplicationContext(), allMaThuoc);
        spnMaThuoc.setAdapter(adapter1);
        for(int i=0;i<allMaThuoc.size();i++)
        {
            if(allMaThuoc.get(i).getMaThuoc().equals(tempMaThuoc))
            {
                spnMaThuoc.setSelection(i);
                break;
            }
        }

        btnXacNhanThemNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSoLuong.getText().toString().length() == 0 )
                {
                    Toast.makeText(suaChiTietBanLeActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(edtSoLuong.getText().toString()) <= 0 )
                {
                    Toast.makeText(suaChiTietBanLeActivity.this, "Vui lòng nhập số lượng lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                CTBLSua();
            }
        });
        btnTroVeNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(suaChiTietBanLeActivity.this, chiTietBanLeActivity.class);
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
        {
            //tempMaThuoc=allMaThuoc.get(0);
            tempThuoc = allMaThuoc.get(0);
        }
    }
    private void CTBLSua() {
        database db = new database(this);
        chiTietBanLe ctbl = getCTBL();
        db.updateCTBL(ctbl);
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
                Intent intent = new Intent(suaChiTietBanLeActivity.this, chiTietBanLeActivity.class);
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
        //ctbl.setMaThuoc(tempThuoc.getMaThuoc());
        ctbl.setSoLuong(Integer.valueOf(edtSoLuong.getText().toString()));
        return ctbl;
    }
}
