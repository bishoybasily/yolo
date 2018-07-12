package com.yolo.demo;

public class MongoDb implements Database {

	private User user;

    public MongoDb(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public MongoDb setUser(User user) {
        this.user = user;
        return this;
    }

	@Override
	public Type type() {
		return Type.MONGODB;
	}

}
