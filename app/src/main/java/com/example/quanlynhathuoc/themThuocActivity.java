package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class themThuocActivity extends AppCompatActivity {
    EditText edtMaThuoc, edtTenThuoc, edtDVT, edtDonGia;
    Button btnXacNhanThemT,btnTroVeT;
    ImageView imgAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmthemthuoc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnXacNhanThemT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (edtMaThuoc.getText().length() == 0)
                {
                    Toast.makeText(themThuocActivity.this, "Vui lòng nhập Mã Thuốc!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtTenThuoc.getText().length() == 0)
                {
                    Toast.makeText(themThuocActivity.this, "Vui lòng nhập Tên Thuốc!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtDVT.getText().length() == 0)
                {
                    Toast.makeText(themThuocActivity.this, "Vui lòng nhập Đơn Vị Tính!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtDonGia.getText().length() == 0)
                {
                    Toast.makeText(themThuocActivity.this, "Vui lòng nhập Đơn Giá!", Toast.LENGTH_SHORT).show();
                    return;
                }
                TNhap();
            }
        });
        btnTroVeT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(themThuocActivity.this, thuocActivity.class);
                startActivity(intent);
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallarie();
            }
        });
    }

    private void setControl() {
        btnXacNhanThemT = findViewById(R.id.btnXacNhanThemT);
        edtMaThuoc = findViewById(R.id.edtMaThuoc);
        edtTenThuoc = findViewById(R.id.edtTenThuoc);
        edtDVT = findViewById(R.id.edtDVT);
        edtDonGia = findViewById(R.id.edtDonGia);
        btnTroVeT = findViewById(R.id.btnTroVeT);
        imgAdd = findViewById(R.id.imgAdd_insert);
    }
    private void TNhap() {
        database db = new database(this);
        thuoc t = getThuoc();
        Cursor cursor = db.checkThuoc(t.getMaThuoc());
        int result=-1;
        while (cursor.moveToNext())
        {
            result = cursor.getInt(0);
            break;
        }
        if(result<0) {
            db.saveThuoc(t);
            thongbao("THÊM THÀNH CÔNG","Bạn có đồng ý thoát chương trình không?");
        }
        else if(result>=0)
        {
            thongbao("ĐÃ TỒN TẠI THUỐC TUONG TỰ","Bạn có đồng ý thoát chương trình không?");
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
                Intent intent = new Intent(themThuocActivity.this, thuocActivity.class);
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
    private thuoc getThuoc() {
        thuoc t = new thuoc();
        t.setMaThuoc(edtMaThuoc.getText().toString());
        t.setTenThuoc(edtTenThuoc.getText().toString());
        t.setDVT(edtDVT.getText().toString());
        t.setDonGia(Float.valueOf(edtDonGia.getText().toString()));
        Bitmap bitmap = ((BitmapDrawable) imgAdd.getDrawable()).getBitmap();
        ByteArrayOutputStream img = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, img);
        byte[] imageInByte = img.toByteArray();
        t.setImageMedical(imageInByte);
        return t;
    }

    public void openGallarie()
    {
        Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
        intentImg.setType("image/*");
        startActivityForResult(intentImg, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                imgAdd.setImageBitmap(decodeStream);
            }
            catch (Exception ex)
            {
                Log.e("ex", ex.getMessage());
            }
        }
    }
}
