package com.springandroid.sample;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ComponentMain_ModuleMain_TwoFactory implements Factory<Two> {
  private final ComponentMain.ModuleMain module;

  public ComponentMain_ModuleMain_TwoFactory(ComponentMain.ModuleMain module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Two get() {
    return Preconditions.checkNotNull(
        module.two(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Two> create(ComponentMain.ModuleMain module) {
    return new ComponentMain_ModuleMain_TwoFactory(module);
  }
}
