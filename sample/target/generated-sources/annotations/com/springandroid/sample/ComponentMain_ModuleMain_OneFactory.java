package com.springandroid.sample;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ComponentMain_ModuleMain_OneFactory implements Factory<One> {
  private final ComponentMain.ModuleMain module;

  public ComponentMain_ModuleMain_OneFactory(ComponentMain.ModuleMain module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public One get() {
    return Preconditions.checkNotNull(
        module.one(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<One> create(ComponentMain.ModuleMain module) {
    return new ComponentMain_ModuleMain_OneFactory(module);
  }
}
