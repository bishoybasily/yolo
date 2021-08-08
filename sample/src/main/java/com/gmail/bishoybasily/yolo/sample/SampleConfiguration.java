package com.gmail.bishoybasily.yolo.sample;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.gmail.bishoybasily.yolo.annotations.Bean;
import com.gmail.bishoybasily.yolo.annotations.Scope;
import com.gmail.bishoybasily.yolo.annotations.Qualifier;
import com.gmail.bishoybasily.yolo.annotations.Configuration;

@Configuration
public class SampleConfiguration {

    private SampleApplication sampleApplication;

    public SampleConfiguration(SampleApplication sampleApplication) {
        this.sampleApplication = sampleApplication;
    }

    @Bean
    public InputMethodManager inputMethodManager() {
        return (InputMethodManager) sampleApplication.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Bean
    @Qualifier("testAspectsDummy")
    @Scope("prototype")
    public TestAspects testAspects(InputMethodManager inputMethodManager) {
        return new TestAspects(inputMethodManager);
    }

}
