package com.yolo.demo;

import com.yolo.annotations.Bean;
import com.yolo.annotations.Configuration;

@Configuration
public class FeatureOne {

	@Bean
	public ActivityOne activityOne(ServiceRegistration serviceRegistration, Database database) {
		return new ActivityOne(serviceRegistration, database);
	}

}
