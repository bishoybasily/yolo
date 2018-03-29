package com.yolo.demo;

import com.yolo.annotations.Bean;
import com.yolo.annotations.Configuration;

@Configuration
public class Application {

	private FeatureOne featureOne;
	private FeatureTwo featureTwo;

	public Application(FeatureOne featureOne, FeatureTwo featureTwo) {
		this.featureOne = featureOne;
		this.featureTwo = featureTwo;
	}

	@Bean
	public Database database(User user) {
		return new Database(user);
	}

	@Bean
	public RepositoryMobiles repositoryMobiles(Database database) {
		return new RepositoryMobiles(database);
	}

	@Bean
	public RepositoryUsers repositoryUsers(Database database) {
		return new RepositoryUsers(database);
	}

	@Bean
	public ServiceRegistration serviceRegistration(RepositoryUsers repositoryUsers, RepositoryMobiles repositoryMobiles) {
		return new ServiceRegistration(repositoryUsers, repositoryMobiles);
	}

}
