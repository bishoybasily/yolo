package com.gmail.bishoybasily.yolo.generated;

import com.gmail.bishoybasily.yolo.sample.ActivityOne;
import com.gmail.bishoybasily.yolo.sample.Application;
import com.gmail.bishoybasily.yolo.sample.Database;
import com.gmail.bishoybasily.yolo.sample.FeatureOne;
import com.gmail.bishoybasily.yolo.sample.FeatureTwo;
import com.gmail.bishoybasily.yolo.sample.RepositoryMobiles;
import com.gmail.bishoybasily.yolo.sample.RepositoryUsers;
import com.gmail.bishoybasily.yolo.sample.ServiceRegistration;
import com.gmail.bishoybasily.yolo.sample.Something;
import com.gmail.bishoybasily.yolo.sample.Token;
import com.gmail.bishoybasily.yolo.sample.User;

public class Graph {
  private static Graph instance;

  private FeatureTwo featureTwo;

  private FeatureOne featureOne;

  private Something something;

  private ActivityOne activityOne;

  private Application application;

  private Database oracle;

  private Database mongoDb;

  private RepositoryMobiles repositoryMobiles;

  private RepositoryUsers repositoryUsers;

  private ServiceRegistration serviceRegistration;

  private User user;

  private Database database;

  private Token token;

  private Graph() {
  }

  public static Graph getInstance() {
    if (instance == null) { synchronized (Graph.class) { if (instance == null) { instance = new Graph(); } } } return instance;
  }

  public FeatureTwo featureTwo() {
    if ( this.featureTwo == null ) this.featureTwo = new FeatureTwo();
    return this.featureTwo;
  }

  public FeatureOne featureOne() {
    if ( this.featureOne == null ) this.featureOne = new FeatureOne(token());
    return this.featureOne;
  }

  public Something something() {
    if ( this.something == null ) this.something = featureOne().something();
    return this.something;
  }

  public ActivityOne activityOne() {
    return featureOne().activityOne(serviceRegistration(),oracle());
  }

  public Application application() {
    if ( this.application == null ) this.application = new Application(featureOne(),featureTwo());
    return this.application;
  }

  public Database oracle() {
    if ( this.oracle == null ) this.oracle = application().db1(user());
    return this.oracle;
  }

  public Database mongoDb() {
    if ( this.mongoDb == null ) this.mongoDb = application().db2(user());
    return this.mongoDb;
  }

  public RepositoryMobiles repositoryMobiles() {
    if ( this.repositoryMobiles == null ) this.repositoryMobiles = application().repositoryMobiles(mongoDb());
    return this.repositoryMobiles;
  }

  public RepositoryUsers repositoryUsers() {
    if ( this.repositoryUsers == null ) this.repositoryUsers = application().repositoryUsers(database());
    return this.repositoryUsers;
  }

  public ServiceRegistration serviceRegistration() {
    if ( this.serviceRegistration == null ) this.serviceRegistration = application().serviceRegistration(repositoryUsers(),repositoryMobiles());
    return this.serviceRegistration;
  }

  public User user() {
    return this.user;
  }

  public void user(User user) {
    this.user=user;
  }

  public Database database() {
    return this.database;
  }

  public void database(Database database) {
    this.database=database;
  }

  public Token token() {
    return this.token;
  }

  public void token(Token token) {
    this.token=token;
  }
}
