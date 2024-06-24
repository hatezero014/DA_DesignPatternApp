package com.example.designpattern.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Interface.OnAnswerClickListener;
import com.example.designpattern.Models.Answer;
import com.example.designpattern.Models.PatternQuestion;
import com.example.designpattern.Models.Question;
import com.example.designpattern.QuestionsActivity;
import com.example.designpattern.R;
import com.example.designpattern.Services.PatternQuestionService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private Context context;
    PatternQuestionService patternQuestionService;
    private OnAnswerClickListener onAnswerClickListener;
    private boolean isClicked = false;

    private String language;


    public QuestionAdapter(Context context, OnAnswerClickListener onAnswerClickListener) {
        this.context = context;
        this.onAnswerClickListener = onAnswerClickListener;
    }

    private Question question;
    public void setData(Question question, String language){
        this.question = question;
        this.language = language;
        this.isClicked = false;
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
        private TextView tvContentQuestion;
        private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContentQuestion = itemView.findViewById(R.id.tv_content_question);
            tvAnswer1 = itemView.findViewById(R.id.tv_answer_1);
            tvAnswer2 = itemView.findViewById(R.id.tv_answer_2);
            tvAnswer3 = itemView.findViewById(R.id.tv_answer_3);
            tvAnswer4 = itemView.findViewById(R.id.tv_answer_4);
//            setEnable();
        }

        public void setData(Question question) {
            if (question == null){
                return;
            }

            tvAnswer1.setBackgroundResource(R.drawable.background_answer);
            tvAnswer2.setBackgroundResource(R.drawable.background_answer);
            tvAnswer3.setBackgroundResource(R.drawable.background_answer);
            tvAnswer4.setBackgroundResource(R.drawable.background_answer);

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
                tvAnswer1.setBackgroundResource(R.drawable.background_correct_answer);
                boolean isCorrect = checkAnswer(question, question.getAnswerList().get(0));
                isClicked = true;
                setDisable(tvAnswer1);
//                setDisableAll();
                if (onAnswerClickListener != null) {
                    onAnswerClickListener.onAnswerClicked(isCorrect, getCorrectAnswer(question));
                }
            } else if (v.getId() == R.id.tv_answer_2) {
                tvAnswer2.setBackgroundResource(R.drawable.background_correct_answer);
                boolean isCorrect = checkAnswer(question, question.getAnswerList().get(1));
                isClicked = true;
                setDisable(tvAnswer2);
//                setDisableAll();
                if (onAnswerClickListener != null) {
                    onAnswerClickListener.onAnswerClicked(isCorrect, getCorrectAnswer(question));
                }
            } else if (v.getId() == R.id.tv_answer_3) {
                tvAnswer3.setBackgroundResource(R.drawable.background_correct_answer);
                boolean isCorrect = checkAnswer(question, question.getAnswerList().get(2));
                isClicked = true;
                setDisable(tvAnswer3);
//                setDisableAll();
                if (onAnswerClickListener != null) {
                    onAnswerClickListener.onAnswerClicked(isCorrect, getCorrectAnswer(question));
                }
            } else if (v.getId() == R.id.tv_answer_4) {
                tvAnswer4.setBackgroundResource(R.drawable.background_correct_answer);
                boolean isCorrect = checkAnswer(question, question.getAnswerList().get(3));
                isClicked = true;
                setDisable(tvAnswer4);
//                setDisableAll();
                if (onAnswerClickListener != null) {
                    onAnswerClickListener.onAnswerClicked(isCorrect, getCorrectAnswer(question));
                }
            }
        }

//        private void setEnable(){
//            if(!isClicked){
//                tvAnswer1.setEnabled(true);
//                tvAnswer2.setEnabled(true);
//                tvAnswer3.setEnabled(true);
//                tvAnswer4.setEnabled(true);
//            }
//        }

        private void setDisable(TextView textView){
            List<TextView> list = Arrays.asList(tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4);
            for(TextView tv : list){
                if(tv != textView){
//                    tv.setEnabled(false);
                    tv.setBackgroundResource(R.drawable.background_answer);
                }
            }
        }

//        private void setDisableAll(){
//            tvAnswer1.setEnabled(false);
//            tvAnswer2.setEnabled(false);
//            tvAnswer3.setEnabled(false);
//            tvAnswer4.setEnabled(false);
//        }

        private boolean checkAnswer(Question question, Answer answer){
            String test = question.getContent();
            List<PatternQuestion> list = new ArrayList<>();
            patternQuestionService = new PatternQuestionService(context);
            if(language.equals("vi")){
                 list = patternQuestionService.GetTableQuestionByQuestionVi(test);
            }
            else if(language.equals("en")){
                list = patternQuestionService.GetTableQuestionByQuestion(test);
            }
            else {
                list = patternQuestionService.GetTableQuestionByQuestion(test);
            }

            if(answer.isCorrect()){
                for(PatternQuestion patternQuestion : list){
                    patternQuestionService.UpdateById(new PatternQuestion(patternQuestion.getPatternId(), patternQuestion.getQuestion(), patternQuestion.getAnswer1(), patternQuestion.getAnswer2(), patternQuestion.getAnswer3(), patternQuestion.getAnswer4(),patternQuestion.getQuestionVi(),patternQuestion.getAnswer1Vi(),patternQuestion.getAnswer2Vi(),patternQuestion.getAnswer3Vi(),patternQuestion.getAnswer4Vi(),patternQuestion.getAnsCorrect(), 1), patternQuestion.getId());                }
                return true;
            }else {
                for(PatternQuestion patternQuestion : list){
                    patternQuestionService.UpdateById(new PatternQuestion(patternQuestion.getPatternId(), patternQuestion.getQuestion(), patternQuestion.getAnswer1(), patternQuestion.getAnswer2(), patternQuestion.getAnswer3(), patternQuestion.getAnswer4(),patternQuestion.getQuestionVi(),patternQuestion.getAnswer1Vi(),patternQuestion.getAnswer2Vi(),patternQuestion.getAnswer3Vi(),patternQuestion.getAnswer4Vi(),patternQuestion.getAnsCorrect(), 0), patternQuestion.getId());                }
                return false;
            }
        }

        private String getCorrectAnswer(Question question) {
            String ansCorrect = null;
            if(question == null || question.getAnswerList() == null || question.getAnswerList().isEmpty()){
                return "";
            }

            if(question.getAnswerList().get(0).isCorrect()){
                ansCorrect = question.getAnswerList().get(0).getContent();
            } else if (question.getAnswerList().get(1).isCorrect()) {
                ansCorrect = question.getAnswerList().get(1).getContent();
            } else if (question.getAnswerList().get(2).isCorrect()) {
                ansCorrect = question.getAnswerList().get(2).getContent();
            } else if (question.getAnswerList().get(3).isCorrect()) {
                ansCorrect = question.getAnswerList().get(3).getContent();
            }
            return ansCorrect;
        }
    }
}
