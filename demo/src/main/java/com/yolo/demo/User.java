package com.yolo.demo;

import com.yolo.annotations.LazyBean;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@LazyBean
public class User {

	private String name;
	private String address;

	public static User bishoy() {
		return new User()
				.setName("bishoy")
				.setAddress("maadi, cairo, egypt");
	}

}
