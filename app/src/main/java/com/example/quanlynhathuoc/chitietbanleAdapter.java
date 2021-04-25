package com.example.quanlynhathuoc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class chitietbanleAdapter extends ArrayAdapter<chiTietBanLe> {
    Context context;
    int layoutResourceId;
    ArrayList<chiTietBanLe> data = null;

    public chitietbanleAdapter(Context context, int layoutResourceId, ArrayList<chiTietBanLe> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }
    static class chiTietBanLeHolder
    {
        TextView edtSoHD,edtMaThuoc,edtSoLuong;
        Button btnXoa;
    }
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        chiTietBanLeHolder holder = null;
        if(row!=null)
        {
            holder = (chiTietBanLeHolder) row.getTag();
        }
        else
        {
            holder = new chiTietBanLeHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.listviewchitietbanle,parent,false);
            holder.edtSoHD = row.findViewById(R.id.listSoHoaDon);
            holder.edtMaThuoc = row.findViewById(R.id.listMaThuoc);
            holder.edtSoLuong = row.findViewById(R.id.listSoLuong);
            row.setTag(holder);
        }
        final chiTietBanLe ctbl = data.get(position);
        holder.edtSoHD.setText(ctbl.getSoHD());
        holder.edtMaThuoc.setText(ctbl.getMaThuoc());
        holder.edtSoLuong.setText(String.valueOf(ctbl.getSoLuong()));
        return row;
    }

}
