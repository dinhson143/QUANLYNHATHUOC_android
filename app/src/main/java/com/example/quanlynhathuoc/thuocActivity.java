package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class thuocActivity extends AppCompatActivity {
    EditText timKiem;
    ListView lvThuoc;
    Button btnThem, btnXoa, btnSua, btnTroVe, btnTK, btnDS;
    //EditText searchT;
    TextView tv;
    ArrayList<thuoc> data = new ArrayList<>();
    thuocAdapter adapter = null;
    thuoc temp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmthuoc);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.formactionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.AB_xemDS:
            {
                return true;
            }
            case R.id.Ab_them:
            {
                Intent intent = new Intent(thuocActivity.this, themThuocActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.AB_xoa:
            {
                thongBaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa nhà thuốc?");
                return true;
            }
            case R.id.AB_sua:
            {
                Intent intent = new Intent(thuocActivity.this, suaThuocActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("maT",temp.getMaThuoc());
                bundle.putString("tenT",temp.getTenThuoc());
                bundle.putString("DVT",temp.getDVT());
                bundle.putFloat("donGia",temp.getDonGia());
                bundle.putByteArray("hinhAnh", temp.getImageMedical());
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(thuocActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setControl()
    {
        timKiem = findViewById(R.id.editTextTextPersonNameT);
        lvThuoc = findViewById(R.id.lvThuoc);
        btnThem = findViewById(R.id.T_btnThem);
        btnXoa = findViewById(R.id.T_btnXoa);
        btnSua = findViewById(R.id.T_btnSua);
        btnTroVe = findViewById(R.id.T_btnTroVe);
        //searchT = findViewById(R.id.editTextTextPersonNameT);
        btnTK = findViewById(R.id.btnTK);
        btnDS = findViewById(R.id.T_btnXemDS);
        tv = findViewById(R.id.textView2);
    }

    private void loadData()
    {
        database db = new database(this);
        data.clear();
        db.getAllDataThuoc(data);
        adapter.notifyDataSetChanged();
        if (data.size() > 0)
        {
            temp = data.get(0);
        }
    }

    private void getSearch(String maThuoc)
    {
        database db = new database(this);
        data.clear();
        db.searchThuoc(data,maThuoc);
        adapter.notifyDataSetChanged();
        if(data.size()>0)
            temp=data.get(0);
    }

    public void thongBaoXoa(String title, String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                database db = new database(thuocActivity.this);
                db.deleteThuoc(temp.getMaThuoc());
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

    private void setEvent()
    {
        adapter = new thuocAdapter(this, R.layout.listviewthuoc, data);
        lvThuoc.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadData();
        lvThuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                temp = data.get(position);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thuocActivity.this, themThuocActivity.class);
                startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongBaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa nhà thuốc?");
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thuocActivity.this, suaThuocActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("maT",temp.getMaThuoc());
                bundle.putString("tenT",temp.getTenThuoc());
                bundle.putString("DVT",temp.getDVT());
                bundle.putFloat("donGia",temp.getDonGia());
                bundle.putByteArray("hinhAnh", temp.getImageMedical());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thuocActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*NT_btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = search.getText().toString().trim();
                //Log.i("aaa",s);
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
        });*/
        timKiem.addTextChangedListener(new TextWatcher() {
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
                    lvThuoc.setAdapter(null);
                    lvThuoc.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    getSearch(s.toString());
                } else {
                    lvThuoc.setAdapter(null);
                    lvThuoc.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    loadData();
                }
            }
        });

        btnTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database db = new database(thuocActivity.this);
                data.clear();
                db.thongKe(data);
                adapter.notifyDataSetChanged();
                if(data.size()>0)
                {
                    temp=data.get(0);
                }
                tv.setText("THỐNG KÊ THUỐC");
            }
        });

        btnDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                tv.setText(R.string.thuoc);
            }
        });
    }
}
