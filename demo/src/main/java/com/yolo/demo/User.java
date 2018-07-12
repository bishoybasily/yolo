package com.yolo.demo;

public class User {

	private String name;
	private String address;

	public static User bishoy() {
		return new User()
				.setName("bishoy")
				.setAddress("maadi, cairo, egypt");
	}

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }
}
