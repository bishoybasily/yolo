package com.springandroid.sample;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Service_Factory implements Factory<Service> {
  private final Provider<One> oneProvider;

  private final Provider<Two> twoProvider;

  public Service_Factory(Provider<One> oneProvider, Provider<Two> twoProvider) {
    assert oneProvider != null;
    this.oneProvider = oneProvider;
    assert twoProvider != null;
    this.twoProvider = twoProvider;
  }

  @Override
  public Service get() {
    return new Service(oneProvider.get(), twoProvider.get());
  }

  public static Factory<Service> create(Provider<One> oneProvider, Provider<Two> twoProvider) {
    return new Service_Factory(oneProvider, twoProvider);
  }
}
