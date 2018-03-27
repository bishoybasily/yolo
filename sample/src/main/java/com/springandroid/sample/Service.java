package com.springandroid.sample;

import javax.inject.Inject;

public class Service {

	private One one;
	private Two two;

	@Inject
	public Service(One one, Two two) {
		this.one = one;
		this.two = two;
	}
}
