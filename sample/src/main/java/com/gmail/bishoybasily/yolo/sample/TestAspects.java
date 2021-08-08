package com.gmail.bishoybasily.yolo.sample;

import android.util.Log;

import com.gmail.bishoybasily.yolo.annotations.Configuration;

@Configuration
public class TestAspects {

    public void doSomething() {
        Log.d("@@", "Done");
    }

}
