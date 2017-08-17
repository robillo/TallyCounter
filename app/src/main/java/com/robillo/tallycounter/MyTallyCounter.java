package com.robillo.tallycounter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

/**
 * Created by robinkamboj on 17/08/17.
 */

public class MyTallyCounter extends View implements TallyCounter{

    private static final int MAX_COUNT = 9999;

    // State variables
    private int count;
    private String displayedCount;

    //Drawing variables
    Paint mBackgroundPaint, mLinePaint;
    TextPaint mNumberPaint;
    RectF mBackgroundRect;
    float mCornerRadius;

    public MyTallyCounter(Context context) {
        super(context);
    }

    public MyTallyCounter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Set up paints for canvas drawing.
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        mLinePaint.setStrokeWidth(5f);
        mNumberPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mNumberPaint.setColor(ContextCompat.getColor(context, android.R.color.white));
        // Set the number text size to be 64sp.
        // Translate 64sp
        mNumberPaint.setTextSize(Math.round(64f * getResources().getDisplayMetrics().scaledDensity));

        // Allocate objects needed for canvas drawing here.
        mBackgroundRect = new RectF();

        // Initialize drawing measurements.
        mCornerRadius = Math.round(1f * getResources().getDisplayMetrics().density);

        // Do initial count setup.
        setCount(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Grab canvas dimensions.
        final int canvasWidth = canvas.getWidth();
        final int canvasHeight = canvas.getHeight();

        // Calculate horizontal center.
        final float centerX = canvasWidth * 0.5f;

        // Draw the background.
        mBackgroundRect.set(0f, 0f, canvasWidth, canvasHeight);
        canvas.drawRoundRect(mBackgroundRect, mCornerRadius, mCornerRadius, mBackgroundPaint);

        // Draw baseline.
        final float baselineY = Math.round(canvasHeight * 0.6f);
        canvas.drawLine(0, baselineY, canvasWidth, baselineY, mLinePaint);

        // Draw text.

        // Measure the width of text to display.
        final float textWidth = mNumberPaint.measureText(displayedCount);
        // Figure out an x-coordinate that will center the text in the canvas.
        final float textX = Math.round(centerX - textWidth * 0.5f);
        // Draw.
        canvas.drawText(displayedCount, textX, baselineY, mNumberPaint);
    }

    @Override
    public void reset() {
        if(getCount()!=0){
            setCount(0);
            invalidate();
        }
    }

    @Override
    public void increment() {
        setCount(count+1);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void setCount(int count) {
        count = Math.min(count, MAX_COUNT);
        this.count = count;
        // Create the string here.
        this.displayedCount = String.format(Locale.getDefault(), "%04d", count);
        invalidate();
    }

    //UNUSED FOR THIS PROJECT
    public MyTallyCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyTallyCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
