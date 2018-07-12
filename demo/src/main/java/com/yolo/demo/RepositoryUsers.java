package com.yolo.demo;

public class RepositoryUsers {

	public Database database;

	public RepositoryUsers(Database database) {
		this.database = database;
	}

    public Database getDatabase() {
        return database;
    }

    public RepositoryUsers setDatabase(Database database) {
        this.database = database;
        return this;
    }
}
