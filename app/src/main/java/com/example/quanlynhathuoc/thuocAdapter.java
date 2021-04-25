package com.example.quanlynhathuoc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class    thuocAdapter extends ArrayAdapter<thuoc> {
    Context context;
    int layoutResourceId;
    ArrayList<thuoc> data = null;

    public thuocAdapter(Context context, int layoutResourceId, ArrayList<thuoc> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    static class thuocHolder
    {
        TextView tvMaThuoc, tvTenThuoc, tvDVT, tvDonGia;
        ImageView img;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        thuocHolder holder = null;
        if (row != null)
        {
            holder = (thuocHolder) row.getTag();
        }
        else
        {
            holder = new thuocHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.listviewthuoc, parent, false);
            holder.tvMaThuoc = row.findViewById(R.id.listMaThuoc);
            holder.tvTenThuoc = row.findViewById(R.id.listTenThuoc);
            holder.tvDVT = row.findViewById(R.id.listDVT);
            holder.tvDonGia = row.findViewById(R.id.listDonGia);
            holder.img = row.findViewById(R.id.listImage);
            row.setTag(holder);
        }
        final thuoc t = data.get(position);
        holder.tvMaThuoc.setText(t.getMaThuoc());
        holder.tvTenThuoc.setText(t.getTenThuoc());
        holder.tvDVT.setText(t.getDVT());
        holder.tvDonGia.setText(String.valueOf(t.getDonGia()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(t.getImageMedical(), 0, t.getImageMedical().length);
        holder.img.setImageBitmap(bitmap);
        return row;
    }
}
