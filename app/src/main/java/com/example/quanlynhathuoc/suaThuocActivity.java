package com.example.quanlynhathuoc;

import android.content.DialogInterface;
import android.content.Intent;
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

public class suaThuocActivity extends AppCompatActivity {
    EditText edtMaThuoc, edtTenThuoc, edtDVT, edtDonGia;
    Button btnSuaT,btnReload,btnExit;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmsuathuoc);
        setControl();
        setEvent();
        edtMaThuoc.setEnabled(false);
    }

    private void setEvent() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(suaThuocActivity.this, thuocActivity.class);
                startActivity(intent);
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTenThuoc.setText("");
                edtDVT.setText("");
                edtDonGia.setText("");
            }
        });
        btnSuaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTenThuoc.getText().length() == 0)
                {
                    Toast.makeText(suaThuocActivity.this, "Vui lòng nhập Tên Thuốc!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtDVT.getText().length() == 0)
                {
                    Toast.makeText(suaThuocActivity.this, "Vui lòng nhập Đơn Vị Tính!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtDonGia.getText().length() == 0)
                {
                    Toast.makeText(suaThuocActivity.this, "Vui lòng nhập Đơn Giá!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Tsua();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallarie();
            }
        });
    }

    private void Tsua() {
        database db = new database(this);
        thuoc t = getThuoc();
        db.updateThuoc(t);
        thongbao("SỬA THÀNH CÔNG","Bạn có đồng ý thoát chương trình không?");
    }

    private void setControl() {
        btnSuaT = findViewById(R.id.btnSuaT);
        edtMaThuoc = findViewById(R.id.edtMaThuoc);
        edtTenThuoc = findViewById(R.id.edtTenThuoc);
        edtDVT = findViewById(R.id.edtDVT);
        edtDonGia = findViewById(R.id.edtDonGia);
        btnReload = findViewById(R.id.btnReloadformSua);
        btnExit = findViewById(R.id.btnExit);
        img = findViewById(R.id.imgAdd_Update);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        edtMaThuoc.setText(bundle.getString("maT"));
        edtTenThuoc.setText(bundle.getString("tenT"));
        edtDVT.setText(bundle.getString("DVT"));
        edtDonGia.setText(String.valueOf(bundle.getFloat("donGia")));
        Bitmap bitmap = BitmapFactory.decodeByteArray(bundle.getByteArray("hinhAnh"), 0, bundle.getByteArray("hinhAnh").length);
        img.setImageBitmap(bitmap);
    }
    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(suaThuocActivity.this, thuocActivity.class);
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
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream imgT = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, imgT);
        byte[] imageInByte = imgT.toByteArray();
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
                img.setImageBitmap(decodeStream);
            }
            catch (Exception ex)
            {
                Log.e("ex", ex.getMessage());
            }
        }
    }
}
