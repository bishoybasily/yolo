package com.yolo.demo;


public interface Database {

	Type type();

	enum Type {
		ORACLE,
		MONGODB
	}

}
