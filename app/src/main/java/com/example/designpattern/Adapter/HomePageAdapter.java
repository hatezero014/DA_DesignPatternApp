package com.example.designpattern.Adapter;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.HomePage;
import com.example.designpattern.R;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static int type_content = 1;
    private static int type_button = 2;
    private static int type_rating = 3;
    private List<HomePage> mContent;
    private final IClickItemListener iClickItemListener;

    public HomePageAdapter(IClickItemListener iClickItemListener){
        this.iClickItemListener = iClickItemListener;
    }

    public void setData(List<HomePage> list){
        this.mContent = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(type_content == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content,parent,false);
            return new TVContentViewHolder(view);
        }else if(type_button == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button,parent,false);
            return new ButtonViewHolder(view);
        }else if(type_rating == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating,parent,false);
            return new RatingViewHolder(view);
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomePage homePage = mContent.get(position);
        if(homePage == null){
            return;
        }
        if(type_content == holder.getItemViewType()){
            TVContentViewHolder tvContentViewHolder = (TVContentViewHolder) holder;
            tvContentViewHolder.textView.setText(homePage.getName());
        }else if(type_button == holder.getItemViewType()){
            ButtonViewHolder buttonViewHolder = (ButtonViewHolder) holder;
            buttonViewHolder.textView.setText(homePage.getName());

            buttonViewHolder.cardView.setOnClickListener(v -> iClickItemListener.onClickItem(homePage.getName()));

        }else if(type_rating == holder.getItemViewType()){
            RatingViewHolder ratingViewHolder = (RatingViewHolder) holder;
            ratingViewHolder.textView.setText(homePage.getName()+": ");
            if(homePage.getName().equals("Complexity"))
                ratingViewHolder.ratingBar.setRating(1f);
            else ratingViewHolder.ratingBar.setRating(2f);
        }
    }

    @Override
    public int getItemCount() {
        if(mContent != null)
            return mContent.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        HomePage homePage = mContent.get(position);
        if(homePage.getType() == 1){
            return type_content;
        }
        else if(homePage.getType() == 2){
            return type_button;
        }
        else{
            return type_rating;
        }
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView textView;
        private ImageView img1;
        private ImageView img2;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.btn_goto);
            textView = itemView.findViewById(R.id.tv_go_to);
            img1 = itemView.findViewById(R.id.img1_goto);
            img2 = itemView.findViewById(R.id.img2_goto);
        }
    }

    public class RatingViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private RatingBar ratingBar;
        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_rating_type);
            ratingBar = itemView.findViewById(R.id.rtb_rating);
        }
    }

    public class TVContentViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public TVContentViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
        }
    }
}
