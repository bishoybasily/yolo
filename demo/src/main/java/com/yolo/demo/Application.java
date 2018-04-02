package com.yolo.demo;

import com.yolo.annotations.Bean;
import com.yolo.annotations.Configuration;
import com.yolo.annotations.Qualifier;

@Configuration
public class Application {

	private FeatureOne featureOne;
	private FeatureTwo featureTwo;

	public Application(FeatureOne featureOne,
					   FeatureTwo featureTwo) {
		this.featureOne = featureOne;
		this.featureTwo = featureTwo;
	}

	@Bean
	@Qualifier("oracle")
	public Database db1(User user) {
		return new Oracle(user);
	}

	@Bean
	@Qualifier("mongoDb")
	public Database db2(User user) {
		return new MongoDb(user);
	}

	@Bean
	public RepositoryMobiles repositoryMobiles(@Qualifier("mongoDb") Database database) {
		return new RepositoryMobiles(database);
	}

	@Bean
	public RepositoryUsers repositoryUsers(Database database) {
		return new RepositoryUsers(database);
	}

	@Bean
	public ServiceRegistration serviceRegistration(RepositoryUsers repositoryUsers,
												   RepositoryMobiles repositoryMobiles) {
		return new ServiceRegistration(repositoryUsers, repositoryMobiles);
	}

}
