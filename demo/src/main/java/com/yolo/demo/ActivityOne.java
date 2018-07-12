package com.yolo.demo;

public class ActivityOne {

    private ServiceRegistration serviceRegistration;
    private Database database;

    public ActivityOne(ServiceRegistration serviceRegistration, Database database) {
        this.serviceRegistration = serviceRegistration;
        this.database = database;
    }

    public ServiceRegistration getServiceRegistration() {
        return serviceRegistration;
    }

    public ActivityOne setServiceRegistration(ServiceRegistration serviceRegistration) {
        this.serviceRegistration = serviceRegistration;
        return this;
    }

    public Database getDatabase() {
        return database;
    }

    public ActivityOne setDatabase(Database database) {
        this.database = database;
        return this;
    }
}
