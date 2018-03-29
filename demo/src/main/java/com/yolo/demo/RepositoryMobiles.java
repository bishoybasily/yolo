package com.yolo.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RepositoryMobiles {

	private Database database;

	public RepositoryMobiles(Database database) {
		this.database = database;
	}

}
