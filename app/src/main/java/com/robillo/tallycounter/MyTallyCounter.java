package com.robillo.tallycounter;

import android.app.Activity;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by robinkamboj on 17/08/17.
 */

public class MyTallyCounter extends View implements CounterInterface {

    private static final int MAX_COUNT = 9999;
    private static final String MAX_COUNT_STRING = String.valueOf(MAX_COUNT);

    // State variables
    private int count = 100;
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
        setCount(count);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final Paint.FontMetrics fontMetrics = mNumberPaint.getFontMetrics();

        // Measure maximum possible width of text.
        final float maxTextWidth = mNumberPaint.measureText(MAX_COUNT_STRING);
        // Estimate maximum possible height of text.
        final float maxTextHeight = -fontMetrics.top + fontMetrics.bottom;

        // Add padding to maximum width calculation.
        final int desiredWidth = Math.round(maxTextWidth + getPaddingLeft() + getPaddingRight());

        // Add padding to maximum height calculation.
        final int desiredHeight = Math.round(maxTextHeight * 2f + getPaddingTop() +
                getPaddingBottom());

        // Reconcile size that this view wants to be with the size the parent will let it be.
        final int measuredWidth = reconcileSize(desiredWidth, widthMeasureSpec);
        final int measuredHeight = reconcileSize(desiredHeight, heightMeasureSpec);

        // Store the final measured dimensions.
        setMeasuredDimension(measuredWidth, measuredHeight);
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
//        canvas.drawRoundRect(mBackgroundRect, mCornerRadius, mCornerRadius, mBackgroundPaint);
        canvas.drawOval(mBackgroundRect, mBackgroundPaint);

        // Draw baseline.
        // Draw lines that show font top and bottom.
        final float baselineY = Math.round(canvasHeight * 0.65f);
        final Paint.FontMetrics fontMetrics = mNumberPaint.getFontMetrics();
        final float topY = Math.round(baselineY + fontMetrics.top);
        final float bottomY = Math.round(baselineY + fontMetrics.bottom);
        canvas.drawLine(0, topY, canvasWidth, topY, mLinePaint);
        canvas.drawLine(0, bottomY, canvasWidth, bottomY, mLinePaint);

        // Measure the width of text to display.
        final float textWidth = mNumberPaint.measureText(displayedCount);
        // Figure out an x-coordinate that will center the text in the canvas.
        final float textX = Math.round(centerX - textWidth * 0.5f);
        // Draw.
        canvas.drawText(displayedCount, textX, baselineY, mNumberPaint);
    }

    /**
     * Reconcile a desired size for the view contents with a {@link android.view.View.MeasureSpec}
     * constraint passed by the parent.
     *
     * This is a simplified version of {@link View#resolveSize(int, int)}
     *
     * @param contentSize Size of the view's contents.
     * @param measureSpec A {@link android.view.View.MeasureSpec} passed by the parent.
     * @return A size that best fits {@code contentSize} while respecting the parent's constraints.
     */
    private int reconcileSize(int contentSize, int measureSpec) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                return specSize;
            case MeasureSpec.AT_MOST:
                if (contentSize < specSize) {
                    return contentSize;
                } else {
                    return specSize;
                }
            case MeasureSpec.UNSPECIFIED:
            default:
                return contentSize;
        }
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
    public void increment(Activity activity) {

    }

    @Override
    public void decrement(){
        setCount(count-1);
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

    //UNUSED CONSTRUCTORS FOR THIS PROJECT
    public MyTallyCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyTallyCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
