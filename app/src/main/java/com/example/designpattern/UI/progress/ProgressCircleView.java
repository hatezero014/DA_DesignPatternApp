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

public class ProgressCircleView extends View {
    private Paint paint;
    private int progress = 0;
    private float strokeWidth = 20; // Default stroke width
    private int startColor;
    private int endColor;

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
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth); // Set the stroke width here

        // Default colors
        startColor = ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark);
        endColor = ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Draw the progress arc with gradient
        Shader gradientShader = new LinearGradient(0, 0, width, height,
                startColor, endColor, Shader.TileMode.CLAMP);
        paint.setShader(gradientShader);

        float sweepAngle = (360 * progress) / 100;
        canvas.drawArc(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2, height - strokeWidth / 2,
                -90, sweepAngle, false, paint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setStrokeWidth(float width) {
        this.strokeWidth = width;
        paint.setStrokeWidth(width);
        invalidate();
    }

    public void setGradientColors(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        invalidate();
    }
}
