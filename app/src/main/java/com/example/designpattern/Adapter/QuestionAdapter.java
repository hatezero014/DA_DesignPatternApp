package com.example.designpattern.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Models.Answer;
import com.example.designpattern.Models.PatternQuestion;
import com.example.designpattern.Models.Question;
import com.example.designpattern.QuestionsActivity;
import com.example.designpattern.R;
import com.example.designpattern.Services.PatternQuestionService;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private Context context;

    public QuestionAdapter(Context context) {
        this.context = context;
    }

    private Question question;
    public void setData(Question question){
        this.question = question;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        if(question == null)
            return;

        holder.setData(question);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvQuestion;
        private TextView tvContentQuestion;
        private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            tvContentQuestion = itemView.findViewById(R.id.tv_content_question);
            tvAnswer1 = itemView.findViewById(R.id.tv_answer_1);
            tvAnswer2 = itemView.findViewById(R.id.tv_answer_2);
            tvAnswer3 = itemView.findViewById(R.id.tv_answer_3);
            tvAnswer4 = itemView.findViewById(R.id.tv_answer_4);
        }

        public void setData(Question question) {
            if (question == null){
                return;
            }

            tvAnswer1.setBackgroundResource(R.drawable.bg_blue_corner_30);
            tvAnswer2.setBackgroundResource(R.drawable.bg_blue_corner_30);
            tvAnswer3.setBackgroundResource(R.drawable.bg_blue_corner_30);
            tvAnswer4.setBackgroundResource(R.drawable.bg_blue_corner_30);

            String titleQuestion = context.getString(R.string.question)+" "+question.getNumber();
            tvQuestion.setText(titleQuestion);
            tvContentQuestion.setText(question.getContent());
            tvAnswer1.setText(question.getAnswerList().get(0).getContent());
            tvAnswer2.setText(question.getAnswerList().get(1).getContent());
            tvAnswer3.setText(question.getAnswerList().get(2).getContent());
            tvAnswer4.setText(question.getAnswerList().get(3).getContent());

            tvAnswer1.setOnClickListener(this);
            tvAnswer2.setOnClickListener(this);
            tvAnswer3.setOnClickListener(this);
            tvAnswer4.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.tv_answer_1){
                tvAnswer1.setBackgroundResource(R.drawable.bg_orange_corner_30);
                checkAnswer(tvAnswer1, question, question.getAnswerList().get(0));
            } else if (v.getId() == R.id.tv_answer_2) {
                tvAnswer2.setBackgroundResource(R.drawable.bg_orange_corner_30);
                checkAnswer(tvAnswer2, question, question.getAnswerList().get(1));
            } else if (v.getId() == R.id.tv_answer_3) {
                tvAnswer3.setBackgroundResource(R.drawable.bg_orange_corner_30);
                checkAnswer(tvAnswer3, question, question.getAnswerList().get(2));
            } else if (v.getId() == R.id.tv_answer_4) {
                tvAnswer4.setBackgroundResource(R.drawable.bg_orange_corner_30);
                checkAnswer(tvAnswer4, question, question.getAnswerList().get(3));
            }
        }

        private void checkAnswer(TextView textView, Question question, Answer answer){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(answer.isCorrect()){
                        textView.setBackgroundResource(R.drawable.bg_green_corner_30);
                    }else {
                        textView.setBackgroundResource(R.drawable.bg_red_corner_30);
                        showCorrectAnswer(question);
                    }
                }
            }, 1000);
        }

        private void showCorrectAnswer(Question question) {
            if(question == null || question.getAnswerList() == null || question.getAnswerList().isEmpty()){
                return;
            }

            if(question.getAnswerList().get(0).isCorrect()){
                tvAnswer1.setBackgroundResource(R.drawable.bg_green_corner_30);
            } else if (question.getAnswerList().get(1).isCorrect()) {
                tvAnswer2.setBackgroundResource(R.drawable.bg_green_corner_30);
            } else if (question.getAnswerList().get(2).isCorrect()) {
                tvAnswer3.setBackgroundResource(R.drawable.bg_green_corner_30);
            } else if (question.getAnswerList().get(3).isCorrect()) {
                tvAnswer4.setBackgroundResource(R.drawable.bg_green_corner_30);
            }
        }
    }
}
