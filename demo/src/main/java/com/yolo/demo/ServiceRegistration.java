package com.yolo.demo;

public class ServiceRegistration {

	private RepositoryUsers repositoryUsers;
	private RepositoryMobiles repositoryMobiles;

	public ServiceRegistration(RepositoryUsers repositoryUsers, RepositoryMobiles repositoryMobiles) {
		this.repositoryUsers = repositoryUsers;
		this.repositoryMobiles = repositoryMobiles;
	}

    public RepositoryUsers getRepositoryUsers() {
        return repositoryUsers;
    }

    public ServiceRegistration setRepositoryUsers(RepositoryUsers repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
        return this;
    }

    public RepositoryMobiles getRepositoryMobiles() {
        return repositoryMobiles;
    }

    public ServiceRegistration setRepositoryMobiles(RepositoryMobiles repositoryMobiles) {
        this.repositoryMobiles = repositoryMobiles;
        return this;
    }
}
