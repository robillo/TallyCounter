package com.robillo.tallycounter;

import android.app.Activity;
import android.content.Context;

/**
 * Created by robinkamboj on 17/08/17.
 */

public interface CounterInterface {
    void reset();
    void increment();
    void increment(Activity activity);
    int getCount();
    void setCount(int count);
}
