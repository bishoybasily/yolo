package com.springandroid.sample;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Main_MembersInjector implements MembersInjector<Main> {
  private final Provider<Service> serviceProvider;

  public Main_MembersInjector(Provider<Service> serviceProvider) {
    assert serviceProvider != null;
    this.serviceProvider = serviceProvider;
  }

  public static MembersInjector<Main> create(Provider<Service> serviceProvider) {
    return new Main_MembersInjector(serviceProvider);
  }

  @Override
  public void injectMembers(Main instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.service = serviceProvider.get();
  }

  public static void injectService(Main instance, Provider<Service> serviceProvider) {
    instance.service = serviceProvider.get();
  }
}
