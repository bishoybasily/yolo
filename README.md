# Dependency injection, quick services and aspects for android

[![](https://jitpack.io/v/bishoybasily/yolo.svg)](https://jitpack.io/#bishoybasily/yolo)

## Overview

This library simplifies the process of di through a very simple api

## Setup (kotlin with gradle)
    repositories {
        maven { url 'https://jitpack.io' }        
    }

    dependencies {
        implementation 'com.github.bishoybasily.yolo:annotations:$version'
        kapt 'com.github.bishoybasily.yolo:processor:$version'
    }

## Example android kotlin

**Android example**
``` kotlin

// configuration class for providing the beans
@Configuration
public class ApplicationConfiguration {

    private Application application;

    public ApplicationConfiguration(Application application) {
        this.application = application;
    }

    @Bean // scope is singleton by default
    public InputMethodManager inputMethodManager() {
        return (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Bean
    @Scope("prototype")
    public User user() {
        return new User();
    }

}

// create the application class
@EnableGraph(lazyBeans = {Application.class})
public class DemoYoloApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Graph.getInstance().application(this);

    }
}

// sample activity
@InjectMembers
class MainActivity : AppCompatActivity() {

    @Autowired
    protected lateinit var inputMethodManager: InputMethodManager
    @Autowired
    protected lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        InjectorMainActivity.inject(this) // members injection

        // you can use (user & inputMethodManager) here

    }

  

} 
