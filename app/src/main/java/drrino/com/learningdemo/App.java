package drrino.com.learningdemo;

import android.app.Application;

public class App extends Application {
    private static App instance;


    public static synchronized App getInstance() {
        return instance;
    }


    @Override public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
