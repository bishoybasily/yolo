package com.gmail.bishoybasily.yolo.sample;

public class InjectorScreen {
  public static void inject(Screen screen) {
    screen.serviceRegistration = com.gmail.bishoybasily.yolo.generated.Graph.getInstance().serviceRegistration();
    screen.database = com.gmail.bishoybasily.yolo.generated.Graph.getInstance().oracle();
    screen.activityOne = com.gmail.bishoybasily.yolo.generated.Graph.getInstance().activityOne();
  }
}
