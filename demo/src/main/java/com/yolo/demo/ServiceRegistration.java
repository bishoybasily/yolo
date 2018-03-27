package com.yolo.demo;

import com.yolo.annotations.Component;

@Component
public class ServiceRegistration {

	private RepositoryUsers repositoryUsers;
	private RepositoryMobiles repositoryMobiles;

	public ServiceRegistration(RepositoryUsers repositoryUsers, RepositoryMobiles repositoryMobiles) {
		this.repositoryUsers = repositoryUsers;
		this.repositoryMobiles = repositoryMobiles;
	}
}
