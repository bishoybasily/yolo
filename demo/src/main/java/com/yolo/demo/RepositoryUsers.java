package com.yolo.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RepositoryUsers {

	public Database database;

	public RepositoryUsers(Database database) {
		this.database = database;
	}
}
