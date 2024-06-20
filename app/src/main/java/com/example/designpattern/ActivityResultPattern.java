package com.example.designpattern;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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

public class ActivityResultPattern extends AppCompatActivity {

    private ProgressCircleView progressCircleView;;
    RecyclerView recyclerView;
    TextView textView1, textView2;
    PatternQuestionService patternQuestionService;
    ShowListResultAdapter showListResultAdapter;
    PatternService patternService;
    String PatternName;
    int countCorrectAnswer = 0;

    List<PatternQuestion> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_pattern);

        textView1 = findViewById(R.id.progress_result_pattern);
        textView2 = findViewById(R.id.title_perfect);
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

        int progress = countCorrectAnswer*100/ getListResult().size();
        progressCircleView.setProgress(progress);
        progressCircleView.setStrokeWidth(100);
        progressCircleView.setRadiusOffset(50);
        applyGradientToCircle(progressCircleView);

        applyGradientToTextView(textView1);
        applyGradientToTextView(textView2);

        ValueAnimator animator = ValueAnimator.ofInt(0, progress);
        if(progress<=50){
            animator.setDuration(2000);
        }
        else if(progress > 50 && progress <= 100){
            animator.setDuration(3000);
        }

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
}