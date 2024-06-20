package com.example.designpattern.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Models.Pattern;
import com.example.designpattern.R;

import com.example.designpattern.R;

import java.util.List;

public class DesignPatternAdapter extends RecyclerView.Adapter<DesignPatternAdapter.DesignPatternHolder>{
    List<Pattern> patternList;
    Context context;

    public DesignPatternAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Pattern> list){
        patternList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DesignPatternHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_designpattern, parent, false);

        return new DesignPatternHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignPatternHolder holder, int position) {
        Pattern pattern = patternList.get(position);
        if(pattern==null) return;
        Context context1 = holder.imageView.getContext();
        holder.tvDetails.setText(pattern.getName());
        holder.tvCatalog.setText(pattern.getCatalog());
//        if(String.valueOf(pattern.getIsDone()) == null || String.valueOf(pattern.getIsDone()) == ""||pattern.getIsDone() == 0){
//            holder.
//        }
        if(pattern.getIsDone() == 1){
            holder.tvisDone.setText("Đã hoàn thành");
        }
        else {
            holder.tvisDone.setText("Chưa hoàn thành");
        }
    }

    @Override
    public int getItemCount() {
        return patternList.size();
    }

    class DesignPatternHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tvDetails;
        TextView tvisDone;
        TextView tvCatalog;
        ImageView imageView;

        public DesignPatternHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_pattern);
            cardView = itemView.findViewById(R.id.card_view_designpattern);
            tvDetails = itemView.findViewById(R.id.detailsName);
            tvCatalog = itemView.findViewById(R.id.detailsCatalog);
            tvisDone = itemView.findViewById(R.id.isDone);
        }
    }
}
