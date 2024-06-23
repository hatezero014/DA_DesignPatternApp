package com.example.designpattern.UI.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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
    private Paint startEndCirclePaint;
    private Paint innerCirclePaint;
    private int progress = 0;
    private float strokeWidth = 20;
    private int startColor;
    private int endColor;
    private float radiusOffset = 50;
    private float cornerRadius = 10;

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

        startEndCirclePaint = new Paint();
        startEndCirclePaint.setAntiAlias(true);
        startEndCirclePaint.setStyle(Paint.Style.FILL);

        innerCirclePaint = new Paint();
        innerCirclePaint.setAntiAlias(true);
        innerCirclePaint.setStyle(Paint.Style.FILL);
        innerCirclePaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));

        startColor = ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark);
        endColor = ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        RectF rectF = new RectF(strokeWidth / 2 + radiusOffset, strokeWidth / 2 + radiusOffset,
                width - strokeWidth / 2 - radiusOffset, height - strokeWidth / 2 - radiusOffset);

        canvas.drawArc(rectF, -90, 360, false, backgroundPaint);

        Shader gradientShader = new LinearGradient(0, 0, width, height,
                startColor, endColor, Shader.TileMode.CLAMP);
        progressPaint.setShader(gradientShader);
        startEndCirclePaint.setShader(gradientShader);

        float sweepAngle = (360 * progress) / 100;

        canvas.drawArc(rectF, -90, sweepAngle, false, progressPaint);

        // Draw start circle
        float cxStart = width / 2;
        float cyStart = radiusOffset + strokeWidth / 2;

        canvas.drawCircle(cxStart, cyStart, strokeWidth / 2, startEndCirclePaint);

        // Draw end circle if progress is greater than 0
        if (progress > 0) {
            float angle = (float) Math.toRadians(-90 + sweepAngle);
            float cxEnd = width / 2 + (width / 2 - radiusOffset - strokeWidth / 2) * (float) Math.cos(angle);
            float cyEnd = height / 2 + (height / 2 - radiusOffset - strokeWidth / 2) * (float) Math.sin(angle);

            canvas.drawCircle(cxEnd, cyEnd, strokeWidth / 2, startEndCirclePaint);
            canvas.drawCircle(cxEnd, cyEnd, strokeWidth / 4, innerCirclePaint); // Draw inner white circle
        }
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

    public void setCornerRadius(float radius) {
        this.cornerRadius = radius;
        invalidate();
    }
}
