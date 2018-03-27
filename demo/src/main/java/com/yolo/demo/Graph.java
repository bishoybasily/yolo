package com.yolo.demo;

public class Graph {

	private Application application;
	private FeatureOne featureOne;
	private FeatureTwo featureTwo;

	public Graph() {
		this.featureOne = featureOne = new FeatureOne();
		this.featureTwo = featureTwo = new FeatureTwo();
		this.application = application = new Application(this.featureOne, this.featureTwo);
	}

	Database database() {
		return application.database();
	}

	RepositoryMobiles repositoryMobiles() {
		return application.repositoryMobiles(database());
	}

	RepositoryUsers repositoryUsers() {
		return application.repositoryUsers(database());
	}

}
