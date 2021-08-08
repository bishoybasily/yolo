package com.gmail.bishoybasily.yolo.sample;

import android.util.Log;

import android.view.inputmethod.InputMethodManager;
import com.gmail.bishoybasily.yolo.annotations.Configuration;


@Configuration
public class TestAspects {
    
    private final InputMethodManager inputMethodManager;

    public TestAspects(InputMethodManager inputMethodManager) {
        this.inputMethodManager = inputMethodManager;
    }

    public void doSomething() {
        Log.d("@@", "Done");
    }

}
