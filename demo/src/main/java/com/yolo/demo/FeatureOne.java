package com.yolo.demo;

import com.yolo.annotations.Bean;
import com.yolo.annotations.Configuration;
import com.yolo.annotations.Qualifier;


@Configuration
public class FeatureOne {

	private Token token;

	public FeatureOne(Token token) {
		this.token = token;
	}

	@Bean
	public ActivityOne activityOne(ServiceRegistration serviceRegistration, @Qualifier("oracle") Database database) {
		return new ActivityOne(serviceRegistration, database);
	}

}
