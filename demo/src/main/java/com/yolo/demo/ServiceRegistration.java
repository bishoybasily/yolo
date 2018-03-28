package com.yolo.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ServiceRegistration {

	private RepositoryUsers repositoryUsers;
	private RepositoryMobiles repositoryMobiles;

	public ServiceRegistration(RepositoryUsers repositoryUsers, RepositoryMobiles repositoryMobiles) {
		this.repositoryUsers = repositoryUsers;
		this.repositoryMobiles = repositoryMobiles;
	}
}
