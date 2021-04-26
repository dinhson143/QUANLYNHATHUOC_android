package com.example.quanlynhathuoc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    /*int flags[];
    String[] countryNames;*/
    LayoutInflater inflter;
    ArrayList<thuoc> data = null;

    public CustomSpinnerAdapter(Context applicationContext, ArrayList<thuoc> data/*int[] flags, String[] countryNames*/) {
        this.context = applicationContext;
        this.data = data;
        /*this.flags = flags;
        this.countryNames = countryNames;*/
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        /*return flags.length;*/
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_ctbl, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageSpinner);
        TextView names = (TextView) view.findViewById(R.id.tvSpinner);
        /*icon.setImageResource(flags[i]);
        names.setText(countryNames[i]);*/
        final thuoc t = data.get(i);
        Bitmap bitmap = BitmapFactory.decodeByteArray(t.getImageMedical(), 0, t.getImageMedical().length);
        icon.setImageBitmap(bitmap);
        names.setText(t.getMaThuoc());
        return view;
    }
}
