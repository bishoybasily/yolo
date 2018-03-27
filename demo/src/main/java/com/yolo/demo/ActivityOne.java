package com.yolo.demo;

public class ActivityOne {

	private ServiceRegistration serviceRegistration;
	private Database database;

	public ActivityOne(ServiceRegistration serviceRegistration, Database database) {
		this.serviceRegistration = serviceRegistration;
		this.database = database;
	}
}
