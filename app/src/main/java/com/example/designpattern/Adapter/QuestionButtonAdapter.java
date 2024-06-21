package com.example.designpattern.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.QuestionButton;
import com.example.designpattern.R;

import java.util.List;

public class QuestionButtonAdapter extends RecyclerView.Adapter<QuestionButtonAdapter.QuestionButtonViewHolder> {
    private Context context;
    private IClickItemListener iClickItemListener;

    public QuestionButtonAdapter(Context context, IClickItemListener iClickItemListener) {
        this.context = context;
        this.iClickItemListener = iClickItemListener;
    }
    private List<QuestionButton> questionButtons;
    public void setData(List<QuestionButton> list){
        this.questionButtons = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_button,parent,false);
        return new QuestionButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionButtonViewHolder holder, int position) {
        QuestionButton questionButton = questionButtons.get(position);
        if(questionButton == null){
            return;
        }

        holder.setFormat(questionButton);
    }

    @Override
    public int getItemCount() {
        if(questionButtons!=null)
            return questionButtons.size();
        return 0;
    }

    public class QuestionButtonViewHolder extends RecyclerView.ViewHolder{
        private CardView cv_question_button;
        private TextView tv_pattern, tv_status;
        private ImageView img_pattern;
        public QuestionButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pattern = itemView.findViewById(R.id.tv_pattern);
            img_pattern = itemView.findViewById(R.id.img_hinh);
            tv_status = itemView.findViewById(R.id.tv_status);
            cv_question_button = itemView.findViewById(R.id.cv_question_button);
        }

        public void setFormat(QuestionButton questionButton) {
            tv_pattern.setText(questionButton.getPatternName());
            //int status = questionButton.getStatus();
            String image = questionButton.getImgPattern();
            int imgResourceId = context.getResources().getIdentifier(image,"drawable", context.getPackageName());
            Drawable drawable = context.getDrawable(imgResourceId);
            img_pattern.setImageDrawable(drawable);

            cv_question_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemListener.onClickItem(questionButton.getPatternName());
                }
            });
        }
    }
}
