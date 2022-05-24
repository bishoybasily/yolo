package com.gmail.bishoybasily.yolo.sample;

import android.content.Intent;
import com.gmail.bishoybasily.yolo.annotations.Job;
import com.gmail.bishoybasily.yolo.annotations.Worker;


/**
 * @author bishoybasily
 * @since 2022-05-24
 */
@Worker
public class DataFetcher extends WorkerDataFetcher {

    @Job
    @Override
    public void fetchPosts(Intent intent) {
        // do some network operation
    }

    @Job
    @Override
    public void fetchComments(Intent intent) {
    }
}
