# Dependency injection, quick services and aspects for android

[![](https://jitpack.io/v/bishoybasily/yolo.svg)](https://jitpack.io/#bishoybasily/yolo)

## Overview

This library simplifies the process of di through a very simple api

## Gradle dependencies

    repositories {
        maven { url 'https://jitpack.io' }        
    }

    dependencies {
        implementation 'com.github.bishoybasily.yolo:annotations:$version'
        kapt 'com.github.bishoybasily.yolo:processor:$version'
    }

## Graph example

``` java
// configuration class for providing the beans
@Configuration
public class ApplicationConfiguration {

    private Application application;

    public ApplicationConfiguration(Application application) {
        this.application = application;
    }

    // scope is singleton by default
    @Bean 
    public InputMethodManager inputMethodManager() {
        return (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Bean
    @Scope("prototype")
    public SomeClass someClass() {
        return new SomeClass();
    }

}
    
// create the application class annotated with @EnableGraph and specify the lazy beans
// lazy means that their instance will be available later
// during the application lifecycle.
// for example, a user object won't be available before login and similarly here,
// the earliest place where an application instance is available is here. 
@EnableGraph(lazyBeans = {Application.class})
public class DemoYoloApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // to provide a lazy instance during runtime,
        // get the graph's singleton instance and set your lazy bean instance
        Graph.getInstance().application(this);

    }
}
```

``` kotlin

// sample activity
@InjectMembers
class MainActivity : AppCompatActivity() {

    @Autowired
    protected lateinit var inputMethodManager: InputMethodManager
    @Autowired
    protected lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // an inector will be created for every class annotated with @InjectMembers,
        // call at the earliest posible time before trying to access the class dependencies
        InjectorMainActivity.inject(this)

        // you can use (someClass & inputMethodManager) here

    }
    
} 
```

## Worker example

``` java

// create the application class annotated with @EnableWorker
@EnableWorker
public class DemoYoloApplication extends Application { }

// create a class representing your intent service annotated with @Worker,
// after building the application a new class will be generated to extend it
// called `Worker${YourClassName}` 
// and finally don't forget to register it as a service in the manifest.xml
@Worker
public class DataFetcher extends WorkerDataFetcher {

    // you can add as many as you want of these @Job annotated methods,
    // where every method will run a background task 
    // (it can notify the UI back through broadcast recievers) 
    @Job
    public void fetchPosts(Intent intent) {
        // do some network operation
    }

    @Job
    public void fetchComments(Intent intent) { }
    
}
```
``` kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // to use the service, the generated class will contain a static method for each job
        // to start the service and execute the logic inside the @Job annotated method
        WorkerDataFetcher.executeFetchCommentsJob(this, Intent().putExtra("k", "v"))

    }

}
```
