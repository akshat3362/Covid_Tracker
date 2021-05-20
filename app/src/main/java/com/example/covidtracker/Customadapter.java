package com.example.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Customadapter extends BaseAdapter {
    Context context;
    ArrayList<String> flags;
    ArrayList<String> countryNames;
    LayoutInflater inflter;


    public Customadapter(Context applicationContext, ArrayList<String> images, ArrayList<String> arrayList) {
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
        this.flags = images;
        this.countryNames =arrayList;

    }


    @Override
    public int getCount() {
        return flags.size();
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
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        Glide.with(icon).load(flags.get(i)).into(icon);
        names.setText(countryNames.get(i));
        return view;
    }
}