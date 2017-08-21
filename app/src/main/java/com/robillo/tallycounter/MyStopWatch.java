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
 * Created by robinkamboj on 19/08/17.
 */

public class MyStopWatch extends View implements CounterInterface{

    //CONSTANT VARIABLES
    private static final int MAX_SECONDS = 9999;
    private static final String MAX_COUNT_STRING = String.valueOf(MAX_SECONDS);

    //STATE VARIABLES
    private int count;
    private String displayedCount;

    //DRAWING VARIABLES
    Paint mBackgroundPaint, mLinePaint;
    TextPaint mNumberPaint;
    RectF mBackgroundRect;
    float mCornerRadius;

    public MyStopWatch(Context context) {
        super(context);
    }

    public MyStopWatch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //SET UP PAINTS FOR CANVAS DRAWING
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        mLinePaint.setStrokeWidth(4f);
        mNumberPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mNumberPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));

        //OBJECT ALLOCATIONS HERE
        mBackgroundRect = new RectF();

        //MEASUREMENTS
        //SET TEXTSIZE TO 64 SP
        mNumberPaint.setTextSize(Math.round(64f * getResources().getDisplayMetrics().scaledDensity));
        mCornerRadius = Math.round(1f * getResources().getDisplayMetrics().density);

        //INITIAL SETUP HERE
        setCount(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final Paint.FontMetrics fontMetrics = mNumberPaint.getFontMetrics();

        //MEASURE MAX WIDTH, ESTIMATE MAX HEIGHT
        final float maxWidth = mNumberPaint.measureText(MAX_COUNT_STRING);
        final float maxHeight = -fontMetrics.top + fontMetrics.bottom;

        //Add paddings to max width and height
        final int desiredWidth = Math.round(maxWidth + getPaddingLeft() + getPaddingRight());
        final int desiredHeight = Math.round(maxHeight + getPaddingTop() + getPaddingBottom());

        final int measureWidth = reconcileSize(desiredWidth, widthMeasureSpec);
        final int measureHeight = reconcileSize(desiredHeight, heightMeasureSpec);

        //STORE THE FINAL MEASURED DIMENSIONS
        setMeasuredDimension(measureWidth, measureHeight);
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
    protected void onDraw(Canvas canvas) {

        //GRAB CANVAS DIMENSIONS
        final int canvasWidth = canvas.getWidth();
        final int canvasHeight = canvas.getHeight();

        //CALCULATE HORIZONTAL CENTER
        final float centerX = canvasWidth * 0.5f; //HALF OF WIDTH OF canvasWidth

        //DRAW THE BACKGROUND
        mBackgroundRect.set(0f, 0f, canvasWidth, canvasHeight);
        canvas.drawRoundRect(mBackgroundRect, mCornerRadius, mCornerRadius, mBackgroundPaint);

        //DRAW THE BASELINE
        final float baselineY = canvasHeight * 0.7f;
        final Paint.FontMetrics fontMetrics = mNumberPaint.getFontMetrics();
        final int topY = Math.round(baselineY + fontMetrics.top);
        final int bottomY = Math.round(baselineY + fontMetrics.bottom);
        canvas.drawLine(0f, topY, canvasWidth, topY, mLinePaint);
        canvas.drawLine(0f, bottomY, canvasWidth, bottomY, mLinePaint);

        //Measure textwidth, text x-cood that will center the text, draw text
        final float textWidth = mNumberPaint.measureText(displayedCount);
        final float textX = Math.round(centerX - textWidth * 0.5f);
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
    public void increment(Activity activity) {
        final Timer timer = new Timer();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        setCount(count+1);
                    }
                }, 1000, 1000);
            }
        });
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
        count = Math.min(count, MAX_SECONDS);
        this.count = count;
        // Create the string here.
        this.displayedCount = String.format(Locale.getDefault(), "%04d", count);
        invalidate();
    }

    //UNUSED CONSTRUCTORS FOR THIS PROJECT
    public MyStopWatch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyStopWatch(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
