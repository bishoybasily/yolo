package com.yolo.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ActivityOne {

	private ServiceRegistration serviceRegistration;
	private Database database;

	public ActivityOne(ServiceRegistration serviceRegistration, Database database) {
		this.serviceRegistration = serviceRegistration;
		this.database = database;
	}
}
