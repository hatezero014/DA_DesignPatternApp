package com.example.designpattern.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.designpattern.Models.Category;
import com.example.designpattern.Models.Size;
import com.example.designpattern.R;

import java.util.List;

public class SizeAdapter extends ArrayAdapter<Size> {
    public SizeAdapter(@NonNull Context context, int resource, @NonNull List<Size> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView tv = convertView.findViewById(R.id.tv_selected);

        Size size = this.getItem(position);
        if(size!=null){
            tv.setText(String.valueOf(size.getSize()));
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        TextView tv = convertView.findViewById(R.id.tv_category);

        Size size = this.getItem(position);
        if(size!=null){
            tv.setText(String.valueOf(size.getSize()));
        }
        return convertView;
    }
}
