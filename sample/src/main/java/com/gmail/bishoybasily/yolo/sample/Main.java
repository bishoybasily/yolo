package com.gmail.bishoybasily.yolo.sample;

import com.gmail.bishoybasily.yolo.annotations.EnableGraph;
import com.gmail.bishoybasily.yolo.sample.generated.Graph;

@EnableGraph(lazyBeans = {User.class, Database.class})
public class Main {

    public static void main(String[] args) {

        Graph.getInstance().user(User.bishoy());
        Graph.getInstance().database(new Oracle(User.bishoy()));

        Screen screen = new Screen();
        System.out.println(
                screen
                        .activityOne
                        .getServiceRegistration()
                        .getRepositoryUsers()
                        .getDatabase().type()
        );

    }

}
