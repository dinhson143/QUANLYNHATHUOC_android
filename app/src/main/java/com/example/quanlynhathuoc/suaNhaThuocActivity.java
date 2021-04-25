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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class suaNhaThuocActivity  extends AppCompatActivity {
    EditText edtmaNT, edtTenNT,edtDiaChi;
    Button btnSuaNT,btnReload,btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmsuanhathuoc);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        edtmaNT.setEnabled(false);
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
                NTsua();
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(suaNhaThuocActivity.this, nhaThuocActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setEvent() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(suaNhaThuocActivity.this, nhaThuocActivity.class);
                startActivity(intent);
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTenNT.setText("");
                edtDiaChi.setText("");
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
        nhaThuoc nt = getNhaThuoc();
            db.updateNhaThuoc(nt);
            thongbao("SỬA THÀNH CÔNG","Bạn có đồng ý thoát chương trình không?");
    }

    private void setControl() {
        btnSuaNT = findViewById(R.id.btnSuaNT);
        edtmaNT = findViewById(R.id.edtMaNhaThuoc);
        edtTenNT = findViewById(R.id.edtTenNhaThuoc);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        btnReload = findViewById(R.id.btnReloadformSua);
        btnExit = findViewById(R.id.btnExit);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        edtmaNT.setText(bundle.getString("maNT"));
        edtTenNT.setText(bundle.getString("tenNT"));
        edtDiaChi.setText(bundle.getString("diachiNT"));
    }
    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(suaNhaThuocActivity.this, nhaThuocActivity.class);
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
