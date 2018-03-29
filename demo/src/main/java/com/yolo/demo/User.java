package com.yolo.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class User {

	private String name;
	private String address;

	public static User bishoy() {
		return new User()
				.setName("bishoy")
				.setAddress("maadi, cairo, egypt");
	}

}
