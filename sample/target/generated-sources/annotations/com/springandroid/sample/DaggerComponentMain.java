package com.springandroid.sample;

import dagger.MembersInjector;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerComponentMain implements ComponentMain {
  private Provider<One> oneProvider;

  private Provider<Two> twoProvider;

  private Provider<Service> serviceProvider;

  private MembersInjector<Main> mainMembersInjector;

  private DaggerComponentMain(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ComponentMain create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.oneProvider = ComponentMain_ModuleMain_OneFactory.create(builder.moduleMain);

    this.twoProvider = ComponentMain_ModuleMain_TwoFactory.create(builder.moduleMain);

    this.serviceProvider = Service_Factory.create(oneProvider, twoProvider);

    this.mainMembersInjector = Main_MembersInjector.create(serviceProvider);
  }

  @Override
  public void inject(Main main) {
    mainMembersInjector.injectMembers(main);
  }

  public static final class Builder {
    private ComponentMain.ModuleMain moduleMain;

    private Builder() {}

    public ComponentMain build() {
      if (moduleMain == null) {
        this.moduleMain = new ComponentMain.ModuleMain();
      }
      return new DaggerComponentMain(this);
    }

    public Builder moduleMain(ComponentMain.ModuleMain moduleMain) {
      this.moduleMain = Preconditions.checkNotNull(moduleMain);
      return this;
    }
  }
}
