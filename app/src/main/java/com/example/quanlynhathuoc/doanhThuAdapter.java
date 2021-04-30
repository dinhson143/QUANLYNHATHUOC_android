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

public class doanhThuAdapter extends ArrayAdapter {
    Context context;
    int layoutResourceId;
    ArrayList<doanhThu> data = null;
    public doanhThuAdapter(Context context, int layoutResourceId, ArrayList<doanhThu> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }
    static class doanhThuHolder
    {
        TextView edtMaNT,edtTenNT,edtDoanhThu,edtThoiGian;
        Button btnXoa;
    }
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        doanhThuHolder holder = null;
        if(row!=null)
        {
            holder = (doanhThuHolder) row.getTag();
        }
        else
        {
            holder = new doanhThuHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.listview_doanh_thu,parent,false);
            holder.edtMaNT = row.findViewById(R.id.DT_maNT);
            holder.edtTenNT = row.findViewById(R.id.DT_TenNT);
            holder.edtDoanhThu = row.findViewById(R.id.DT_doanhThu);
            holder.edtThoiGian = row.findViewById(R.id.DT_thoiGian);
            row.setTag(holder);
        }
        final doanhThu nt = data.get(position);
        holder.edtMaNT.setText(nt.getMaNT());
        holder.edtTenNT.setText(nt.getTenNT());
        holder.edtDoanhThu.setText(String.valueOf(nt.getDoanhThu()));
        holder.edtThoiGian.setText(nt.getThoiGian());
        return row;
    }
}
