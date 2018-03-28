package com.yolo.demo;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Database {

	private final String value = "Database value";
}
