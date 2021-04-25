package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

public class themHoaDonActivity extends AppCompatActivity {
    EditText edtSoHD, edtNgayHD;
    Button btnXacNhanThemNT,btnTroVeNT;
    Spinner spnMaNT;
    String tempMaNT;
    ArrayList<String> allMaNT = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmthemhoadon);
        setControl();
        setEvent();
    }
    private void setControl() {
        btnXacNhanThemNT = findViewById(R.id.btnXacNhanThemNT);
        spnMaNT = findViewById(R.id.spnMaNT);
        edtSoHD = findViewById(R.id.edtSoHoaDon);
        edtNgayHD = findViewById(R.id.edtNgayHoaDon);
        btnTroVeNT = findViewById(R.id.btnTroVeNT);
        getAllMaNT();
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
        btnXacNhanThemNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSoHD.getText().length() == 0)
                {
                    Toast.makeText(themHoaDonActivity.this, "Vui lòng nhập Số Hóa Đơn!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtNgayHD.getText().length() == 0)
                {
                    Toast.makeText(themHoaDonActivity.this, "Vui lòng nhập Ngày Hóa Đơn!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tempMaNT == null)
                {
                    Toast.makeText(themHoaDonActivity.this, "Vui lòng chọn Thuốc!", Toast.LENGTH_SHORT).show();
                    return;
                }
                HDNhap();
            }
        });
        btnTroVeNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(themHoaDonActivity.this, hoaDonActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getAllMaNT() {
        database db = new database(this);
        allMaNT.clear();
        db.getAllMaNhaThuoc(allMaNT);
        if(allMaNT.size()>0)
            tempMaNT=allMaNT.get(0);
    }

    private void HDNhap() {
        database db = new database(this);
        hoaDon nt = getHoaDon();
        Cursor cursor = db.checkHoaDon(nt.getMaHD());
        int result=-1;
        while (cursor.moveToNext())
        {
            result = cursor.getInt(0);
            break;
        }
        if(result<0) {
            db.saveHoaDon(nt);
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
                Intent intent = new Intent(themHoaDonActivity.this, hoaDonActivity.class);
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

}
