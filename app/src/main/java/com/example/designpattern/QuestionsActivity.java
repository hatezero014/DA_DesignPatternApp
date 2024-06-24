package com.example.designpattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
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
    private ProgressBar progressBar;
    private String PatternName;
    private List<Question> mListQuestion;
    private int curQuestion = 0;
    private PatternQuestionService patternQuestionService;
    private QuestionAdapter questionAdapter;
    private RecyclerView rcv_question;
    Boolean test;
    private Button btn_Check;
    private boolean layoutTheme;
    private String ans_correct;

    String language;
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
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        language = prefs.getString("My_Language", "");

        PatternName = (String) bundle.get("PatternName");
        test = (Boolean) bundle.get("check");
        tvPatternName.setText(PatternName);

        btn_Check.setOnClickListener(this);
        btn_Check.setEnabled(false);
        btn_Check.setBackgroundResource(R.drawable.background_answer);
        questionAdapter = new QuestionAdapter(this, new OnAnswerClickListener() {
            @Override
            public void onAnswerClicked(boolean isCorrect, String ansCorrect) {
                layoutTheme = isCorrect;
                ans_correct = ansCorrect;
                btn_Check.setEnabled(true);
                btn_Check.setBackgroundResource(R.drawable.background_correct_answer);
            }
        });
        mListQuestion = getListQuestion();
        if(mListQuestion.isEmpty()){
            return;
        }
        questionAdapter.setData(mListQuestion.get(curQuestion), language);
        rcv_question.setAdapter(questionAdapter);
        updateProgress();
    }


    @Override
    protected void onResume() {
        super.onResume();

        updateProgress();
    }

    private void updateProgress(){
        float progress = ((float) (curQuestion) / mListQuestion.size()) * 100;

        progressBar.setProgress((int) progress, true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if(test==null || !test){
                onCLickGoToDSGInfoActivity(PatternName);
            }
            else
            {
                finish();
                test = false;

            }
//            else {
//                onCLickGoToDSGInfoActivity(PatternName);
//            }

        }
        return true;
    }

    private void initUI(){
        progressBar = findViewById(R.id.progress_bar);
        btn_Check = findViewById(R.id.btn_Check);
        btn_Check.setText(R.string.check);
        tvPatternName = findViewById(R.id.tv_pattern_name);
        rcv_question = findViewById(R.id.rcv_question);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_question.setLayoutManager(linearLayoutManager);
    }

    private List<Question> getListQuestion(){
        List<Question> list = new ArrayList<>();

        PatternQuestionService patternQuestionService = new PatternQuestionService(this);
        List<PatternQuestion> patternQuestionList = patternQuestionService.getQuestion(PatternName);

        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Language", "");

        for(int i = 0; i < patternQuestionList.size(); i++){
            List<Answer> answers = new ArrayList<>();
            int ansCorrect = patternQuestionList.get(i).getAnsCorrect();
            PatternQuestion question = patternQuestionList.get(i);
            for (int j = 0; j < 4; j++){
                switch (j){
                    case 0:
                        if(ansCorrect == 1){
                            answers.add(new Answer(getAnswerBasedOnLanguage(question.getAnswer1(), question.getAnswer1Vi(), language),true));
                        }
                        else{
                            answers.add(new Answer(getAnswerBasedOnLanguage(question.getAnswer1(), question.getAnswer1Vi(), language),false));
                        }
                        break;
                    case 1:
                        if(ansCorrect == 2){
                            answers.add(new Answer(getAnswerBasedOnLanguage(question.getAnswer2(), question.getAnswer2Vi(), language),true));
                        }
                        else{
                            answers.add(new Answer(getAnswerBasedOnLanguage(question.getAnswer2(), question.getAnswer2Vi(), language),false));
                        }
                        break;
                    case 2:
                        if(ansCorrect == 3){
                            answers.add(new Answer(getAnswerBasedOnLanguage(question.getAnswer3(), question.getAnswer3Vi(), language),true));
                        }
                        else{
                            answers.add(new Answer(getAnswerBasedOnLanguage(question.getAnswer3(), question.getAnswer3Vi(), language),false));
                        }
                        break;
                    default:
                        if(ansCorrect == 4){
                            answers.add(new Answer(getAnswerBasedOnLanguage(question.getAnswer4(), question.getAnswer4Vi(), language),true));
                        }
                        else{
                            answers.add(new Answer(getAnswerBasedOnLanguage(question.getAnswer4(), question.getAnswer4Vi(), language),false));
                        }
                        break;
                }
            }
            int numQuestion = i+1;
            list.add(new Question(numQuestion,getQuestionBasedOnLanguage(question.getQuestion(), question.getQuestionVi(), language),answers));
        }

        return list;
    }

    private String getAnswerBasedOnLanguage(String ans, String ansVi, String language){
        String answer;
        if(language.equals("en")){
            answer = ans;
        } else if (language.equals("vi")) {
            answer = ansVi;
        }
        else answer = ans;
        return answer;
    }

    private String getQuestionBasedOnLanguage(String ques, String quesVi, String language){
        String question;
        if(language.equals("en")){
            question = ques;
        } else if (language.equals("vi")) {
            question = quesVi;
        }
        else question = ques;
        return question;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Check){
            openDetailDiaLog(Gravity.BOTTOM);
            //setAnimation(R.anim.layout_animation_right_to_left);
        }
    }

    private void setAnimation(int animResource){
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(this, animResource);
        rcv_question.setLayoutAnimation(animationController);

        nextQuestion();
    }

    private void nextQuestion() {
        if(curQuestion == mListQuestion.size() - 1){
            updatePatternIsDone(PatternName);
            onCLickGoToActivityResult(PatternName);
        } else if (curQuestion == mListQuestion.size() - 2) {
            btn_Check.setEnabled(false);
            btn_Check.setBackgroundResource(R.drawable.background_answer);
            curQuestion++;
            updateProgress();
            questionAdapter.setData(mListQuestion.get(curQuestion), language);
            rcv_question.setAdapter(questionAdapter);
        } else{
            btn_Check.setEnabled(false);
            btn_Check.setBackgroundResource(R.drawable.background_answer);
            curQuestion++;
            updateProgress();
            questionAdapter.setData(mListQuestion.get(curQuestion), language);
            rcv_question.setAdapter(questionAdapter);
        }
    }

    private void onCLickGoToActivityResult(String patternName){

        Intent intent = new Intent(this, ActivityResultPattern.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", patternName);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void onCLickGoToDSGInfoActivity(String patternName){
        Intent intent = new Intent(this, ShowDesignPatternInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", patternName);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
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

    private void openDetailDiaLog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.correct_dialog);

        Window window = dialog.getWindow();
        if(window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttibutes = window.getAttributes();
        windowAttibutes.gravity = gravity;
        window.setAttributes(windowAttibutes);

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        LinearLayout linearLayout = dialog.findViewById(R.id.linear_dialog);
        TextView tvTitleCorrect = dialog.findViewById(R.id.TitleCorrect);
        TextView tvContentAnswer = dialog.findViewById(R.id.ContentAnswer);
        Button btn_Continue = dialog.findViewById(R.id.btn_Continue);
        ImageView img_dog = dialog.findViewById(R.id.img_dog);

        if(layoutTheme)
        {
            linearLayout.setBackgroundResource(R.drawable.background_green);
            tvTitleCorrect.setText(R.string.correct_answer);
            img_dog.setImageResource(R.drawable.dog);
        }
        else
        {
            linearLayout.setBackgroundResource(R.drawable.bg_incorrect);
            tvTitleCorrect.setText(R.string.wrong_answer);
            img_dog.setImageResource(R.drawable.dogsad);
        }
        tvContentAnswer.setText(ans_correct);

        if(curQuestion == mListQuestion.size() - 1){
            btn_Continue.setText(R.string.done);
        }
        else btn_Continue.setText(R.string.next_question);

        btn_Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation(R.anim.layout_animation_right_to_left);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}