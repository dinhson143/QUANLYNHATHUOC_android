package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class nhaThuocActivity extends AppCompatActivity {

    private ListView lvNhaThuoc;
    ArrayList<nhaThuoc> data = new ArrayList<>();
    nhaThuocAdapter adapter = null;
    Button NT_btnThem, NT_btnSua,NT_btnXoa,NT_btnXemDS,NT_btnTroVe,NT_btnSearch;
    EditText search;
    nhaThuoc temp =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmnhathuoc);
        setControl();
        setEvent();
    }

    private void setControl()
    {
        NT_btnThem = findViewById(R.id.NT_btnThem);
        NT_btnXoa = findViewById(R.id.NT_btnXoa);
        NT_btnSua = findViewById(R.id.NT_btnSua);
        lvNhaThuoc = findViewById(R.id.lvNhaThuoc);
        NT_btnTroVe = findViewById(R.id.NT_btnTroVe);
        search = findViewById(R.id.editTextTextPersonName);
        NT_btnSearch = findViewById(R.id.btnSearch);
        NT_btnXemDS = findViewById(R.id.NT_btnXemDS);
    }

    private void loadData() {
        database db = new database(this);
        data.clear();
        db.getAllDataNhaThuoc(data);
        adapter.notifyDataSetChanged();
        if(data.size()>0)
            temp=data.get(0);
    }

    private void getSearch(String tenNT)
    {
        database db = new database(this);
        data.clear();
        db.searchNhaThuoc(data,tenNT);
        adapter.notifyDataSetChanged();
        if(data.size()>0)
            temp=data.get(0);   
    }
    public void thongbaoXoa(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                database db = new database(nhaThuocActivity.this);
                db.deleteNhaThuoc(temp.getMaNT());
                loadData();
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
    private void setEvent() {
        adapter = new nhaThuocAdapter(this,R.layout.listviewnhathuoc,data);
        lvNhaThuoc.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadData();
        lvNhaThuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                temp = data.get(position);
            }
        });
        NT_btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nhaThuocActivity.this, themNhaThuocActivity.class);
                startActivity(intent);
            }
        });
        NT_btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongbaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa nhà thuốc?");
            }
        });
        NT_btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nhaThuocActivity.this, suaNhaThuocActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("maNT",temp.getMaNT());
                bundle.putString("tenNT",temp.getTenNT());
                bundle.putString("diachiNT",temp.getDiaChi());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        NT_btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nhaThuocActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        NT_btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = search.getText().toString().trim();
                Log.i("aaa",s);
                if(s.length()>0)
                {
                    lvNhaThuoc.setAdapter(null);
                    lvNhaThuoc.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    getSearch(s);
                }
                else
                {
                    lvNhaThuoc.setAdapter(null);
                    lvNhaThuoc.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    loadData();
                }
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s,int start, int count, int after){
            }

            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count){
    //                nhaThuocActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged (Editable s){
                if (s.length() > 0) {
                    lvNhaThuoc.setAdapter(null);
                    lvNhaThuoc.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    getSearch(s.toString());
                } else {
                    lvNhaThuoc.setAdapter(null);
                    lvNhaThuoc.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    loadData();
                }
            }
        });
                NT_btnXemDS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(nhaThuocActivity.this, hoaDonActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("maNT", temp.getMaNT());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
