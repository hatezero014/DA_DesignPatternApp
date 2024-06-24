package com.example.designpattern;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.ShowListResultAdapter;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Models.PatternQuestion;
import com.example.designpattern.Services.PatternQuestionService;
import com.example.designpattern.Services.PatternService;
import com.example.designpattern.UI.progress.ProgressCircleView;

import java.util.ArrayList;
import java.util.List;

public class ActivityResultPattern extends BaseActivity {

    private ProgressCircleView progressCircleView;;
    RecyclerView recyclerView;
    TextView textView1, textView2, textView3, textView4;
    PatternQuestionService patternQuestionService;
    ShowListResultAdapter showListResultAdapter;
    PatternService patternService;
    String PatternName;
    int countCorrectAnswer = 0;
    List<PatternQuestion> list;
    Button btnRedo, btnReview;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_pattern);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        textView1 = findViewById(R.id.progress_result_pattern);
        textView2 = findViewById(R.id.title_perfect);
        textView3 = findViewById(R.id.textview3);
        textView4 = findViewById(R.id.textview4);

        btnRedo = findViewById(R.id.btn_Redolesson);
        btnReview = findViewById(R.id.btn_review);

        progressCircleView = findViewById(R.id.progress_circle2);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle == null){
            return;
        }

        PatternName = (String) bundle.get("PatternName");

        list = getListResult();
        for(PatternQuestion patternQuestion : list){
            if(patternQuestion.getIsCorrect() == 1){
                countCorrectAnswer++;
            }
        }

        textView4.setText(countCorrectAnswer+ "/5 " + getString(R.string.string2_result));

        progress = countCorrectAnswer*100/ getListResult().size();
        progressCircleView.setProgress(progress);
        progressCircleView.setStrokeWidth(100);
        progressCircleView.setRadiusOffset(50);
        applyGradientToCircle(progressCircleView);

        applyGradientToTextView(textView1);
        applyGradientToTextView(textView2);
        applyGradientToTextView(textView3);
        applyGradientToTextView(textView4);


        ValueAnimator animator = ValueAnimator.ofInt(0, progress);
        if(progress<=50){
            animator.setDuration(2000);
        }
        else if(progress > 50 && progress <= 100){
            animator.setDuration(3000);
        }

        btnRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetAnwser();
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDesignPattern();
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                progressCircleView.setProgress(animatedValue);
                textView1.setText(animatedValue + "%");
            }
        });
        animator.start();

        recyclerView = findViewById(R.id.rcv_list_answer);
        showListResultAdapter = new ShowListResultAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        showListResultAdapter.setData(getListResult());
        recyclerView.setAdapter(showListResultAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private List<PatternQuestion> getListResult() {
//        patternQuestionService = new PatternQuestionService(this);
//        List<PatternQuestion> list = patternQuestionService.GetAll(PatternQuestion.class);
//        return list;
        List<PatternQuestion> patternQuestionList = new ArrayList<>();
        patternService = new PatternService(this);
        List<Pattern> patternList = patternService.GetPatternIdByName(PatternName);
        int PatternId = 0;
        for(Pattern pattern : patternList){
            PatternId = pattern.getId();
        }
        if(PatternId != 0){
            patternQuestionService = new PatternQuestionService(this);
            patternQuestionList = patternQuestionService.GetQuestionByPatternId(PatternQuestion.class, String.valueOf(PatternId));

        }
        return patternQuestionList;
    }

    private void applyGradientToTextView(final TextView textView) {
        TextPaint paint = textView.getPaint();
        float width = paint.measureText(textView.getText().toString());
        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#EC9008"),
                        Color.parseColor("#07DC2A"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);
    }

    private void applyGradientToCircle(final ProgressCircleView progressCircleView) {
        progressCircleView.setGradientColors(
                ContextCompat.getColor(this, R.color.startColor),
                ContextCompat.getColor(this, R.color.endColor)
        );
    }

    private void onCLickGoToQuestionActivity(String patternName){

        Intent intent = new Intent(this, QuestionsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", patternName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void ResetAnwser(){
        List<PatternQuestion> patternQuestionList = getListResult();
        patternQuestionService = new PatternQuestionService(this);
        for(PatternQuestion patternQuestion : patternQuestionList){
            patternQuestionService.UpdateById(new PatternQuestion(patternQuestion.getPatternId(), patternQuestion.getQuestion(), patternQuestion.getAnswer1(), patternQuestion.getAnswer2(), patternQuestion.getAnswer3(), patternQuestion.getAnswer4(),patternQuestion.getAnsCorrect(), 0), patternQuestion.getId());
        }
        onCLickGoToQuestionActivity(PatternName);
        finish();
    }

    private void gotoDesignPattern(){
        Intent intent = new Intent(this, ShowDesignPatternInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", PatternName);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        showListResultAdapter.setData(getListResult());
        recyclerView.setAdapter(showListResultAdapter);
        progressCircleView.setProgress(progress);
    }
}