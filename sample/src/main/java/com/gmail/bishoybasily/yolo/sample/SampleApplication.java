package com.gmail.bishoybasily.yolo.sample;

import android.app.Application;
import com.gmail.bishoybasily.yolo.annotations.ComponentScan;
import com.gmail.bishoybasily.yolo.generated.Graph;

@ComponentScan(lazyBeans = {SampleApplication.class})
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Graph.getInstance().sampleApplication(this);

    }

}
