package com.example.designpattern.UI.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.LinearGradient;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.designpattern.R;

public class ProgressCircleView extends View {
    private Paint progressPaint;
    private Paint backgroundPaint;
    private int progress = 0;
    private float strokeWidth = 20;
    private int startColor;
    private int endColor;
    private float radiusOffset = 50;

    public ProgressCircleView(Context context) {
        super(context);
        init();
    }

    public ProgressCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.darker_gray));
        startColor = ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark);
        endColor = ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        canvas.drawArc(strokeWidth / 2 + radiusOffset, strokeWidth / 2 + radiusOffset,
                width - strokeWidth / 2 - radiusOffset, height - strokeWidth / 2 - radiusOffset,
                -90, 360, false, backgroundPaint);

        Shader gradientShader = new LinearGradient(0, 0, width, height,
                startColor, endColor, Shader.TileMode.CLAMP);
        progressPaint.setShader(gradientShader);

        float sweepAngle = (360 * progress) / 100;
        canvas.drawArc(strokeWidth / 2 + radiusOffset, strokeWidth / 2 + radiusOffset,
                width - strokeWidth / 2 - radiusOffset, height - strokeWidth / 2 - radiusOffset,
                -90, sweepAngle, false, progressPaint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setStrokeWidth(float width) {
        this.strokeWidth = width;
        progressPaint.setStrokeWidth(width);
        backgroundPaint.setStrokeWidth(width);
        invalidate();
    }

    public void setGradientColors(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        invalidate();
    }

    public void setRadiusOffset(float offset) {
        this.radiusOffset = offset;
        invalidate();
    }
}
