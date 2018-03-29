package com.yolo.demo;

import com.yolo.annotations.Autowired;
import com.yolo.annotations.InjectMembers;
import com.yolo.annotations.Qualifier;

@InjectMembers
public class Screen {

	@Autowired
	protected ServiceRegistration serviceRegistration;
	@Autowired
	@Qualifier("oracle")
	protected Database database;
	@Autowired
	protected ActivityOne activityOne;

	public Screen() {
		InjectorScreen.inject(this);
	}
}
