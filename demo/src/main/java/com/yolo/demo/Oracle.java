package com.yolo.demo;

public class Oracle implements Database {

	@Override
	public Type type() {
		return Type.ORACLE;
	}

}
