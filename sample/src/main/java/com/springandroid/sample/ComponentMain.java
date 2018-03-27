package com.springandroid.sample;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Component(modules = {ComponentMain.ModuleMain.class})
public interface ComponentMain {

	void inject(Main main);

	class Initializer {

		private static ComponentMain componentMain;

		public static ComponentMain initialize() {
			if (componentMain == null)
				componentMain = DaggerComponentMain.builder().build();
			return componentMain;
		}

		public static ComponentMain get() {
			return componentMain;
		}
	}

	@Module
	class ModuleMain {

		@Provides
		public One one() {
			return new One();
		}

		@Provides
		public Two two() {
			return new Two();
		}

	}

}
