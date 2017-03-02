package drrino.com.learningdemo;

import android.app.Activity;
import android.app.Application;
import com.squareup.leakcanary.LeakCanary;
import java.util.HashSet;
import java.util.Set;

public class App extends Application {
    private static App instance;
    private Set<Activity> activities;


    public static synchronized App getInstance() {
        return instance;
    }


    @Override public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化内存泄漏检测
        LeakCanary.install(this);
    }


    public void addActivity(Activity act) {
        if (activities == null) {
            activities = new HashSet<>();
        }
        activities.add(act);
    }


    public void removeActivity(Activity act) {
        if (activities != null) {
            activities.remove(act);
        }
    }


    public void exitApp() {
        if (activities != null) {
            synchronized (activities) {
                for (Activity act : activities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
