package com.yolo.demo;

import com.yolo.annotations.EnableGraph;
import com.yolo.generated.Graph;

@EnableGraph
public class Main {

	public static void main(String[] args) {
		Graph.getInstance().user(User.bishoy());

		Screen screen = new Screen();
		System.err.println(screen.activityOne.getServiceRegistration().getRepositoryMobiles().getDatabase().getUser().getName());


//		System.out.println(screen.activityOne
//				.getServiceRegistration()
//				.getRepositoryMobiles()
//				.getDatabase()
//				.getValue());



	}

}
