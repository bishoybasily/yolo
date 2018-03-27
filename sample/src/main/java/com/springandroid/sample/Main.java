package com.springandroid.sample;


import javax.inject.Inject;

public class Main {

	@Inject
	protected Service service;

	public static void main(String[] args) {
		Main main = new Main();

		ComponentMain.Initializer.initialize().inject(main);

		System.out.println(main.service == null);

	}


}
