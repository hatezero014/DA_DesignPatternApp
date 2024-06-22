package com.example.designpattern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;


import com.example.designpattern.Models.Answer;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Models.PatternQuestion;
import com.example.designpattern.Models.Question;
import com.example.designpattern.Services.PatternQuestionService;
import com.example.designpattern.R;
import com.example.designpattern.Services.PatternService;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class QuestionsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvPatternName;
    private TextView tvQuestion;
    private TextView tvContentQuestion;
    private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    private CardView cv_check_answer;
    private ProgressBar progressBar;
    private TextView tv_check_answer;
    private String PatternName;
    private List<Question> mListQuestion;
    private Question mQuestion;
    private int curQuestion = 0;
    PatternQuestionService patternQuestionService;
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

        updateProgress();
        cv_check_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        updateProgress();
    }

    private void updateProgress(){
        float progress = ((float) (curQuestion ) / mListQuestion.size()) * 100;

        progressBar.setProgress((int) progress, true);
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
        cv_check_answer = findViewById(R.id.cv_check_answer);
        progressBar = findViewById(R.id.progress_bar);
        tv_check_answer = findViewById(R.id.tv_check_answer);
        tv_check_answer.setText(R.string.next_question);
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
                    String test = question.getContent();
                    patternQuestionService = new PatternQuestionService(QuestionsActivity.this);
                    List<PatternQuestion> list = patternQuestionService.GetTableQuestionByQuestion(test);
                    for(PatternQuestion patternQuestion : list){
                        patternQuestionService.UpdateById(new PatternQuestion(patternQuestion.getPatternId(), patternQuestion.getQuestion(), patternQuestion.getAnswer1(), patternQuestion.getAnswer2(), patternQuestion.getAnswer3(), patternQuestion.getAnswer4(), patternQuestion.getAnsCorrect(), 1),patternQuestion.getId());
                    }

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

    private void nextQuestion() {
        if(curQuestion == mListQuestion.size() - 1){
            tv_check_answer.setText(R.string.done);
            onCLickGoToActivityResult(PatternName);
        } else if (curQuestion == mListQuestion.size() - 2) {
            curQuestion++;
            updateProgress();
            tv_check_answer.setText(R.string.done);
            setDataQuestion(mListQuestion.get(curQuestion));
        } else{
            curQuestion++;
            updateProgress();
            tv_check_answer.setText(R.string.next_question);
            setDataQuestion(mListQuestion.get(curQuestion));
        }
    }

    private void onCLickGoToActivityResult(String patternName){
        updatePatternIsDone(patternName);
        Intent intent = new Intent(this, ActivityResultPattern.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", patternName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void updatePatternIsDone(String patternName){
        PatternService patternService = new PatternService(this);
        List<Pattern> patternList = patternService.GetPatternIdByName(patternName);
//        int PatternId = 0;
        for(Pattern pattern : patternList){
//            PatternId = pattern.getId();
            patternService.UpdateById(new Pattern(pattern.getName(), pattern.getCatalog(), pattern.getLanguage(), pattern.getImage(),pattern.getVideo(),1), pattern.getId());
        }
//        if(PatternId != 0){
//            patternQuestionService = new PatternQuestionService(this);
//            //patternQuestionList = patternQuestionService.GetQuestionByPatternId(PatternQuestion.class, String.valueOf(PatternId));
//
//        }
    }
}