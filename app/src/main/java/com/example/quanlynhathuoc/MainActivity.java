package com.example.quanlynhathuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnMoNhaThuoc,btnMoHoaDon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmmain);
        setControl();
        setEvent();
    }

    private void setControl() {
        btnMoNhaThuoc = findViewById(R.id.btnMoNhaThuoc);
        btnMoHoaDon = findViewById(R.id.btnMoHoaDon);
    }
    private void  setEvent()
    {
        btnMoNhaThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, nhaThuocActivity.class);
                startActivity(intent);
            }
        });
        btnMoHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, hoaDonActivity.class);
                startActivity(intent);
            }
        });
    }
}