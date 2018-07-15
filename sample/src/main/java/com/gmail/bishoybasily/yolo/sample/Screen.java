package com.gmail.bishoybasily.yolo.sample;

import com.gmail.bishoybasily.yolo.annotations.Autowired;
import com.gmail.bishoybasily.yolo.annotations.InjectMembers;
import com.gmail.bishoybasily.yolo.annotations.Qualifier;

@InjectMembers
public class Screen {

    @Autowired
    protected ServiceRegistration serviceRegistration;
    @Autowired
    @Qualifier("oracle")
    protected Database database;
    @Autowired
    protected ActivityOne activityOne;

    public Screen() {
        InjectorScreen.inject(this);
    }
}
