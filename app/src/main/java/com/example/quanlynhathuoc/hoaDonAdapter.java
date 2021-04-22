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

public class hoaDonAdapter extends ArrayAdapter<hoaDon> {
    Context context;
    int layoutResourceId;
    ArrayList<hoaDon> data = null;

    public hoaDonAdapter(Context context, int layoutResourceId, ArrayList<hoaDon> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }
    static class hoaDonHolder
    {
        TextView edtSoHd,edtNgayHD,edtMaNT;
        Button btnXoa;
    }
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        hoaDonHolder holder = null;
        if(row!=null)
        {
            holder = (hoaDonHolder) row.getTag();
        }
        else
        {
            holder = new hoaDonHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.listviewhoadon,parent,false);
            holder.edtSoHd = row.findViewById(R.id.listSoHD);
            holder.edtNgayHD = row.findViewById(R.id.listNgayHD);
            holder.edtMaNT = row.findViewById(R.id.listMaNT);
            row.setTag(holder);
        }
        final hoaDon nt = data.get(position);
        holder.edtSoHd.setText(nt.getMaHD());
        holder.edtNgayHD.setText(nt.getNgayHD());
        holder.edtMaNT.setText(nt.getMaNT());
        return row;
    }
}
