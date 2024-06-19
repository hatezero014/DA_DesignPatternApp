package com.example.designpattern.UI.progress;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.designpattern.R;


public class ProgressFragment extends Fragment {
    private ProgressCircleView progressCircleView;;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        final TextView textView1 = view.findViewById(R.id.progress_percent);
        final TextView textView2 = view.findViewById(R.id.title_id);
        progressCircleView = view.findViewById(R.id.progress_circle);

        String progressText = textView1.getText().toString().replace("%", "");
        int progress = Integer.parseInt(progressText);
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

        return view ;

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