package com.yolo.demo;

public class RepositoryMobiles {

	private Database database;

	public RepositoryMobiles(Database database) {
		this.database = database;
	}

    public Database getDatabase() {
        return database;
    }

    public RepositoryMobiles setDatabase(Database database) {
        this.database = database;
        return this;
    }
}
