package com.example.quanlynhathuoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class doanhThuActivity extends AppCompatActivity {
    EditText edtNam,edtThang;
    Button btnXacNhan,btnTroVe;
    String nam,thang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_doanh_thu);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doanhThuActivity.this, thongKeDoanhThuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("thang",edtThang.getText().toString());
                bundle.putString("nam",edtNam.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doanhThuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        edtNam = findViewById(R.id.edtNam);
        edtThang = findViewById(R.id.edtThang);
        btnXacNhan = findViewById(R.id.NT_btnDoanhThu);
        btnTroVe = findViewById(R.id.NT_btnTroVe);
    }

}
