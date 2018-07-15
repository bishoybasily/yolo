package com.gmail.bishoybasily.yolo.sample;


public interface Database {

    Type type();

    enum Type {
        ORACLE,
        MONGODB
    }

}
