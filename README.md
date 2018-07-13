# Dependency injection for java & android

[![](https://jitpack.io/v/bishoybasily-fidelyo/yolo.svg)](https://jitpack.io/#bishoybasily-fidelyo/yolo)

## Overview

This library simplifies the process of di through a very simple api

## Setup (kotlin with gradle)
    repositories {
        maven { url 'https://jitpack.io' }        
    }

    dependencies {
        implementation 'com.github.bishoybasily-fidelyo.yolo:annotations:$version'
        kapt 'com.github.bishoybasily-fidelyo.yolo:processor:$version'
    }

## Example android kotlin

**Full example**
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
        return User.dummy();
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

// sample activity the recives it's dependecies injected
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

        fab.setOnClickListener { view ->
            if (currentFocus != null)
                inputMethodManager.showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT)
        }

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

} 