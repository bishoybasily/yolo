package com.yolo.demo;

import com.yolo.annotations.Autowired;
import com.yolo.annotations.InjectMembers;

@InjectMembers
public class Screen {

	@Autowired
	protected ServiceRegistration serviceRegistration;
	@Autowired
	protected Database database;
	@Autowired
	protected ActivityOne activityOne;

	public Screen() {
		InjectorScreen.inject(this);
	}
}
