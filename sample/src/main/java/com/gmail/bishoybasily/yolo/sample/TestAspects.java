package com.gmail.bishoybasily.yolo.sample;

import android.util.Log;

import com.gmail.bishoybasily.yolo.annotations.Configuration;
import com.gmail.bishoybasily.yolo.aspects.annotation.DebugTrace;

@Configuration
public class TestAspects {

    @DebugTrace
    public void doSomething() {
        Log.d("@@", "Done");
    }

}
