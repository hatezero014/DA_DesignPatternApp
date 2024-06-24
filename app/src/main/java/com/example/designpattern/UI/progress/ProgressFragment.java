package com.example.designpattern.UI.progress;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.designpattern.Adapter.DesignPatternAdapter;
import com.example.designpattern.Adapter.QuestionButtonAdapter;
import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Models.QuestionButton;
import com.example.designpattern.QuestionsActivity;
import com.example.designpattern.R;
import com.example.designpattern.Services.PatternService;

import java.util.ArrayList;
import java.util.List;


public class ProgressFragment extends Fragment {
    private ProgressCircleView progressCircleView;;
    RecyclerView recyclerView;
//    DesignPatternAdapter designPatternAdapter;
    PatternService patternService;
    private DesignPatternAdapter designPatternAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        final TextView textView1 = view.findViewById(R.id.progress_percent);
        final TextView textView2 = view.findViewById(R.id.title_id);
        progressCircleView = view.findViewById(R.id.progress_circle);

        //String progressText = textView1.getText().toString().replace("%", "");
        int countPatternisDone = countPatternisDone();
        int progress = countPatternisDone*100/getListPattern().size();
        progressCircleView.setProgress(progress);
        progressCircleView.setStrokeWidth(100);
        progressCircleView.setRadiusOffset(50);
        applyGradientToCircle(progressCircleView);

        applyGradientToTextView(textView1);
        applyGradientToTextView(textView2);

        ValueAnimator animator = ValueAnimator.ofInt(0, progress);
        if(progress<=25){
            animator.setDuration(500);
            textView2.setText(R.string.Inception);
        }
        else if(progress > 25 && progress <= 50){
            animator.setDuration(1000);
            textView2.setText(R.string.Foundation);
        }
        if(progress > 50 && progress <= 75){
            animator.setDuration(1500);
            textView2.setText(R.string.Potential);
        }
        else if(progress > 75 && progress <= 100){
            animator.setDuration(2000);
            textView2.setText(R.string.Excellent);
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

        recyclerView = view.findViewById(R.id.rcv_dsp);
//        designPatternAdapter = new DesignPatternAdapter(getContext());

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);

//        designPatternAdapter.setData(getListPattern());
//        recyclerView.setAdapter(designPatternAdapter);

        designPatternAdapter = new DesignPatternAdapter(getContext());

        designPatternAdapter.setData(getData());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(designPatternAdapter);

        return view ;

    }

    private List<Pattern> getListPattern() {
        patternService = new PatternService(getContext());
        List<Pattern> list = patternService.GetAll(Pattern.class);
        return list;
    }

    private void onClickGoToQuestionsActivity(String itemType) {
        Intent intent = new Intent(getContext(), QuestionsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", itemType);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<QuestionButton> getData() {
        List<QuestionButton> list = new ArrayList<>();

        PatternService patternService = new PatternService(getContext());
        List<Pattern> patternList = patternService.ShortPatternByIsDone(Pattern.class);
        for(Pattern pattern: patternList){
            list.add(new QuestionButton(pattern.getName(), pattern.getImage(), pattern.getIsDone()));
        }

        return list;
    }

    @Override
    public void onResume() {
        super.onResume();
        designPatternAdapter.setData(getData());
        recyclerView.setAdapter(designPatternAdapter);
    }

    private int countPatternisDone(){
        patternService = new PatternService(getContext());
        List<Pattern> list = patternService.GetPatternisDone(Pattern.class);
        return list.size();
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
                ContextCompat.getColor(getContext(), R.color.startColor),
                ContextCompat.getColor(getContext(), R.color.endColor)
        );
    }
}