package com.robillo.tallycounter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by robinkamboj on 19/08/17.
 */

public class MyStopWatch extends View implements CounterInterface{

    //CONSTANT VARIABLES
    private static final int MAX_SECONDS = 9999;
    private static final String MAX_COUNT_STRING = String.valueOf(MAX_SECONDS);

    //STATE VARIABLES
    private int count;
    private int displayedCount;

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
        mLinePaint.setStrokeWidth(1f);
        mNumberPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mNumberPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));

        //OBJECT ALLOCATIONS HERE
        mBackgroundRect = new RectF();

        //MEASUREMENTS
        //SET TEXTSIZE TO 64 SP
        mNumberPaint.setTextSize(Math.round(64f * getResources().getDisplayMetrics().scaledDensity));

        //INITIAL SETUP HERE
        setCount(0);
    }

    @Override
    public void reset() {

    }

    @Override
    public void increment() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public void setCount(int count) {

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
