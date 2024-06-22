package com.example.designpattern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.designpattern.Adapter.QuestionAdapter;
import com.example.designpattern.Interface.OnAnswerClickListener;
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
    private CardView cv_check_answer;
    private ProgressBar progressBar;
    private TextView tv_check_answer;
    private String PatternName;
    private List<Question> mListQuestion;
    private int curQuestion = 0;
    private PatternQuestionService patternQuestionService;
    private QuestionAdapter questionAdapter;
    private RecyclerView rcv_question;
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

        cv_check_answer.setOnClickListener(this);
        cv_check_answer.setEnabled(false);
        questionAdapter = new QuestionAdapter(this, new OnAnswerClickListener() {
            @Override
            public void onAnswerClicked() {
                cv_check_answer.setEnabled(true);
            }
        });

        mListQuestion = getListQuestion();
        if(mListQuestion.isEmpty()){
            return;
        }
        questionAdapter.setData(mListQuestion.get(curQuestion));
        rcv_question.setAdapter(questionAdapter);
        updateProgress();
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
        rcv_question = findViewById(R.id.rcv_question);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_question.setLayoutManager(linearLayoutManager);
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
        if (v.getId() == R.id.cv_check_answer){
            setAnimation(R.anim.layout_animation_right_to_left);
        }
    }

    private void setAnimation(int animResource){
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(this, animResource);
        rcv_question.setLayoutAnimation(animationController);

        nextQuestion();
    }

    private void nextQuestion() {
        if(curQuestion == mListQuestion.size() - 1){
            tv_check_answer.setText(R.string.done);
            updatePatternIsDone(PatternName);
            onCLickGoToActivityResult(PatternName);
        } else if (curQuestion == mListQuestion.size() - 2) {
            cv_check_answer.setEnabled(false);
            curQuestion++;
            updateProgress();
            tv_check_answer.setText(R.string.done);
            questionAdapter.setData(mListQuestion.get(curQuestion));
            rcv_question.setAdapter(questionAdapter);
        } else{
            cv_check_answer.setEnabled(false);
            curQuestion++;
            updateProgress();
            tv_check_answer.setText(R.string.next_question);
            questionAdapter.setData(mListQuestion.get(curQuestion));
            rcv_question.setAdapter(questionAdapter);
        }
    }

    private void onCLickGoToActivityResult(String patternName){

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