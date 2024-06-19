package com.example.designpattern;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;


import com.example.designpattern.Models.Answer;
import com.example.designpattern.Models.PatternQuestion;
import com.example.designpattern.Models.Question;
import com.example.designpattern.Services.PatternQuestionService;
import com.example.designpattern.R;

public class QuestionsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvPatternName;
    private TextView tvQuestion;
    private TextView tvContentQuestion;
    private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    private String PatternName;
    private List<Question> mListQuestion;
    private Question mQuestion;
    private int curQuestion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.assignment));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUI();

        Bundle bundle = this.getIntent().getExtras();
        if(bundle == null){
            return;
        }

        PatternName = (String) bundle.get("PatternName");
        tvPatternName.setText(PatternName);

        mListQuestion = getListQuestion();
        if(mListQuestion.isEmpty()){
            return;
        }
        setDataQuestion(mListQuestion.get(curQuestion));
    }

    private void setDataQuestion(Question question) {
        if (question == null){
            return;
        }

        mQuestion = question;

        tvAnswer1.setBackgroundResource(R.drawable.bg_blue_corner_30);
        tvAnswer2.setBackgroundResource(R.drawable.bg_blue_corner_30);
        tvAnswer3.setBackgroundResource(R.drawable.bg_blue_corner_30);
        tvAnswer4.setBackgroundResource(R.drawable.bg_blue_corner_30);

        String titleQuestion = getString(R.string.question)+" "+question.getNumber();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void initUI(){
        tvPatternName = findViewById(R.id.tv_pattern_name);
        tvQuestion = findViewById(R.id.tv_question);
        tvContentQuestion = findViewById(R.id.tv_content_question);
        tvAnswer1 = findViewById(R.id.tv_answer_1);
        tvAnswer2 = findViewById(R.id.tv_answer_2);
        tvAnswer3 = findViewById(R.id.tv_answer_3);
        tvAnswer4 = findViewById(R.id.tv_answer_4);
    }

    private List<Question> getListQuestion(){
        List<Question> list = new ArrayList<>();

        PatternQuestionService patternQuestionService = new PatternQuestionService(this);
        List<PatternQuestion> patternQuestionList = patternQuestionService.getQuestion(PatternName);

        for(int i = 0; i < patternQuestionList.size(); i++){
            List<Answer> answers = new ArrayList<>();
            int ansCorrect = patternQuestionList.get(i).getAnsCorrect();
            PatternQuestion question = patternQuestionList.get(i);
            for (int j = 0; j < 4; j++){
                switch (j){
                    case 0:
                        if(ansCorrect == 1){
                            answers.add(new Answer(question.getAnswer1(),true));
                        }
                        else{
                            answers.add(new Answer(question.getAnswer1(),false));
                        }
                        break;
                    case 1:
                        if(ansCorrect == 2){
                            answers.add(new Answer(question.getAnswer2(),true));
                        }
                        else{
                            answers.add(new Answer(question.getAnswer2(),false));
                        }
                        break;
                    case 2:
                        if(ansCorrect == 3){
                            answers.add(new Answer(question.getAnswer3(),true));
                        }
                        else{
                            answers.add(new Answer(question.getAnswer3(),false));
                        }
                        break;
                    default:
                        if(ansCorrect == 4){
                            answers.add(new Answer(question.getAnswer4(),true));
                        }
                        else{
                            answers.add(new Answer(question.getAnswer4(),false));
                        }
                        break;
                }
            }
            int numQuestion = i+1;
            list.add(new Question(numQuestion,patternQuestionList.get(i).getQuestion(),answers));
        }

        return list;
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_answer_1){
            tvAnswer1.setBackgroundResource(R.drawable.bg_orange_corner_30);
            checkAnswer(tvAnswer1, mQuestion, mQuestion.getAnswerList().get(0));
        } else if (v.getId() == R.id.tv_answer_2) {
            tvAnswer2.setBackgroundResource(R.drawable.bg_orange_corner_30);
            checkAnswer(tvAnswer2, mQuestion, mQuestion.getAnswerList().get(1));
        } else if (v.getId() == R.id.tv_answer_3) {
            tvAnswer3.setBackgroundResource(R.drawable.bg_orange_corner_30);
            checkAnswer(tvAnswer3, mQuestion, mQuestion.getAnswerList().get(2));
        } else if (v.getId() == R.id.tv_answer_4) {
            tvAnswer4.setBackgroundResource(R.drawable.bg_orange_corner_30);
            checkAnswer(tvAnswer4, mQuestion, mQuestion.getAnswerList().get(3));
        }
    }

    private void checkAnswer(TextView textView, Question question, Answer answer){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(answer.isCorrect()){
                    textView.setBackgroundResource(R.drawable.bg_green_corner_30);
                    nextQuestion();
                }else {
                    textView.setBackgroundResource(R.drawable.bg_red_corner_30);
                    showCorrectAnswer(question);
                    nextQuestion();
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

    private void nextQuestion() {
        if(curQuestion == mListQuestion.size() - 1){
            return;
        }else{
            curQuestion++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDataQuestion(mListQuestion.get(curQuestion));
                }
            }, 1000);
        }
    }
}