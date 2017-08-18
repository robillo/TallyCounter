package com.robillo.tallycounter;

/**
 * Created by robinkamboj on 17/08/17.
 */

public interface CounterInterface {
    void reset();
    void increment();
    int getCount();
    void setCount(int count);
}
