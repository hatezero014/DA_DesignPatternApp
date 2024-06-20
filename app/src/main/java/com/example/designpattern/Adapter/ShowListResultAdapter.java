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
import com.example.designpattern.Models.PatternQuestion;
import com.example.designpattern.R;

import java.util.List;

public class ShowListResultAdapter extends RecyclerView.Adapter<ShowListResultAdapter.ShowListResultHolder>{
    List<PatternQuestion> patternQuestionList;
    Context context;

    int count = 1;

    public ShowListResultAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PatternQuestion> list){
        patternQuestionList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ShowListResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_result_pattern, parent, false);

        return new ShowListResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowListResultHolder holder, int position) {
        PatternQuestion patternQuestion = patternQuestionList.get(position);
        if(patternQuestion==null) return;
        holder.tvQuestion.setText("Câu" + count);
        count++;
        if(patternQuestion.getIsCorrect() == 1){
            holder.imageView.setImageResource(R.drawable.icon_correct);
            holder.tvAnswer.setText("Đáp án chính xác");
        }
        else {
            holder.imageView.setImageResource(R.drawable.icon_wrong);
            holder.tvAnswer.setText("Đáp án sai");
        }
    }

    @Override
    public int getItemCount() {
        return patternQuestionList.size();
    }

    class ShowListResultHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tvQuestion;
        TextView tvAnswer;
        ImageView imageView;

        public ShowListResultHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_checked);
            tvQuestion = itemView.findViewById(R.id.tv_question0);
            tvAnswer = itemView.findViewById(R.id.tv_answer_0);
        }
    }
}
