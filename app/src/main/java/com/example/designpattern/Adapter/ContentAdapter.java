package com.example.designpattern.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Models.Content;
import com.example.designpattern.R;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private List<Content> mContent;

    public void setData(List<Content>list){
        this.mContent = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_element,parent,false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        Content content = mContent.get(position);
        if(content == null)
            return;

        holder.tv_title.setText(content.getTitle());
        holder.tv_content.setText(content.getContent());
    }

    @Override
    public int getItemCount() {
        if(mContent!=null)
            return mContent.size();
        return 0;
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;
        private TextView tv_content;
        private ImageView img_bookmark;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_section_title);
            tv_content = itemView.findViewById(R.id.tv_content_element);
            img_bookmark = itemView.findViewById(R.id.img_bookmark);
        }
    }
}
