package com.yolo.demo;

public class Oracle implements Database {

	private User user;

	public Oracle(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Oracle setUser(User user) {
		this.user = user;
		return this;
	}

	@Override
	public Type type() {
		return Type.ORACLE;
	}

}
