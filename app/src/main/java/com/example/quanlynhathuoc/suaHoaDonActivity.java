package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
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

public class suaHoaDonActivity extends AppCompatActivity {
    EditText edtSoHD, edtNgayHD;
    Spinner spnMaNT;
    Button btnSuaNT,btnReload,btnExit;
    String tempMaNT;
    ArrayList<String> allMaNT = new ArrayList<>();
    String maNT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmsuahoadon);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        edtSoHD.setEnabled(false);
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
                HDsua();
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(suaHoaDonActivity.this, hoaDonActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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
       maNT = bundle.getString("maNT");
    }

    private void setEvent() {

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,allMaNT);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnMaNT.setAdapter(adapter);
        for(int i=0;i<allMaNT.size();i++)
        {
            if(allMaNT.get(i).equals(maNT))
            {
                spnMaNT.setSelection(i);
                break;
            }
        }
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

                if (edtSoHD.getText().length() == 0)
                {
                    Toast.makeText(suaHoaDonActivity.this, "Vui l??ng nh???p S??? H??a ????n!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtNgayHD.getText().length() == 0)
                {
                    Toast.makeText(suaHoaDonActivity.this, "Vui l??ng nh???p Ng??y H??a ????n!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tempMaNT == null)
                {
                    Toast.makeText(suaHoaDonActivity.this, "Vui l??ng ch???n Thu???c!", Toast.LENGTH_SHORT).show();
                    return;
                }
                HDsua();
            }
        });
    }
    private void HDsua() {
        database db = new database(this);
        hoaDon hd = getHoaDon();
        db.updateHoaDon(hd);
        thongbao("S???A TH??NH C??NG","B???n c?? ?????ng ?? tho??t ch????ng tr??nh kh??ng?");
    }
    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // N??t Ok
        b.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(suaHoaDonActivity.this, hoaDonActivity.class);
                startActivity(intent);
            }
        });
        //N??t Cancel
        b.setNegativeButton("Kh??ng ?????ng ??", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //T???o dialog
        AlertDialog al = b.create();
        //Hi???n th???
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
