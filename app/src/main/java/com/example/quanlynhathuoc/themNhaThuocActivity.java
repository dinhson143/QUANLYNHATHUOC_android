package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class themNhaThuocActivity extends AppCompatActivity {

    EditText edtmaNT, edtTenNT,edtDiaChi;
    Button btnXacNhanThemNT,btnTroVeNT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmthemnhathuoc);
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
                NTNhap();
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(themNhaThuocActivity.this, nhaThuocActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setEvent() {
        btnXacNhanThemNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (edtmaNT.getText().length() == 0)
                {
                    Toast.makeText(themNhaThuocActivity.this, "Vui lòng nhập Mã Nhà Thuốc!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtTenNT.getText().length() == 0)
                {
                    Toast.makeText(themNhaThuocActivity.this, "Vui lòng nhập Tên Nhà Thuốc!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtDiaChi.getText().length() == 0)
                {
                    Toast.makeText(themNhaThuocActivity.this, "Vui lòng nhập Địa Chỉ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                NTNhap();
            }
        });
        btnTroVeNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(themNhaThuocActivity.this, nhaThuocActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnXacNhanThemNT = findViewById(R.id.btnXacNhanThemNT);
        edtmaNT = findViewById(R.id.edtMaNhaThuoc);
        edtTenNT = findViewById(R.id.edtTenNhaThuoc);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        btnTroVeNT = findViewById(R.id.btnTroVeNT);
    }
    private void NTNhap() {
        database db = new database(this);
        nhaThuoc nt = getNhaThuoc();
        Cursor cursor = db.checkNhaThuoc(nt.getMaNT(),nt.getTenNT());
        int result=-1;
        while (cursor.moveToNext())
        {
            result = cursor.getInt(0);
            break;
        }
        if(result<0) {
            db.saveNhaThuoc(nt);
            thongbao("THÊM THÀNH CÔNG","Bạn có đồng ý thoát chương trình không?");
        }
        else if(result>=0)
        {
            thongbao("ĐÃ TỒN TẠI NHÀ THUỐC TUONG TỰ","Bạn có đồng ý thoát chương trình không?");
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
                Intent intent = new Intent(themNhaThuocActivity.this, nhaThuocActivity.class);
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
    private nhaThuoc getNhaThuoc() {
        nhaThuoc nt = new nhaThuoc();
        nt.setMaNT(edtmaNT.getText().toString());
        nt.setTenNT(edtTenNT.getText().toString());
        nt.setDiaChi(edtDiaChi.getText().toString());
        return nt;
    }
}
